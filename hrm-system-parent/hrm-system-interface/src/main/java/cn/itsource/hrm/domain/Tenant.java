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
@TableName("t_tenant")
public class Tenant implements Serializable {

    private static final long serialVersionUID=1L;

    private Long tenantType;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("companyName")
    private String companyName;

    @TableField("companyNum")
    private String companyNum;

    @TableField("registerTime")
    private Long registerTime;

    private Integer state;

    private String address;

    private String logo;


}
