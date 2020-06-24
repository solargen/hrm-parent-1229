package cn.itsource.hrm.controller;

import cn.itsource.hrm.controller.vo.CourseAddVo;
import cn.itsource.hrm.service.ICourseService;
import cn.itsource.hrm.domain.Course;
import cn.itsource.hrm.query.CourseQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    public ICourseService courseService;

    /**
     * 添加课程基本信息
     * @param courseAddVo
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public AjaxResult save(@RequestBody CourseAddVo courseAddVo){

        //对表单提交的内容做验证.........

        try {
            courseService.add(courseAddVo);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
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
            courseService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Course get(@PathVariable("id")Long id)
    {
        return courseService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Course> list(){

        return courseService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<Course> page(@RequestBody CourseQuery query)
    {
        return courseService.page(query);
    }

    /**
     * 上线
     * @param ids
     * @return
     */
    @PostMapping("/online")
    public AjaxResult online(@RequestBody List<Long> ids){
        try {
            courseService.online(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上线失败!"+e.getMessage());
        }
    }

    /**
     * 下线
     * @param ids
     * @return
     */
    @PostMapping("/offline")
    public AjaxResult offline(@RequestBody List<Long> ids){
        try {
            courseService.offline(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("下线失败!"+e.getMessage());
        }
    }

}
