package com.apih5.aspect;

import cn.hutool.core.bean.BeanUtil;
import com.apih5.annotation.CheckOnlyPoint;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.CheckOnly;
import com.apih5.service.CheckOnlyService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class CheckOnlyAspect {

    @Autowired(required = true)
    private CheckOnlyService checkOnlyService;

    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     *
     * 1、execution(public * *(..)) 任意的公共方法
     * 2、execution（* set*（..）） 以set开头的所有的方法
     * 3、execution（* com.apih5.annotation.LoggerApply.*（..））com.apih5.annotation.LoggerApply这个类里的所有的方法
     * 4、execution（* com.apih5.annotation.*.*（..））com.apih5.annotation包下的所有的类的所有的方法
     * 5、execution（* com.apih5.annotation..*.*（..））com.apih5.annotation包及子包下所有的类的所有的方法
     * 6、execution(* com.apih5.annotation..*.*(String,?,Long)) com.apih5.annotation包及子包下所有的类的有三个参数，第一个参数为String类型，第二个参数为任意类型，第三个参数为Long类型的方法
     * 7、execution(@annotation(com.apih5.annotation.apih5))
     */
    @Pointcut("@annotation(com.apih5.annotation.CheckOnlyPoint)")
    private void aspectjMethod(){};
    /**
     * 前置通知：在目标方法执行前调用
     */
    @Before("aspectjMethod()")
    public void before(){
        System.out.println("==@Before== lingyejun blog logger : begin");
    }

    /**
     * 后置通知：在目标方法执行后调用，若目标方法出现异常，则不执行
     */
    @AfterReturning(value = "aspectjMethod()", returning = "returnValue")
    public void log(JoinPoint point, Object returnValue){

    }
    /**
     * 后置/最终通知：无论目标方法在执行过程中出现一场都会在它之后调用
     */
    @After(value = "aspectjMethod()")
    public void after(JoinPoint point){
    }
    /**
     * 异常通知：目标方法抛出异常时执行
     */
    @AfterThrowing(value = "aspectjMethod()", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
    }
    /**
     * 环绕通知：灵活自由的在目标方法中切入代码
     */
    @Around(value = "aspectjMethod()")
    public ResponseEntity around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取目标方法的名称
        String methodName = joinPoint.getSignature().getName();
        // 获取方法传入参数
        Object[] params = joinPoint.getArgs();
        CheckOnlyPoint checkOnlyPoint = getDeclaredAnnotation(joinPoint);
        CheckOnly checkOnly = new CheckOnly();
        checkOnly.setObjName(checkOnlyPoint.objName());
        checkOnly.setCondStr(checkOnlyPoint.condStr());
        checkOnly.setMessage(checkOnlyPoint.message());
        System.out.println("==@Around== lingyejun blog logger --》 method name " + methodName + " args " + params[0]+params[0].getClass());
        ResponseEntity entity = checkOnlyService.checkOnlyForAspect(checkOnly,params[0]);

        if(entity.isSuccess()){
            // 执行源方法
            entity = (ResponseEntity) joinPoint.proceed();
        }
        // 模拟进行验证
        /*执行后*/
        return entity;
    }
    /**
     * 获取方法中声明的注解
     *
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    public CheckOnlyPoint getDeclaredAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 反射获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        // 拿到方法对应的参数类型
        Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 根据类、方法、参数类型（重载）获取到方法的具体信息
        Method objMethod = targetClass.getMethod(methodName, parameterTypes);
        // 拿到方法定义的注解信息
        CheckOnlyPoint annotation = objMethod.getDeclaredAnnotation(CheckOnlyPoint.class);
        // 返回
        return annotation;
    }
}