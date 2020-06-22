package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author solargen
 * @since 2020-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_course_market")
public class CourseMarket implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 课程id
     */
    private Long id;

    /**
     * 收费规则，对应数据字典,免费，收费
     */
    private Long charge;

    /**
     * 有效性，对应数据字典
     */
    private Long valid;

    /**
     * 咨询qq
     */
    private String qq;

    /**
     * 价格
     */
    private Float price;

    /**
     * 原价
     */
    private Float priceOld;

    /**
     * 过期时间
     */
    private Long expires;

    private Long courseId;


}
