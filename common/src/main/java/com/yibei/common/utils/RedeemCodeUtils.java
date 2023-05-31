package com.yibei.common.utils;

/**
 * 随机数生成工具
 */
public class RedeemCodeUtils {

    /**
     *
     * @function 生成num位的随机字符串(数字、大写字母随机混排)
     * @param num
     * @return
     */
    public static String createBigSmallLetterStrOrNumberRadom(int num) {

        StringBuilder str = new StringBuilder();
        for(int i=1;i <= num;i++){
            int intVal=(int)(Math.random()*58+65);
            if(intVal >= 91 && intVal <= 96){
                i--;
            }
            if(intVal < 91 || intVal > 96){
                if(intVal%2==0){
                    str.append((char) intVal);
                }else{
                    str.append((int) (Math.random() * 10));
                }
//                if(i%4==0){
//                    str.append("-");
//                }
            }

        }
        return str.toString();
//        return str.substring(0,str.length()-1);
    }
    /**
     *
     * @function 生成num位的随机字符串(数字、小写字母随机混排)
     * @param num
     * @return
     */
    public static String createSmallStrOrNumberRadom(int num) {

        StringBuilder str = new StringBuilder();
        for(int i=1;i <= num;i++){
            int intVal=(int)(Math.random()*26+97);
            if(intVal%2==0){
                str.append((char) intVal);
            }else{
                str.append((int) (Math.random() * 10));
            }
//            if(i%4==0){
//                str.append("-");
//            }
        }
        return str.toString();
//        return str.substring(0,str.length()-1);
    }
    /**
     *
     * @function 生成num位的随机字符串(小写字母与数字混排)
     * @param num
     * @return
     */
    public static String createBigStrOrNumberRadom(int num) {

        StringBuilder str = new StringBuilder();
        for(int i=1;i <= num;i++){
            int intVal=(int)(Math.random()*26+65);
            if(intVal%2==0){
                str.append((char) intVal);
            }else{
                str.append((int) (Math.random() * 10));
            }
//            if(i%4==0){
//                str.append("-");
//            }
        }
        return str.toString();
//        return str.substring(0,str.length()-1);
    }

}
