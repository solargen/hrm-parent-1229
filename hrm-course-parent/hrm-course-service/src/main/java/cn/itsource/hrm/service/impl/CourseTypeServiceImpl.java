package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.client.CacheClient;
import cn.itsource.hrm.domain.CourseType;
import cn.itsource.hrm.mapper.CourseTypeMapper;
import cn.itsource.hrm.service.ICourseTypeService;
import cn.itsource.hrm.util.AjaxResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author solargen
 * @since 2020-06-17
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private CacheClient cacheClient;

    private final String KEY = "coursetype:tree";

    /**
     * 加载课程类型树的数据
     *
     * 思考：查询出来的课程类型，以什么样的数据类型保存到redis中？
     *
     * @return
     */
    @Override
    public List<CourseType> loadTypeTree() {

        List<CourseType> courseTypeList = new ArrayList<>();

        //自旋获取锁资源
        while(true){
            //查询redis
            AjaxResult ajaxResult = cacheClient.get(KEY);
            String courseTypeJSON = (String) ajaxResult.getResultObj();
            if(StringUtils.isNotEmpty(courseTypeJSON)){
                //如果有，则直接返回
                courseTypeList = JSONObject.parseArray(courseTypeJSON,CourseType.class);
                return courseTypeList;
            }

            try {
                //上锁
                ajaxResult = cacheClient.setnx("courseTypeKey","1");
                Integer result = (Integer) ajaxResult.getResultObj();
                //上锁成功
                if(result==1){
                    //2.2如果没有，则查询数据库
                    courseTypeList = typeTreeByLoopMap();
                    //3缓存到redis中
                    courseTypeJSON = JSONObject.toJSONString(courseTypeList);
                    cacheClient.setex(KEY,10*60,courseTypeJSON);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cacheClient.deleteKey("courseTypeKey");
            }
        }

        //4返回数据
        return courseTypeList;
    }

    /**
     * 根据父id递归查询所有的子类型
     *
     * 递归方式：
     * 1、代码可读性
     * 2、每次递归都要查询数据库，效率低
     * 3、如果级别比较深，递归的次数比较多，如果递归过深，可能会导致栈溢出
     *
     * 所以，能有循环解决的问题尽量使用循环
     *
     *
     * @param parentId
     * @return
     */
    private List<CourseType> getByParentId(Long parentId){
        List<CourseType> courseTypes = baseMapper.selectList(new QueryWrapper<CourseType>().eq("pid", parentId));
        //递归的出口
        if(courseTypes==null||courseTypes.size()<=0){
            return null;
        }
        for (CourseType courseType : courseTypes) {
            List<CourseType> children = getByParentId(courseType.getId());
            courseType.setChildren(children);
        }
        return courseTypes;
    }

    /**
     * 嵌套循环的方式查询无限级别的课程类型
     *
     * 1000个课程类型
     *  循环的次数：1000*1000 = 1000000
     *
     * @return
     */
    private List<CourseType> typeTreeByLoop(){

        //先查询所有的课程类型
        List<CourseType> allCourseTypes = baseMapper.selectList(null);
        //创建一个集合存放所有的一级类型
        List<CourseType> firstLevelCourseTypes = new ArrayList<>();
        //遍历所有的课程类型
        for (CourseType courseType : allCourseTypes) {
            //如果pid为0，则表示是一级类型，放入之前创建的集合中
            if(courseType.getPid()==0){
                firstLevelCourseTypes.add(courseType);
            }else{
                //如果pid不为0，则嵌套循环查询他的父类型，添加到父类型的children集合中
                for (CourseType parentType : allCourseTypes) {
                    if(courseType.getPid().equals(parentType.getId())){
                        parentType.getChildren().add(courseType);
                    }
                }
            }
        }

        return firstLevelCourseTypes;
    }

    /**
     *
     * 循环+Map的形式
     *
     * 1000个类型
     *
     * 循环的次数  : 1000+1000=2000
     *
     * @return
     */
    private List<CourseType> typeTreeByLoopMap(){

        //查询所有的课程类型
        List<CourseType> courseTypeList = baseMapper.selectList(null);
        //创建一个集合存放所有的一级类型
        List<CourseType> firstLevleTypes = new ArrayList<>();
        //创建一个Map存放所有类型，Map的key为类型d的i，Map的value为当前类型对象
        Map<Long,CourseType> courseTypeMap = new HashMap<>();

        //往Map中放值
        for (CourseType courseType : courseTypeList) {
            courseTypeMap.put(courseType.getId(),courseType);
        }

        for (CourseType courseType : courseTypeList) {
            if(courseType.getPid()==0){
                firstLevleTypes.add(courseType);
            }else{
                //如果pid不为0，则嵌套循环查询他的父类型，添加到父类型的children集合中
                CourseType parent = courseTypeMap.get(courseType.getPid());
                if(parent!=null){
                    parent.getChildren().add(courseType);
                }
            }
        }
        return firstLevleTypes;

    }

    @Override
    @Transactional
    public boolean save(CourseType entity) {
        boolean result = super.save(entity);
        synchronizedOperation();
        return result;
    }

    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        boolean result = super.removeById(id);
        synchronizedOperation();
        return result;
    }

    @Override
    @Transactional
    public boolean updateById(CourseType entity) {
        boolean b = super.updateById(entity);
        synchronizedOperation();
        return b;
    }


    /**
     * 增删改时的同步操作
     */
    private void synchronizedOperation(){
        List<CourseType> courseTypes = typeTreeByLoopMap();
        String str = JSONObject.toJSONString(courseTypes);
        cacheClient.setex(KEY,10*60,str);
    }


}
