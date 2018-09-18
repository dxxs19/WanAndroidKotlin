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
        if ("xxx".equals(name) && "123".equals(pass)) {
            return true;
        }
        return false;
    }
}
