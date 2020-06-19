package cn.itsource.hrm.domain;

import java.math.BigDecimal;
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
@TableName("t_meal")
public class Meal implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("mealName")
    private String mealName;

    @TableField("mealPrice")
    private BigDecimal mealPrice;

    @TableField("expireDate")
    private Integer expireDate;

    private Integer status;


}
