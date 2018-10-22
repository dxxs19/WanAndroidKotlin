package com.wei.wanandroidkotlin.widgets.imagedrag;

import com.wei.wanandroidkotlin.App;

/**
 * 公共工具类
 * Created by kuyue on 2017/7/13 上午10:18.
 * 邮箱:595327086@qq.com
 **/
public class CommonUtils {

    /**
     * 获取dimens定义的大小
     *
     * @param dimensionId
     * @return
     */
    public static int getPixelById(int dimensionId) {
        return App.Companion.getAppContext().getResources().getDimensionPixelSize(dimensionId);
    }

}
