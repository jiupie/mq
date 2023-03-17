package com.wl.rk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/3/21
 */
@MapperScan(basePackages = {"com.wl.rk.mapper"})
@SpringBootApplication
public class RocketmqApplication {
    public static void main(String[] args) {
        SpringApplication.run(RocketmqApplication.class, args);
    }


}
