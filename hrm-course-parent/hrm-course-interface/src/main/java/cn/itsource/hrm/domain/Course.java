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
 * @since 2020-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_course")
public class Course implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 适用人群
     */
    private String users;

    /**
     * 课程大分类
     */
    private Long courseTypeId;

    @TableField("gradeName")
    private String gradeName;

    /**
     * 课程等级
     */
    private Long grade;

    /**
     * 课程状态
     */
    private Integer status;

    /**
     * 教育机构
     */
    private Long tenantId;

    @TableField("tenantName")
    private String tenantName;

    /**
     * 创建用户
     */
    private Long userId;

    @TableField("userName")
    private String userName;

    private Long startTime;

    private Long endTime;

    private String pic;

    @TableField(exist = false)
    private CourseType courseType;


}
