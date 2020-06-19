package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ISystemdictionaryItemService;
import cn.itsource.hrm.domain.SystemdictionaryItem;
import cn.itsource.hrm.query.SystemdictionaryItemQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/systemdictionaryItem")
public class SystemdictionaryItemController {
    @Autowired
    public ISystemdictionaryItemService systemdictionaryItemService;

    /**
    * 保存和修改公用的
    * @param systemdictionaryItem  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody SystemdictionaryItem systemdictionaryItem){
        try {
            if(systemdictionaryItem.getId()!=null){
                systemdictionaryItemService.updateById(systemdictionaryItem);
            }else{
                systemdictionaryItemService.save(systemdictionaryItem);
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
            systemdictionaryItemService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public SystemdictionaryItem get(@PathVariable("id")Long id)
    {
        return systemdictionaryItemService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<SystemdictionaryItem> list(){

        return systemdictionaryItemService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<SystemdictionaryItem> page(@RequestBody SystemdictionaryItemQuery query)
    {
        Page<SystemdictionaryItem> page = systemdictionaryItemService.page(new Page<SystemdictionaryItem>(query.getPageNum(), query.getPageSize()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
