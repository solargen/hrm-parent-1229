package cn.itsource.hrm.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-29
 */
public enum  SMSUtil {

    INSTANCE;

    /**
     * 短信发送
     * @param phoneNum 手机号码
     * @param signName 签名
     * @param template 模板
     * @param param 模板参数
     */
    public void sendSMS(String phoneNum,String signName,String template,String param) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIuwxwT8vTMZz1", "KuDcictgkUTbzlgIYhpS1pZGn8MHAH");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", template);
        request.putQueryParameter("TemplateParam", param);
        CommonResponse response = client.getCommonResponse(request);
    }
}
