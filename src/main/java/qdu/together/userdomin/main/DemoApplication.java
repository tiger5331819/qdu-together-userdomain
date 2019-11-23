package qdu.together.userdomin.main;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import qdu.together.userdomin.core.UserDomainCore;

@SpringBootApplication
@MapperScan("qdu.mapping")
public class DemoApplication {

    public static void main(String[] args) throws IOException {    
        UserDomainCore core=UserDomainCore.getInstance();
        core.setApplicationContext(SpringApplication.run(DemoApplication.class, args));
    }
    
    /* @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    } */
}