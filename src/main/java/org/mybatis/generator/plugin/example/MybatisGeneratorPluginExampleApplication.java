package org.mybatis.generator.plugin.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "org.mybatis.generator.plugin.example.mapper")
public class MybatisGeneratorPluginExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisGeneratorPluginExampleApplication.class, args);
    }

}
