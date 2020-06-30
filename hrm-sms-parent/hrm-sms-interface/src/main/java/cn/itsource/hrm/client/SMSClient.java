package cn.itsource.hrm.client;

import cn.itsource.hrm.client.impl.SMSClientImpl;
import cn.itsource.hrm.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sms-service",fallback = SMSClientImpl.class)
public interface SMSClient {


    /**
     * 注册发送短信验证码
     * @param phoneNum
     * @param code
     * @return
     */
    @GetMapping("/sendRegCode")
    AjaxResult sendRegCode(@RequestParam("phoneNum") String phoneNum, @RequestParam("code") String code);
}
