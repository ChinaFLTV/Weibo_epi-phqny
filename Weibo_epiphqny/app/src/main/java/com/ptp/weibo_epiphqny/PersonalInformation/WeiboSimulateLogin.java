package com.ptp.weibo_epiphqny.PersonalInformation;


import com.ptp.weibo_epiphqny.Utils.DownloadMediaSources;
import com.ptp.weibo_epiphqny.Utils.REGgetStringBetweenAandB;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.cookie.CookieSpec;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class WeiboSimulateLogin {


    //用于存储用户的相关信息
    public String nickName = null;//用户的昵称
    public String personalizedSignature = null;//用户的个性签名
    public String IPAddress = null;//用户的IP属地
    public String UID = null;//用户ID
    public String gender = null;//用户性别
    public String fansTotalNum = null;//用户的粉丝数
    public String focusTotalNum = null;//用户关注的数量
    public String postTotalNum = null;//用户的发帖总数
    public Boolean isNormalUsers = true;//判断用户是否为普通用户（用于与特权用户区分），是普通用户，则为true；若不是普通用户，则为false


    public void getPersonalInformation() {

        try {

            String personalHomepage = new cookieLogin().Login();
            System.out.println(personalHomepage);
            System.out.println("----------------------------");

            //进行个人数据填充
            this.nickName = REGgetStringBetweenAandB.getStringBetweenAandB("\"screen_name\":\"", "\",\"profile_image_url\"", personalHomepage);
            this.personalizedSignature = REGgetStringBetweenAandB.getStringBetweenAandB("\"description\":\"", "\",\"location\"", personalHomepage);
            this.IPAddress = REGgetStringBetweenAandB.getStringBetweenAandB("\"location\":\"", "\",\"gender\"", personalHomepage);
            this.UID = REGgetStringBetweenAandB.getStringBetweenAandB("\"uid\":", ",\"apmSampleRate\"", personalHomepage);
            this.gender = REGgetStringBetweenAandB.getStringBetweenAandB("\"gender\":\"", "\",\"followers_count\"", personalHomepage);
            this.fansTotalNum = REGgetStringBetweenAandB.getStringBetweenAandB("\"followers_count\":", ",\"followers_count_str\"", personalHomepage);
            this.focusTotalNum = REGgetStringBetweenAandB.getStringBetweenAandB("\"friends_count\":", ",\"statuses_count\"", personalHomepage);
            this.postTotalNum = REGgetStringBetweenAandB.getStringBetweenAandB("\"statuses_count\":", ",\"url\"", personalHomepage);
            if (REGgetStringBetweenAandB.getStringBetweenAandB("\"isNormal\":", ",\"flags\"", personalHomepage) == "true") {

                this.isNormalUsers = true;

            } else {

                this.isNormalUsers = false;

            }

            System.out.println("nickname="+this.nickName+"\npersonalizedSignature="+this.personalizedSignature+"\nisNormalUsers="+this.isNormalUsers+"\npostTotalNum="+this.postTotalNum+"\nfocusTotalNum="+this.focusTotalNum+"\ngender="+this.gender+"\nUID="+this.UID+"\nIPAddress="+this.IPAddress+"\nfansTotalNum="+this.fansTotalNum);

            //进行相关媒体文件的下载（缓存），比如，头像。。。。。。
            String avatarHDURL = REGgetStringBetweenAandB.getStringBetweenAandB("\"avatar_hd\":\"","\",\"follow_me\"",personalHomepage);
            DownloadMediaSources.downloadMediaSources(avatarHDURL,"avatar_hd.jpg");




        } catch (IOException e) {

            e.printStackTrace();
            e.getCause();

        } catch (URISyntaxException e) {

            e.printStackTrace();
            e.getLocalizedMessage();

        }

    }


}

class cookieLogin {

    private HttpClient client;
    private HttpPost post;
    private HttpGet get;
    private BasicCookieStore cookieStore;


    public cookieLogin() {

        //cookie策略，不设置会拒绝cookie rejected，设置策略保存cookie信息
        cookieStore = new BasicCookieStore();


        CookieSpecProvider myCookie = new CookieSpecProvider() {

            public CookieSpec create(HttpContext context) {

                return new DefaultCookieSpec();

            }

        };

        Registry rg = RegistryBuilder.create().register("myCookie", myCookie).build();
        client = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultCookieSpecRegistry(rg).build();
        get = new HttpGet();
        post = new HttpPost();


    }

    public String Login() throws ClientProtocolException, IOException, URISyntaxException {

        String LoginUrl = "https://weibo.com/u/7483529402";

        get.setURI(new URI(LoginUrl));
        get.addHeader("Host", "weibo.com");
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
        get.addHeader("Accept", "*/*");
        get.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        get.addHeader("Accept-Encoding", "gzip, deflate");
        get.addHeader("Referer", "http://weibo.com/");
        get.addHeader(new BasicHeader("Cookie", "SINAGLOBAL=4098670731829.4546.1647146726218; UOR=www.google.com,weibo.com,www.google.com; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WWZUS_8N9mnZbSVpeYNSXRF5JpX5KMhUgL.FoMX1hefeo.Xehz2dJLoIEBLxKMLB.eL1KqLxKMLBKnL12zLxKqL1KMLBKMLxKBLB.2L12zt; ULV=1648950902439:3:1:1:3967682655799.525.1648950902322:1647697916558; ALF=1685263800; SSOLoginState=1653727802; SCF=Ajn9mTQCrPbyeHdCZYes4-7jBe6hMNybeX8yYKi7Gw7lrcfst9OFnGiPEZBoQKieHh2V63owwk-1yPlxVFt2MwY.; SUB=_2A25PlZJqDeRhGeFK41EU8ifIyz6IHXVs4oSirDV8PUNbmtAKLWHRkW9NQvEshlKKs7pmKoABX3GafN2D8D_sT3my; XSRF-TOKEN=2h8wEmLpdK9RuN0RzCXByjOT; WBPSESS=b9tkHRqraosXN5KoeDZ7ZXTzkG5dAlbjxYIHRt26ecFM7ChPiY0eBQDDeUjMWNEfPE1b5BsWDZGWr5a_CtBHDVHN1szNVQkJQUnBviGd90ZeEPiScoK1DnabiZ1xGD1ugiFaviV-IlvBzDqFCoHjzw=="));

        HttpResponse resp = client.execute(get);
        HttpEntity entity = resp.getEntity();

        String cont = EntityUtils.toString(entity);
        return cont;

    }

}
