package com.myblog.version3.Tools;


import com.zhenzi.sms.ZhenziSmsClient;

public class eMessage {

    private static ZhenziSmsClient client = new ZhenziSmsClient(
            "https://sms_developer.zhenzikj.com"
            , "100628",
            "952970ff-8c79-462c-add7-c952e0625492");

    public static String sendVerification(String phone) {
        try {
            int number = (int) (Math.random() * 100000);
            System.out.println(client.send(phone
                    , "欢迎注册“三年二班的路小雨”的博客，您的注册验证码为：" + number + "，验证码的有效时间为60秒"));
            return Integer.toString(number);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getBalance() {
        try {
            return client.balance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
