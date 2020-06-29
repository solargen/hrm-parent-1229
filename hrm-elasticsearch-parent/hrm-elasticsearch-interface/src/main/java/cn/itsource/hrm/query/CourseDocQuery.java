package cn.itsource.hrm.query;

import lombok.Data;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-29
 */
@Data
public class CourseDocQuery extends BaseQuery{

    //课程类型
    private Long courseType;

    private Float maxPrice;

    private Float minPrice;

    private Long tenantId;

    /**
     * 排序的列名
     *     xp 新品
     *     jg 价格
     */
    private String columnName;
    /**
     * 排序方式
     *      asc  升序
     *      desc  降序
     */
    private String orderType;

}
