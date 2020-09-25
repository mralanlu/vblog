package com.northbund.vblog;

import com.github.pagehelper.PageHelper;
import com.northbund.vblog.common.annotation.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@MapperScan(annotationClass = Mapper.class, basePackages={"com.northbund.vblog.mapper"})
@SpringBootApplication
@EnableScheduling
public class VblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(VblogApplication.class, args);
    }

    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        /**
         * 配置mysql数据库的方言
         */
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }

    @Bean
    public SpelExpressionParser spelExpressionParser() {
        return new SpelExpressionParser(new SpelParserConfiguration(true, true));
    }

}
