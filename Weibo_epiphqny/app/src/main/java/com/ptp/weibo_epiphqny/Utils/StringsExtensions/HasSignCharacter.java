package com.ptp.weibo_epiphqny.Utils.StringsExtensions;


//用于判断所提供字符串中是否含有指定的字符，若含有，则返回该字符第一次出现的下标；若不含有，则返回-1.
public class HasSignCharacter {


    public static int hasSignCharacter(String context, char signCharacter) {

        char[] charArray = context.toCharArray();
        for (int k = 0; k < charArray.length; k++) {

            if (charArray[k] == signCharacter) {

                return k;

            }

        }

        return -1;


    }


}
