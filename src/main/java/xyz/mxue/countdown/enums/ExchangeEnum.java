package xyz.mxue.countdown.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author mxuexxmy
 * @date  2023/3/26 0:17
 * @version 1.0
 */
public enum ExchangeEnum {

    /**
     * 倒计时
     */
    COUNTDOWN("countdown", "倒计时");
    /**
     * 交换机名称
     */
    final String name;

    public String getDescribe() {
        return describe;
    }

    public String getName() {
        return name;
    }

    /**
     * 交换机描述
     */
    final String describe;

    ExchangeEnum(String name, String describe) {
        this.name = name;
        this.describe = describe;
    }


    public static List<ExchangeEnum> toList() {
        return new ArrayList<>(Arrays.asList(ExchangeEnum.values()));
    }

}
