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
 * 收货地址
 * </p>
 *
 * @author solargen
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_address")
public class VipAddress implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("createTime")
    private Long createTime;

    @TableField("updateTime")
    private Long updateTime;

    /**
     * 登录用户
     */
    @TableField("ssoId")
    private Long ssoId;

    /**
     * 收货人
     */
    private String reciver;

    /**
     * 区域
     */
    @TableField("areaCode")
    private String areaCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 全地址
     */
    @TableField("fullAddress")
    private String fullAddress;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 备用手机号
     */
    @TableField("phoneBack")
    private String phoneBack;

    /**
     * 固定电话
     */
    private String tel;

    /**
     * 邮编
     */
    @TableField("postCode")
    private String postCode;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 是否默认
     */
    @TableField("defaultAddress")
    private Integer defaultAddress;


}
