package com.bfd.api.utils;

import org.apache.commons.lang.StringUtils;
import javax.servlet.http.Cookie;

/**
 * Created by jiangnan on 16/9/13.
 */
public class CookieUtil {

    private final static int COOKIE_AGE_DEFAULT = 60 * 30;

    /**
     * 获得一个Cookie
     *
     * @param cookies
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(Cookie[] cookies, String cookieName) {
        if (cookies!= null && cookies.length>0) {
            for (Cookie c : cookies) {
                if (c.getName().equalsIgnoreCase(cookieName)) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * 创建一个Cookie，自定义Cookie时间
     *
     * @param cookieName
     * @param value
     * @param cookieTime
     * @return
     */
    public static Cookie createCookie(String cookieName, String value, int cookieTime) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setPath("/");
        cookie.setMaxAge(cookieTime);
//        cookie.setDomain(Constants.COOKIE_DOMAIN);
        return cookie;
    }

    /**
     * 创建一个Cookie，默认永久Cookie
     *
     * @param cookieName
     * @param value
     * @return
     */
    public static Cookie createCookie(String cookieName, String value) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_AGE_DEFAULT);
//        cookie.setDomain(Constants.COOKIE_DOMAIN);
        return cookie;
    }

    /**
     * 从cookies中取出name为k的值
     * 名字不清已作废
     * @param cookies
     * @param k
     * @return
     */
    @Deprecated
    public static String get(Cookie[] cookies, String k) {
        return getCookieValue(cookies,k);
    }

    /**
     * 从cookies中取出name为k的值
     * @param cookies
     * @param key
     * @return
     */
    public static String getCookieValue(Cookie[] cookies, String key) {
        if (null != cookies) {
            for (int i =   0; i < cookies.length; i++) {
                if (key.equals(cookies[i].getName()) && null != cookies[i].getValue() && cookies[i].getValue().length() >   0) {
                    return cookies[i].getValue();
                }
            }
        }
        return null;
    }

    /**
     * 移除一个Cookie
     * @param cookies
     * @param cookieName
     * @return
     */
    public static Cookie removeCookie(Cookie[] cookies, String cookieName) {
        if (StringUtils.isNotEmpty(cookieName)) {
            Cookie cookie = getCookie(cookies, cookieName);
            if (cookie != null) {
                cookie.setPath("/");// 不要漏掉
                cookie.setMaxAge(0);// 如果0，就说明立即删除
//                cookie.setDomain(domain);
                return cookie;
            }
        }
        return null;
    }

    /**
     * 检测该用户是否有uid
     * @param cookies
     * @return
     */
    public static boolean checkUID(Cookie[] cookies){
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("uid".equals(cookie.getName())){
                    String value = cookie.getValue();
                    if(value != null && value.length() >   0){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
