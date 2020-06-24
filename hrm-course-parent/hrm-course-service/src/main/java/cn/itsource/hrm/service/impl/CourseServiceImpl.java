package cn.itsource.hrm.service.impl;
import cn.itsource.hrm.client.CourseDocClient;
import cn.itsource.hrm.client.SystemdictionaryItemClient;
import cn.itsource.hrm.doc.CourseDoc;
import cn.itsource.hrm.domain.*;

import cn.itsource.hrm.controller.vo.CourseAddVo;
import cn.itsource.hrm.mapper.CourseDetailMapper;
import cn.itsource.hrm.mapper.CourseMapper;
import cn.itsource.hrm.mapper.CourseMarketMapper;
import cn.itsource.hrm.mapper.CourseTypeMapper;
import cn.itsource.hrm.query.CourseQuery;
import cn.itsource.hrm.service.ICourseService;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author solargen
 * @since 2020-06-17
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {

    @Autowired
    private SystemdictionaryItemClient systemdictionaryItemClient;
    @Autowired
    private CourseDetailMapper courseDetailMapper;
    @Autowired
    private CourseDocClient courseDocClient;
    @Autowired
    private CourseTypeMapper courseTypeMapper;
    @Autowired
    private CourseMarketMapper courseMarketMapper;

    /**
     * 上线
     * @param ids
     */
    @Override
    @Transactional
    public void online(List<Long> ids) {
        //修改上线时间和状态
        baseMapper.online(ids,System.currentTimeMillis());
        //查询数据库中的数据
        List<Course> courses = baseMapper.selectBatchIds(ids);
        //上传到es中
        List<CourseDoc> courseDocs = courses2Docs(courses);
        AjaxResult ajaxResult = courseDocClient.add(courseDocs);
        if(!ajaxResult.isSuccess()){
            //手动回滚事务
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException(ajaxResult.getMessage());
        }
    }

    /**
     * 下线
     * @param ids
     */
    @Override
    @Transactional
    public void offline(List<Long> ids) {
        //修改下线时间和状态
        baseMapper.offline(ids,System.currentTimeMillis());
        //删除es
        AjaxResult ajaxResult = courseDocClient.delete(ids);
        if(!ajaxResult.isSuccess()){
            throw new RuntimeException(ajaxResult.getMessage());
        }
    }


    private List<CourseDoc> courses2Docs(List<Course> courses) {
        List<CourseDoc> courseDocs = new ArrayList<>();
        for (Course course : courses) {
            CourseDoc courseDoc = course2Doc(course);
            courseDocs.add(courseDoc);
        }
        return courseDocs;
    }

    private CourseDoc course2Doc(Course course) {
        CourseDoc courseDoc = new CourseDoc();
        //all字段
        CourseType courseType = courseTypeMapper.selectById(course.getCourseTypeId());
        String courseTypeName = "";
        if(courseType!=null){
            courseTypeName = courseType.getName();
        }
        String all = course.getName()+" "+courseTypeName+" "+course.getTenantName();
        courseDoc.setAll(all);

        BeanUtils.copyProperties(course,courseDoc);
        CourseMarket courseMarket = courseMarketMapper.selectOne(new QueryWrapper<CourseMarket>().eq("course_id", course.getId()));
        Float price = 0f;
        if(courseMarket!=null){
            price = courseMarket.getPrice();
        }
        courseDoc.setPrice(price);
        return courseDoc;
    }


    /**
     * 课程高级分页查询
     * @param query
     * @return
     */
    @Override
    public PageList<Course> page(CourseQuery query) {
        IPage<Course> iPage = baseMapper.selectPageByQuery(new Page<Course>(query.getPageNum(), query.getPageSize()), query);
        PageList<Course> pageList = new PageList<>();
        pageList.setTotal(iPage.getTotal());
        pageList.setRows(iPage.getRecords());
        return pageList;
    }

    /**
     * 添加课程基本信息
     * @param courseAddVo
     */
    @Override
    public void add(CourseAddVo courseAddVo) {
        //课程基本信息表
        Course course = new Course();
        BeanUtils.copyProperties(courseAddVo,course);
        SystemdictionaryItem systemdictionaryItem = systemdictionaryItemClient.get(courseAddVo.getGrade());
        if(systemdictionaryItem!=null){
            course.setGradeName(systemdictionaryItem.getName());
        }
        //当前登录的用户中获取用户的登录信息
        //后面会讲解单点登录，现在暂时把用户信息写死 TODO
        course.setTenantId(26L);
        course.setTenantName("源码时代");
        course.setUserId(42L);
        course.setUserName("admin");
        course.setStatus(0);
        baseMapper.insert(course);
        //课程详情表
        CourseDetail courseDetail = new CourseDetail();
        BeanUtils.copyProperties(courseAddVo,courseDetail);
        courseDetail.setCourseId(course.getId());
        courseDetailMapper.insert(courseDetail);
    }


}
