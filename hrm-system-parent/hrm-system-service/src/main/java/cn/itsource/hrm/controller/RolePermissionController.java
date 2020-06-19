package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IRolePermissionService;
import cn.itsource.hrm.domain.RolePermission;
import cn.itsource.hrm.query.RolePermissionQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {
    @Autowired
    public IRolePermissionService rolePermissionService;

    /**
    * 保存和修改公用的
    * @param rolePermission  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody RolePermission rolePermission){
        try {
            if(rolePermission.getId()!=null){
                rolePermissionService.updateById(rolePermission);
            }else{
                rolePermissionService.save(rolePermission);
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
            rolePermissionService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public RolePermission get(@PathVariable("id")Long id)
    {
        return rolePermissionService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<RolePermission> list(){

        return rolePermissionService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<RolePermission> page(@RequestBody RolePermissionQuery query)
    {
        Page<RolePermission> page = rolePermissionService.page(new Page<RolePermission>(query.getPageNum(), query.getPageSize()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
