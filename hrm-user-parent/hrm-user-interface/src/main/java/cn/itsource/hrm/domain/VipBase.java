package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 会员基本信息
 * </p>
 *
 * @author solargen
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_base")
public class VipBase implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("createTime")
    private Long createTime;

    @TableField("updateTime")
    private Long updateTime;

    /**
     * 登录账号
     */
    @TableField("ssoId")
    private Long ssoId;

    /**
     * 注册渠道
     */
    @TableField("regChannel")
    private Integer regChannel;

    /**
     * 注册时间
     */
    @TableField("regTime")
    private Long regTime;

    /**
     * QQ
     */
    private String qq;

    /**
     * 用户等级
     */
    private Integer level;

    /**
     * 成长值
     */
    @TableField("growScore")
    private Integer growScore;

    /**
     * 推荐人
     */
    @TableField("referId")
    private Long referId;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 居住地区域
     */
    @TableField("areaCode")
    private Integer areaCode;

    /**
     * 详细地址
     */
    private String address;


}
