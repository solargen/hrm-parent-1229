package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-16
 */
@Data
@TableName("t_employee")
public class Employee {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    @TableField("realName")
    private String realName;
    private String tel;
    private String email;
    @TableField("inputTime")
    private Date inputTime;
    private Integer state;
    private Long deptId;
    private Long tenantId;
    private Integer type;

}
