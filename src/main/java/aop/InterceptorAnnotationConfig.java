package aop;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 切点注入 也可以 继承 AbstractPointcutAdvisor
 * @author shijinjin@cloudwalk.cn
 * @date 2020/8/31
 * @since 1.0
 */
@Configuration
public class InterceptorAnnotationConfig {
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor3() {
        TestInterceptor interceptor = new TestInterceptor();
        //定义切入点表达式
        //见spring-aop
       // AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        //注解切入点
         AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(Override.class, true);
        // 配置增强类advisor
        //        AnnotationMatchingPointcut.forMethodAnnotation(MethodCustomAnnotation.class);<br> 　　　　  //基于class级别的注解
//        AnnotationMatchingPointcut.forClassAnnotation(ClazzCustomAnnotation.class);
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice(interceptor);
        return advisor;
    }
}
