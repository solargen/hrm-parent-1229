package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.ISsoService;
import cn.itsource.hrm.domain.Sso;
import cn.itsource.hrm.query.SsoQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sso")
public class SsoController {
    @Autowired
    public ISsoService ssoService;

    /**
    * 保存和修改公用的
    * @param sso  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Sso sso){
        try {
            if(sso.getId()!=null){
                ssoService.updateById(sso);
            }else{
                ssoService.save(sso);
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
            ssoService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Sso get(@PathVariable("id")Long id)
    {
        return ssoService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Sso> list(){

        return ssoService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<Sso> page(@RequestBody SsoQuery query)
    {
        Page<Sso> page = ssoService.page(new Page<Sso>(query.getPageNum(), query.getPageSize()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
