package com.ptp.weibo_epiphqny.HomePage;




import com.ptp.weibo_epiphqny.Utils.REGgetStringBetweenAandB;
import com.ptp.weibo_epiphqny.Utils.StringsExtensions.HasSignCharacter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//用于获取用户登录所需的cookie
public class GetTopSearchData {

    public void getCookie() {

        Iterator<String> it = getWeiboTopSearch().iterator();
        while(it.hasNext()){

            System.out.println(it.next());

        }

    }



    public static ArrayList<String> getWeiboTopSearch() {

        ArrayList<String> topItemsList = new ArrayList<String>();

        try {

            Document page = Jsoup.connect("https://weibo.cn/pub/").get();
            Elements topItems = page.select("div.c a");
            topItems.forEach(tI -> {

                if (HasSignCharacter.hasSignCharacter(tI.text(), '#') != -1) {

                    topItemsList.add(REGgetStringBetweenAandB.getStringBetweenAandB("#", "#", tI.text()));

                } else {

                    topItemsList.add(tI.text());

                }

            });


        } catch (IOException e) {

            e.printStackTrace();
            e.getMessage();

        }finally {

            return topItemsList;

        }

    }

}
