package cn.itsource.hrm.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.RandomUtil;
import cn.itsource.hrm.client.CacheClient;
import cn.itsource.hrm.client.SMSClient;
import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.StrUtils;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-29
 */
@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private CacheClient cacheClient;
    @Autowired
    private SMSClient smsClient;

    /**
     * 加载图形验证码
     * 验证码有很多实现方式
     *  1、自己写
     *      不要钱，但是自己写的很low
     *  2、用别人提供的验证码服务
     *      高端大气上档次，但是要花钱
     *      网易验证码
     *
     * @param uuid
     * @return
     */
    @GetMapping("/loadImageCode")
    public String loadImageCode(@RequestParam("uuid") String uuid){

        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

        //获取生成的验证码
        String code = lineCaptcha.getCode();

        String imageBase64 = lineCaptcha.getImageBase64();

        //保存redis
        //key :   CODE:IMAGECODE:uuid
        String key = "CODE:IMAGECODE:"+uuid;
        AjaxResult ajaxResult = cacheClient.setex(key, 300, code);
        return imageBase64;

    }

    /**
     * 发送短信验证码
     * @param uuid 浏览器的唯一标识
     * @param imageCode 图形验证码
     * @param telephone 手机号码
     * @return
     */
    @GetMapping("/sendSMSCode")
    public AjaxResult sendSMSCode(
            @RequestParam("uuid") String uuid,
            @RequestParam("imageCode")String imageCode,
            @RequestParam("telephone")String telephone){

        //一系列验证

        //图形验证码验证
        String key = "CODE:IMAGECODE:"+uuid;
        AjaxResult ajaxResult = cacheClient.get(key);
        if(!ajaxResult.isSuccess()){
            return AjaxResult.me().setSuccess(false).setMessage(ajaxResult.getMessage());
        }
        String imageCodeValue = (String) ajaxResult.getResultObj();
        if(StringUtils.isEmpty(imageCodeValue)){
            return AjaxResult.me().setSuccess(false).setMessage("图形验证码过期");
        }
        if(!imageCode.equals(imageCodeValue)){
            return AjaxResult.me().setSuccess(false).setMessage("图形验证码错误");
        }


        //获取短信验证码
        key = "CODE:REGCODE:"+telephone;
        ajaxResult = cacheClient.get(key);
        if(!ajaxResult.isSuccess()){
            return AjaxResult.me().setSuccess(false).setMessage(ajaxResult.getMessage());
        }
        String smsCodeValue = (String) ajaxResult.getResultObj();

        String smsCode = null;
        //判断是否存在
        if(StringUtils.isEmpty(smsCodeValue)){
            smsCode = RandomUtil.randomString("0123456789", 6);
        }else{
            String[] smsCodeValueArr = smsCodeValue.split(",");
            smsCode = smsCodeValueArr[0];
            long lastTime = Long.parseLong(smsCodeValueArr[1]);

            //判断是否重发
            long currentTime = System.currentTimeMillis();
            if(currentTime-lastTime<30000){
                return AjaxResult.me().setSuccess(false).setMessage("你爪子!");
            }
        }


        //重新保存redis
        String value = smsCode+","+System.currentTimeMillis();
        ajaxResult = cacheClient.setex(key, 300, value);
        if(!ajaxResult.isSuccess()){
            return AjaxResult.me().setSuccess(false).setMessage(ajaxResult.getMessage());
        }

        //发送短信
        return smsClient.sendRegCode(telephone, smsCode);

    }


}
