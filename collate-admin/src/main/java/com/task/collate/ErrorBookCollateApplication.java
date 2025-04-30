package com.task.collate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 启动程序
 * 
 * @author  
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class ErrorBookCollateApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ErrorBookCollateApplication.class, args);
        System.out.println("\n" +
                "=========================================\n" +
                "🚀 错题本系统已启动\n" +
                "-----------------------------------------\n" +
                "📅 启动时间: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" +
                "🌐 访问地址: http://localhost:8080\n" +
                "=========================================");
    }
}
