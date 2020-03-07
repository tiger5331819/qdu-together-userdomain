package qdu.together.userdomain.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import qdu.together.userdomain.core.UserDomainCore;

@SpringBootApplication
@MapperScan("qdu.mapping")
@ComponentScan("qdu.together.net")
@ComponentScan("qdu.together.togethercore.amqp")
@ComponentScan("qdu.together.userdomain")
public class Application {

    public static void main(String[] args){
        UserDomainCore.getInstance().run(SpringApplication.run(Application.class, args));
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
