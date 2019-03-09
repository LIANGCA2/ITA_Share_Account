package com.oocl.ita.demo.services;

public interface LoginService {
    String app_secret = "86db1e5b9cc0d727612a8af80ee0eb7a";
    String app_id = "";
    String code2SessionUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

    String error_code = "errcode";  // 0 成功  // -1 系统繁忙，稍后再试 // 40029 code 无效  //  45011 频率限制，每个用户每分钟100次
    String errro_message = "errmsg";
    String open_id = "openid";
    String session_key = "session_key";
    String union_id = "unionid";



    String weChatLogin(String code);
    boolean checkLogin(String loginStatus);
}
