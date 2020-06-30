package cn.itsource.hrm.client.impl;

import cn.itsource.hrm.client.SMSClient;
import cn.itsource.hrm.util.AjaxResult;
import org.springframework.stereotype.Component;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-30
 */
@Component
public class SMSClientImpl implements SMSClient {
    @Override
    public AjaxResult sendRegCode(String phoneNum, String code) {
        return AjaxResult.me().setSuccess(false).setMessage("系统异常!");
    }
}
