package com.tpl.ssm.anno.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 主键生成器
 * <p>
 * https://blog.csdn.net/weixin_42871989/article/details/100133736
 * 本类用于获取mysql全局自增主键，格式为
 * 时间戳(yyMMddHHmm) + 5 位自增，自增范围根据配置获取(也可以加上秒yyMMddHHmmss)
 */
public class MajorKeyUtil {

    private static final DateFormat df = new SimpleDateFormat("yyMMddHHmm");//主键时间戳
    private static final int MAX_SEQ = 0; // 计数位最大值
    private static final int RESET_SEQ = 99999; //计数位重置初始值(可以自行设置)
    private static final AtomicInteger seq = new AtomicInteger(RESET_SEQ); //计数器


    /**
     * 生成全局自增主键,字符串类型
     *
     * @return 生成的主键
     */
    public static String getSeq() {
        seq.compareAndSet(MAX_SEQ, RESET_SEQ); //判断是否进行重置
        return df.format(new Date()) + String.format("%05d", seq.incrementAndGet());
    }

    /**
     * 生成全局自增主键,long类型
     *
     * @return 生成的主键
     */
    public static Long idSeq() {
        String str = getSeq();
        return Long.parseLong(str);
    }

}
