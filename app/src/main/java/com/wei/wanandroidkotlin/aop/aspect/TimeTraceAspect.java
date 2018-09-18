package com.wei.wanandroidkotlin.aop.aspect;

import android.util.Log;

import com.wei.wanandroidkotlin.aop.annotation.TimeTrace;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
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
    @Pointcut("execution(@com.wei.wanandroidkotlin.aop.annotation.TimeTrace * *(..))")
    public void pointCut() {
    }

//    @Before("pointCut()")
//    public void before(JoinPoint joinPoint) {
//        Log.e(TAG, "before pointcut ...");
//    }
//
//    @After("pointCut()")
//    public void after(JoinPoint joinPoint) {
//        Log.e(TAG, "after pointcut ...");
//    }

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
        Object proceed = null;
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
            }
            Log.e(TAG, stringBuffer.toString());
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return proceed;
    }
}
