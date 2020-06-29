package cn.itsource.hrm.controller;

import cn.itsource.hrm.service.IVipRealinfoService;
import cn.itsource.hrm.domain.VipRealinfo;
import cn.itsource.hrm.query.VipRealinfoQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vipRealinfo")
public class VipRealinfoController {
    @Autowired
    public IVipRealinfoService vipRealinfoService;

    /**
    * 保存和修改公用的
    * @param vipRealinfo  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody VipRealinfo vipRealinfo){
        try {
            if(vipRealinfo.getId()!=null){
                vipRealinfoService.updateById(vipRealinfo);
            }else{
                vipRealinfoService.save(vipRealinfo);
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
            vipRealinfoService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public VipRealinfo get(@PathVariable("id")Long id)
    {
        return vipRealinfoService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<VipRealinfo> list(){

        return vipRealinfoService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<VipRealinfo> page(@RequestBody VipRealinfoQuery query)
    {
        Page<VipRealinfo> page = vipRealinfoService.page(new Page<VipRealinfo>(query.getPageNum(), query.getPageSize()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
