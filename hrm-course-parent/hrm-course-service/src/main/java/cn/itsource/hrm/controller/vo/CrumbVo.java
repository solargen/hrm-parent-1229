package cn.itsource.hrm.controller.vo;

import cn.itsource.hrm.domain.CourseType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-27
 */
@Data
public class CrumbVo {

    private CourseType currentType;

    private List<CourseType> otherTypes = new ArrayList<>();

}
