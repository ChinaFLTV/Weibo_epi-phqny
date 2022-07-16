package com.ptp.weibo_epiphqny.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadMediaSources {

    public static void downloadMediaSources(String url,String fileName) {

        InputStream is = null;
        FileOutputStream fos = null;

        try {

            URL originalURL = new URL(url);//格式化输入的字符串
            URLConnection connection = originalURL.openConnection();//建立与该输入的URL的连接
            is = connection.getInputStream();//将从该输入的URL连接中的数据转换为输入流
            fos = new FileOutputStream(new File("C://Users//Lenovo//Desktop//"+fileName));
            byte[] readBlock = new byte[128];
            int read = -1;
            while ((read = is.read(readBlock)) != -1) {

                fos.write(readBlock);

            }


        } catch (MalformedURLException e) {

            e.printStackTrace();
            e.getLocalizedMessage();

        } catch (IOException e) {

            e.printStackTrace();
            e.getCause();

        } finally {

            if (fos != null) {

                try {

                    fos.close();

                } catch (IOException e) {

                    e.printStackTrace();
                    e.getCause();

                }

            }

            if (is != null) {

                try {

                    is.close();

                } catch (IOException e) {

                    e.printStackTrace();
                    e.getMessage();

                }

            }

        }


    }

}
