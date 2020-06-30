package cn.itsource.hrm.controller;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import cn.itsource.hrm.client.CacheClient;
import cn.itsource.hrm.controller.vo.LoginVo;
import cn.itsource.hrm.controller.vo.RegisterVo;
import cn.itsource.hrm.service.ISsoService;
import cn.itsource.hrm.domain.Sso;
import cn.itsource.hrm.query.SsoQuery;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sso")
public class SsoController {
    @Autowired
    public ISsoService ssoService;
    @Autowired
    private CacheClient cacheClient;

    /**
     * 手机号码注册
     * @return
     */
    @PostMapping("/telephoneRegister")
    public AjaxResult telephoneRegister(@RequestBody RegisterVo vo){

        // 判断图形验证码
        //图形验证码验证
        String key = "CODE:IMAGECODE:"+vo.getUuid();
        AjaxResult ajaxResult = cacheClient.get(key);
        if(!ajaxResult.isSuccess()){
            return AjaxResult.me().setSuccess(false).setMessage(ajaxResult.getMessage());
        }
        String imageCodeValue = (String) ajaxResult.getResultObj();
        if(StringUtils.isEmpty(imageCodeValue)){
            return AjaxResult.me().setSuccess(false).setMessage("图形验证码过期");
        }
        if(!vo.getImageCode().equalsIgnoreCase(imageCodeValue)){
            return AjaxResult.me().setSuccess(false).setMessage("图形验证码错误");
        }

        //判断短信验证码
        key = "CODE:REGCODE:"+vo.getTelephone();
        ajaxResult = cacheClient.get(key);
        if(!ajaxResult.isSuccess()){
            return AjaxResult.me().setSuccess(false).setMessage(ajaxResult.getMessage());
        }
        String smsCodeValue = (String) ajaxResult.getResultObj();
        if(StringUtils.isEmpty(smsCodeValue)){
            return AjaxResult.me().setSuccess(false).setMessage("短信验证码过期");
        }
        String smsCode = smsCodeValue.split(",")[0];
        if(!vo.getSmsCode().equalsIgnoreCase(smsCode)){
            return AjaxResult.me().setSuccess(false).setMessage("短信验证码错误");
        }

        //交给service进行注册
        try {
            ssoService.telephoneReg(vo);
            return AjaxResult.me().setSuccess(true).setMessage("注册成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("注册失败！"+e.getMessage());
        }
    }

    /**
     * 登录
     * @param vo
     * @return
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginVo vo){

        //根据name查询登录信息
        Sso sso = ssoService.getByName(vo.getName());
        if(sso==null){
            return AjaxResult.me().setSuccess(false).setMessage("用户名或密码错误");
        }

        String salt = sso.getSalt();
        String password = SecureUtil.md5(vo.getPassword() + salt);
        if(!password.equals(sso.getPassword())){
            return AjaxResult.me().setSuccess(false).setMessage("用户名或密码错误");
        }

        //生成token
        String token = UUID.randomUUID().toString();
        //用户信息保存到redis中
        sso.setPassword(null);
        sso.setSalt(null);

        try(
                ByteArrayOutputStream byteOutputStream=new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream =new ObjectOutputStream(byteOutputStream)) {

            objectOutputStream.writeObject(sso);
            byte[] bytes = byteOutputStream.toByteArray();

            String key = "USER:"+token;
            AjaxResult ajaxResult = cacheClient.setBytes(key,600,bytes);
            //将后端生成的token响应给前端
            if(ajaxResult.isSuccess()){
                ajaxResult.setResultObj(token);
            }
            return ajaxResult;
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败"+e.getMessage());
        }
    }





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
