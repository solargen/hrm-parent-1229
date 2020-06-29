package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IVipAddressService;
import cn.itsource.hrm.domain.VipAddress;
import cn.itsource.hrm.query.VipAddressQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipAddress")
public class VipAddressController {
    @Autowired
    public IVipAddressService vipAddressService;

    /**
    * 保存和修改公用的
    * @param vipAddress  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody VipAddress vipAddress){
        try {
            if(vipAddress.getId()!=null){
                vipAddressService.updateById(vipAddress);
            }else{
                vipAddressService.save(vipAddress);
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
            vipAddressService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public VipAddress get(@PathVariable("id")Long id)
    {
        return vipAddressService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<VipAddress> list(){

        return vipAddressService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<VipAddress> page(@RequestBody VipAddressQuery query)
    {
        Page<VipAddress> page = vipAddressService.page(new Page<VipAddress>(query.getPageNum(), query.getPageSize()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
