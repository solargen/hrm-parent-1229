package cn.itsource.hrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-16
 */
@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
public class ZuulServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulServerApplication.class,args);
    }

}
