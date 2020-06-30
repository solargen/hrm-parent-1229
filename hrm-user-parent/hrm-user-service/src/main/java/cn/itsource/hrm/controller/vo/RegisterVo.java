package cn.itsource.hrm.controller.vo;

import lombok.Data;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-30
 */
@Data
public class RegisterVo {

    private String uuid;
    private String telephone;
    private String password;
    private String imageCode;
    private String smsCode;

}
