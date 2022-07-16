package com.ptp.weibo_epiphqny.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class REGgetStringBetweenAandB {

    //用于获取A与Ｂ之间的字符串
    public static String getStringBetweenAandB(String previousSign, String nextSign, String context) {

        String reg = "(?<=" + previousSign + ").*?(?=" + nextSign + ")";//自定义正则表达式
        Pattern regPattern = Pattern.compile(reg);//解析正则格式
        Matcher regMatcher = regPattern.matcher(context);//开始匹配符合本正则规则的字符串
        if (regMatcher.find()) {//如果存在符合正则规则的字符串，则将其返回

            return regMatcher.group();

        } else {//如果不存在，则将返回null

            return null;

        }

    }

}
