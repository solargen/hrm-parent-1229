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
 * 登录记录
 * </p>
 *
 * @author solargen
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_login_log")
public class VipLoginLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("createTime")
    private Long createTime;

    @TableField("ssoId")
    private Long ssoId;

    /**
     * IP
     */
    private String ip;

    /**
     * 客户端
     */
    @TableField("clientInfo")
    private String clientInfo;

    /**
     * 登录方式
     */
    @TableField("loginType")
    private Integer loginType;

    /**
     * 登录是否成功
     */
    private Integer success;

    /**
     * 结果说明
     */
    private String remark;


}
