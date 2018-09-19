package com.wei.wanandroidkotlin.aop;

import com.wei.wanandroidkotlin.aop.annotation.TimeTrace;

/**
 * @author XiangWei
 * @since 2018/9/18
 */
public class AOPTest {

    @TimeTrace("登录...")
    public static boolean login(String name, String pass) {
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "xxx".equals(name) && "123".equals(pass);
    }

    @TimeTrace("累加运算结束，...")
    public static long computePlus(int from, int to) {
        int result = 0;
        for ( ; from <= to; from ++)
        {
            result += from;
        }
        return result;
    }
}
