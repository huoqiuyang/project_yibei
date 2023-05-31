package com.yibei.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 编号公用类
 */
public class CodesUtil {


    /** 订单类别头 */
    private static final String ORDER_CODE = "1";
    /** 退货类别头 */
    private static final String RETURN_ORDER = "2";
    /** 退款类别头 */
    private static final String REFUND_ORDER = "3";
    /** 服务费类别头 */
    private static final String SERVICE_ORDER = "4";
    /** 打款类别头 */
    private static final String REMIT_ORDER = "5";
    /** 随即编码 */
    private static final int[] r = new int[]{7, 9, 6, 2, 8 , 1, 3, 0, 5, 4};
    /** 用户id和随机数总长度 */
    private static final int maxLength = 3;



    /**
     *商品编号
     */
    public static String getProductCode(){
        return String.valueOf(getRandom(10000000,20000000));
    }

    /**
     *商品库编号
     */
    public static String getProductSkuCode(){
        return String.valueOf(getRandom(20000000,30000000));
    }

    /**
     *商品库编号
     */
    public static String getMerchantCode(){
        return String.valueOf(getRandom(30000000,40000000));
    }

    /**
     * 范围随机数
     */
    public static int getRandom(int min, int max){
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 随机数
     */
    public static String getRandomNumber(int length) {
        double random = Math.random();
        for (int i = 0; i < length; i++) {
            random = random * 10;
        }
        return String.valueOf((int) random);
    }


    /**
     * 更具id进行加密+加随机数组成固定长度编码
     */
    private static String toCode(Integer id) {
        String idStr = id.toString();
        StringBuilder idsbs = new StringBuilder();
        for (int i = idStr.length() - 1 ; i >= 0; i--) {
            idsbs.append(r[idStr.charAt(i)-'0']);
        }
        return idsbs.append(getRandom(maxLength - idStr.length())).toString();
    }

    /**
     * 生成时间戳
     */
    private static String getDateTime(){
        DateFormat sdf = new SimpleDateFormat("yyMMdd");
        return sdf.format(new Date());
    }

    /**
     * 生成临时id
     */
    public static Long getTemporaryId(){
        DateFormat sdf = new SimpleDateFormat("dHHmmss");
        String format = sdf.format(new Date()) + getRandom(3);
        return Long.valueOf(format);
    }

    /**
     * 生成固定长度随机码
     * @param n    长度
     */

    private static long getRandom(long n) {
        long min = 1,max = 9;
        for (int i = 1; i < n; i++) {
            min *= 10;
            max *= 10;
        }
        long rangeLong = (((long) (new Random().nextDouble() * (max - min)))) + min ;
        return rangeLong;
    }

    /**
     * 生成不带类别标头的编码
     * @param userId
     */
    public static synchronized String getCode(Integer userId){
        userId = userId == null ? 1000 : userId;
        return System.currentTimeMillis() / 1000 + userId + String.valueOf(getRandom(4));
    }

    /**
     * 生成订单单号编码
     * @param userId
     */

    public static String getOrderCode(Integer userId){
        return ORDER_CODE + getCode(userId);
    }

    /**
     * 生成订单单号编码
     * @param userId
     */

    public static String getAccountOrderCode(Integer userId){
        return RETURN_ORDER + getCode(userId);
    }

    /**
     * 生成退款单号编码
     * @param userId
     */
    public static String getRefundCode(Integer userId){
        return REFUND_ORDER + getCode(userId);
    }

    /**
     * 生成服务费单号编码
     * @param userId
     */
    public static String getServiceCode(Integer userId){
        return SERVICE_ORDER + getCode(userId);
    }

    /**
     * 生成打款单号编码
     * @param userId
     */
    public static String getRemitCode(Integer userId){
        return REMIT_ORDER + getCode(userId);
    }

    /**
     * 生成随机用户编码
     * @param userId
     */
    public static String getUserCode(Integer userId){
        return getRandomNumber(4) + getCode(userId);
    }

    /**
     * 不能超过三位连续同一个数字 或者三位以上顺序倒序数字组合
     * @param str
     * @return
     */
    public static boolean isLxAndXt(Long str){
        String[] split = str.toString().split("");
        int last = 0;
        int lx = 1;
        int xt =1;
        for (String s : split) {
            if(lx > 3 || xt > 3){
                return false;
            }
            int i = Integer.parseInt(s);
            int j = i-1;
            if(j == last){
                lx++;
            }else{
                lx = 1;
            }
            if(i == last){
                xt++;
            }else{
                xt = 1;
            }
            last = i;
        }
        if(lx > 3 || xt > 3){
            return false;
        }
        return true;
    }

    /**
     * 获取用户id
     * @return
     */
    public static String getCoding(){
        long random = getRandom(1000, 99999999);
        boolean lxAndXt = isLxAndXt(random);
        int i = 0;
        while (i<99){
            i++;
            if(!lxAndXt){
                random = getRandom(1000, 99999999);
                lxAndXt = isLxAndXt(random);
            }else{
                break;
            }
        }
        return String.valueOf(random);
    }

}
