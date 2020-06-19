package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ISystemdictionaryService;
import cn.itsource.hrm.domain.Systemdictionary;
import cn.itsource.hrm.query.SystemdictionaryQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemdictionary")
public class SystemdictionaryController {
    @Autowired
    public ISystemdictionaryService systemdictionaryService;

    /**
    * 保存和修改公用的
    * @param systemdictionary  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Systemdictionary systemdictionary){
        try {
            if(systemdictionary.getId()!=null){
                systemdictionaryService.updateById(systemdictionary);
            }else{
                systemdictionaryService.save(systemdictionary);
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
            systemdictionaryService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Systemdictionary get(@PathVariable("id")Long id)
    {
        return systemdictionaryService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Systemdictionary> list(){

        return systemdictionaryService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<Systemdictionary> page(@RequestBody SystemdictionaryQuery query)
    {
        Page<Systemdictionary> page = systemdictionaryService.page(new Page<Systemdictionary>(query.getPageNum(), query.getPageSize()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
