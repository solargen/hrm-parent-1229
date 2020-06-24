package cn.itsource.hrm.doc;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Document的目的是和es中的数据进行映射
 *
 * es中数据存储的目的是前端展示与查询
 *
 * 所以document中属性分为两种：用来展示的    用来查询的
 *
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-24
 */
@Document(indexName = "hrm")
@Data
public class CourseDoc {

    //关键字查询的时候 (name type tenantName)   IO流 Java 源码时代 ===  IO 流  Java  源码 时代  源码时代
    private String all;

    private Long id;

    private String name;
    private String users;

    private Long courseTypeId;

    private String gradeName;
    private Long grade;

    private String tenantName;
    private Long tenantId;

    private Long startTime;
    private String pic;

    private Float price;





}
