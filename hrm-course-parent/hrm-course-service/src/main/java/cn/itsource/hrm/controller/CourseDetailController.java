package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ICourseDetailService;
import cn.itsource.hrm.domain.CourseDetail;
import cn.itsource.hrm.query.CourseDetailQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseDetail")
public class CourseDetailController {
    @Autowired
    public ICourseDetailService courseDetailService;

    /**
    * 保存和修改公用的
    * @param courseDetail  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody CourseDetail courseDetail){
        try {
            if(courseDetail.getId()!=null){
                courseDetailService.updateById(courseDetail);
            }else{
                courseDetailService.save(courseDetail);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            courseDetailService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CourseDetail get(@PathVariable("id")Long id)
    {
        return courseDetailService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<CourseDetail> list(){

        return courseDetailService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<CourseDetail> page(@RequestBody CourseDetailQuery query)
    {
        Page<CourseDetail> page = courseDetailService.page(new Page<CourseDetail>(query.getPageNum(), query.getPageSize()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
