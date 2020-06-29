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
 * 课程收藏
 * </p>
 *
 * @author solargen
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_vip_course_collect")
public class VipCourseCollect implements Serializable {

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
     * 商品ID
     */
    @TableField("courseId")
    private Long courseId;


}
