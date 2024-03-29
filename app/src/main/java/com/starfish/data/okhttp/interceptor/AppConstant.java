package com.starfish.data.okhttp.interceptor;

public interface AppConstant {

    String BASE_URL = "https://www.wanandroid.com";

    public static final String DOWNLOAD_URL1 = "https://imtt.dd.qq.com/16891/89E1C87A75EB3E1221F2CDE47A60824A.apk?fsname=com.snda.wifilocating_4.2.62_3192.apk&csr=1bbd";
    public static final String DOWNLOAD_URL2 = "https://www.wanandroid.com/blogimgs/64542bbb-b0f6-4c0e-828f-e38eb3abdc00.apk";
    public static final String DOWNLOAD_URL3 = "https://ftp-new-apk.pconline.com.cn/4ffdda3af99a7f1826fa997bea3e1033/pub/download/201907/pconline1562082007841.apk";
    public static final String DOWNLOAD_URL4 = "https://downali.game.uc.cn/s/3/3/20190319171901_uc.apk?x-oss-process=udf/uc-apk,ZBHDhDR0L1tlSsK0wpLCng==40c29d34414a2b14&sh=10&sf=278909978&vh=56d180e146570661debd76a0fbce86f5&cc=1729610161&did=f307d17276b24e78b2d466e88aa0f2a2";
    public static final String DOWNLOAD_URL5 = "https://gdown.baidu.com/data/wisegame/0e58d76e3c525828/anzhishichang_6620.apk";

    String WEB_SITE_LOGIN = "user/login";
    String WEB_SITE_REGISTER = "user/register";
    String WEB_SITE_COLLECTIONS = "lg/collect";
    String WEB_SITE_UNCOLLECTIONS = "lg/uncollect";
    String WEB_SITE_ARTICLE = "article";


    public interface LoginParamsKey {
        String SET_COOKIE_KEY = "set-cookie";
        String SET_COOKIE_NAME = "Cookie";
        String USER_NAME = "username";
        String PASSWORD = "password";
        String REPASSWORD = "repassword";
    }
}
