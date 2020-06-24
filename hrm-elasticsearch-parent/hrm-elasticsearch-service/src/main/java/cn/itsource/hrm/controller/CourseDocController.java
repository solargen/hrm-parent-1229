package cn.itsource.hrm.controller;

import cn.itsource.hrm.doc.CourseDoc;
import cn.itsource.hrm.repository.CourseDocRepository;
import cn.itsource.hrm.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-24
 */
@RestController
@RequestMapping("/course")
public class CourseDocController {

    @Autowired
    private CourseDocRepository repository;

    /**
     * 添加课程到es中
     * @param list
     * @return
     */
    @PostMapping("/addBatch")
    public AjaxResult add(@RequestBody List<CourseDoc> list){
        try {
            repository.saveAll(list);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败!"+e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/deleteBatch")
    public AjaxResult delete(@RequestBody List<Long> ids){
        try {
            for (Long id : ids) {
                repository.deleteById(id);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败!"+e.getMessage());
        }
    }


}
