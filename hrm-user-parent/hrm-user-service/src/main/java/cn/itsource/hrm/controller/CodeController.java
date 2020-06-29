package cn.itsource.hrm.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.itsource.hrm.client.CacheClient;
import cn.itsource.hrm.util.AjaxResult;
import com.netflix.discovery.converters.Auto;
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

    /**
     * 加载图形验证码
     * 验证码有很多实现方式
     *  1、自己写
     *      不要钱，但是自己写的很low
     *  2、用别人提供的验证码服务
     *      高端大气上档次，但是要花钱
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




}
