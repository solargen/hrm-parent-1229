package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IEmployeeRoleService;
import cn.itsource.hrm.domain.EmployeeRole;
import cn.itsource.hrm.query.EmployeeRoleQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeeRole")
public class EmployeeRoleController {
    @Autowired
    public IEmployeeRoleService employeeRoleService;

    /**
    * 保存和修改公用的
    * @param employeeRole  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody EmployeeRole employeeRole){
        try {
            if(employeeRole.getId()!=null){
                employeeRoleService.updateById(employeeRole);
            }else{
                employeeRoleService.save(employeeRole);
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
            employeeRoleService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public EmployeeRole get(@PathVariable("id")Long id)
    {
        return employeeRoleService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<EmployeeRole> list(){

        return employeeRoleService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<EmployeeRole> page(@RequestBody EmployeeRoleQuery query)
    {
        Page<EmployeeRole> page = employeeRoleService.page(new Page<EmployeeRole>(query.getPageNum(), query.getPageSize()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
