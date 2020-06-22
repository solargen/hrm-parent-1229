package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ICourseMarketService;
import cn.itsource.hrm.domain.CourseMarket;
import cn.itsource.hrm.query.CourseMarketQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courseMarket")
public class CourseMarketController {
    @Autowired
    public ICourseMarketService courseMarketService;

    /**
    * 保存和修改公用的
    * @param courseMarket  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody CourseMarket courseMarket){
        try {
            if(courseMarket.getId()!=null){
                courseMarketService.updateById(courseMarket);
            }else{
                courseMarketService.save(courseMarket);
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
            courseMarketService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public CourseMarket get(@PathVariable("id")Long id)
    {
        return courseMarketService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<CourseMarket> list(){

        return courseMarketService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<CourseMarket> page(@RequestBody CourseMarketQuery query)
    {
        Page<CourseMarket> page = courseMarketService.page(new Page<CourseMarket>(query.getPageNum(), query.getPageSize()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
