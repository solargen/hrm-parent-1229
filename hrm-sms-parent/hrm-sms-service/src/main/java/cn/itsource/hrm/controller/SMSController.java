package cn.itsource.hrm.controller;

import cn.itsource.hrm.util.AjaxResult;
import cn.itsource.hrm.util.SMSUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-29
 */
@RestController
public class SMSController {

    /**
     * 注册发送短信验证码
     * @param phoneNum
     * @param code
     * @return
     */
    @GetMapping("/sendRegCode")
    public AjaxResult sendRegCode(@RequestParam("phoneNum") String phoneNum, @RequestParam("code") String code){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("code",code);

        String param = JSONObject.toJSONString(paramMap);
        try {
            SMSUtil.INSTANCE.sendSMS(
                    phoneNum,
                    "solargen",
                    "SMS_187756530",
                    param
            );
            return AjaxResult.me().setSuccess(true).setMessage("成功!");
        } catch (ClientException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败!"+e.getMessage());
        }
    }

}
