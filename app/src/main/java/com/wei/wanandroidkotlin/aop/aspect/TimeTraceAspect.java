package com.wei.wanandroidkotlin.aop.aspect;

import android.util.Log;

import com.wei.wanandroidkotlin.aop.annotation.TimeTrace;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author XiangWei
 * @since 2018/9/18
 */
// 一、用@Aspect标注切面类
@Aspect
public class TimeTraceAspect {
    private final static String TAG = "TimeTraceAspect";
    // 二、在切面类中定义PointCut（切入点）
    // 语法：execution(@注解 访问权限 返回值的类型 包名.函数名(参数))
    // 表示：使用TimeTrace注解的任意类型返回值任意方法名（任意参数）
    // 切点表达式使用注解，一定是@+注解全路径
//    @Pointcut("execution(@com.wei.wanandroidkotlin.aop.annotation.TimeTrace * *(..))")
//    @Pointcut("execution(* com.wei.wanandroidkotlin.aop.AOPTest.computePlus(..))")
    @Pointcut("execution(* com.wei..*Plus(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        Log.e(TAG, "before pointcut ...");
    }

    /**
     * 三、在切面类中定义Advance(通知)
     * Advance比较常用的有：Before():方法执行前,After():方法执行后,Around():代替原有逻辑
     * @param joinPoint
     * @return
     */
    @Around("pointCut()")
    public Object dealPoint(ProceedingJoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取注解
        TimeTrace timeTraceAnnotation = methodSignature.getMethod().getAnnotation(TimeTrace.class);
        String value = timeTraceAnnotation.value();
        Object proceed  =null;
        try {
            // 执行原方法体
            proceed = joinPoint.proceed();
            long end = System.currentTimeMillis();
            StringBuffer stringBuffer = new StringBuffer();
            if (proceed instanceof Boolean) {
                if ((Boolean) proceed) {
                    stringBuffer.append(value)
                            .append("成功，耗时：")
                            .append(end - start);
                } else {
                    stringBuffer.append(value)
                            .append("失败，耗时：")
                            .append(end - start);
                }
            } else {
                stringBuffer.append(value)
                        .append("结果为：")
                        .append(proceed)
                        .append("，耗时：")
                        .append(end - start)
                        .append("ms");
            }
            Log.e(TAG, stringBuffer.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        Log.e(TAG, "after pointcut ...");
    }

    // returning的值必须和想接收的结果一样
    @AfterReturning(value = "pointCut()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        Log.e(TAG, "运算结果为 : " + result);
    }

    @AfterThrowing(value = "pointCut()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().getName();
        StringBuilder builder = new StringBuilder();
        builder.append("================== ")
                .append(methodName)
                .append(" 方法 -- AOP 异常后通知 ==============");
        Log.e(TAG, methodName + " 方法抛出异常为：\t" + ex.getMessage());
    }
}
