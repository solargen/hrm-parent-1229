package cn.itsource.hrm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-16
 */
@SpringBootApplication
@MapperScan("cn.itsource.hrm.mapper")
public class SystemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemServiceApplication.class,args);
    }

}
