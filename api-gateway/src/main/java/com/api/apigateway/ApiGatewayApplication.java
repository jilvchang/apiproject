package com.api.apigateway;

import com.apiproject.demo.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@EnableDubbo
@SpringBootApplication
public class ApiGatewayApplication {
    @DubboReference
    private DemoService demoService;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ApiGatewayApplication.class);
        ApiGatewayApplication application = run.getBean(ApiGatewayApplication.class);
        String result1 = application.sayHello("api");
        String result2 = application.sayHello2("api");
        System.out.println("Receive result ======> " + result1);
        System.out.println("Receive result ======> " + result2);
    }

    public String sayHello(String name){
        return demoService.sayHello(name+"1");
    }
    public String sayHello2(String name){
        return demoService.sayHello(name+"2");
    }
        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
            return builder.routes()
                    .route("tobaidu", r -> r.path("/baidu")
                            .uri("http://www.baidu.com"))
                    .route("host_route", r -> r.host("*.myhost.org")
                            .uri("http://httpbin.org"))
                    .route("rewrite_route", r -> r.host("*.rewrite.org")
                             .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
                            .uri("http://httpbin.org"))


                    .build();
        }
}
