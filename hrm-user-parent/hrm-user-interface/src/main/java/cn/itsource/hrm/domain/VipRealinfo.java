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
 * 会员实名资料
 * </p>
 *
 * @author solargen
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_realinfo")
public class VipRealinfo implements Serializable {

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
     * 真实姓名
     */
    @TableField("realName")
    private String realName;

    /**
     * 身份证号
     */
    @TableField("idCardNo")
    private String idCardNo;

    /**
     * 身份证正面
     */
    @TableField("idCardFront")
    private String idCardFront;

    /**
     * 身份证反面
     */
    @TableField("idCardBack")
    private String idCardBack;

    /**
     * 手持身份证
     */
    @TableField("idCardHand")
    private String idCardHand;

    /**
     * 审核状态
     */
    private Integer state;

    /**
     * 提交时间
     */
    @TableField("applyTime")
    private Long applyTime;

    /**
     * 审核时间
     */
    @TableField("auditTime")
    private Long auditTime;

    /**
     * 审核人
     */
    @TableField("auditUser")
    private String auditUser;

    /**
     * 审核备注
     */
    private String remark;


}
