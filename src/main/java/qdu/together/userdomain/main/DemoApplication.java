package qdu.together.userdomain.main;

import java.io.IOException;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import qdu.together.userdomain.core.UserDomainCore;

@SpringBootApplication
@MapperScan("qdu.mapping")
@ComponentScan("qdu")
public class DemoApplication {

    public static void main(String[] args) throws IOException {
        UserDomainCore core = UserDomainCore.getInstance();
        core.setApplicationContext(SpringApplication.run(DemoApplication.class, args));
    }
    
/*     @Bean
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
