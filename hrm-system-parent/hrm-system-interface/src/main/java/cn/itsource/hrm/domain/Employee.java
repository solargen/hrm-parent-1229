package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author solargen
 * @since 2020-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_employee")
public class Employee implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    @TableField("realName")
    private String realName;

    private String tel;

    private String email;

    @TableField("inputTime")
    private Long inputTime;

    private Integer state;

    private Long deptId;

    private Long tenantId;


}
