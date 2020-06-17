package cn.itsource.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-17
 */
@SpringBootApplication
@MapperScan("cn.itsource.hrm.mapper")
public class CourseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseServiceApplication.class,args);
    }

}
