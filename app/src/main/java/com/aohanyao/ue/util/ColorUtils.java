package com.aohanyao.ue.util;

import android.graphics.Color;

/**
 * Created by 江俊超 on 2017/9/28.
 * Version:1.0
 * Description:
 * ChangeLog:
 */

public class ColorUtils {
    /**
     * 根据当前的百分比 计算两个颜色之间的值
     *
     * @param fraction 分值
     * @param startValue 开始颜色
     * @param endValue 结束颜色
     * @return 计算得出的颜色
     */
    public static Integer evaluate(float fraction, Integer startValue, Integer endValue) {

        float[] startHsv = new float[3];
        float[] endHsv = new float[3];
        float[] outHsv = new float[3];

        // 把 ARGB 转换成 HSV
        Color.colorToHSV(startValue, startHsv);
        Color.colorToHSV(endValue, endHsv);

        // 根据当前的百分比（fraction）所对应的颜色值
        if (endHsv[0] - startHsv[0] > 180) {
            endHsv[0] -= 360;
        } else if (endHsv[0] - startHsv[0] < -180) {
            endHsv[0] += 360;
        }
        outHsv[0] = startHsv[0] + (endHsv[0] - startHsv[0]) * fraction;
        if (outHsv[0] > 360) {
            outHsv[0] -= 360;
        } else if (outHsv[0] < 0) {
            outHsv[0] += 360;
        }
        outHsv[1] = startHsv[1] + (endHsv[1] - startHsv[1]) * fraction;
        outHsv[2] = startHsv[2] + (endHsv[2] - startHsv[2]) * fraction;

        // 根据当前的百分比（fraction）所对应的透明度
        int alpha = startValue >> 24 + (int) ((endValue >> 24 - startValue >> 24) * fraction);

        // 把 HSV 转换回 ARGB 返回
        return Color.HSVToColor(alpha, outHsv);
    }
}
