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
 * 成长值记录
 * </p>
 *
 * @author solargen
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_grow_log")
public class VipGrowLog implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("createTime")
    private Long createTime;

    /**
     * 登录用户
     */
    @TableField("ssoId")
    private Long ssoId;

    /**
     * 来源
     */
    @TableField("fromReason")
    private String fromReason;

    /**
     * 成长值
     */
    private Integer score;

    /**
     * 备注
     */
    private String remark;


}
