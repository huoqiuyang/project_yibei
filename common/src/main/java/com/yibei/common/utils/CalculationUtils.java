package com.yibei.common.utils;

import java.text.DecimalFormat;

/**
 * 计算处理
 */
public class CalculationUtils {

    /**
     * 计算百分比
     * @param number 被除数
     * @param divisor 除数
     * @return 2位小数百分比，0时返回 '0%'
     * */
    public static String division(long number,long divisor){
        if(divisor>0 && number>0){
            try {
                DecimalFormat decimalFormat = new DecimalFormat("0.00%");
                String divide = decimalFormat.format((float) number / (float) divisor);
                return "0.00%".equals(divide)?"0%":divide;
            }catch (Exception e){
                e.printStackTrace();
                return "0%";
            }
        }else{
            return "0%";
        }
    }

    /**
     * 计算百分比
     * @param number 被除数
     * @param divisor 除数
     * @return 小数百分比，0时返回 '0%'
     * */
    public static String divisionFormat(long number,long divisor,String format){
        if(divisor>0 && number>0){
            try {
                DecimalFormat decimalFormat = new DecimalFormat(format);
                String divide = decimalFormat.format((float) number / (float) divisor);
                return "0.0%".equals(divide)?"0%":divide;
            }catch (Exception e){
                e.printStackTrace();
                return "0%";
            }
        }else{
            return "0%";
        }
    }


}
