package com.itrip.service;

import com.itrip.pojo.ItripUser;

/**
 * Token维护业务接口
 */
public interface TokenService {

    //会话的过期时间 默认2h
    public final int SESSION_TIMEOUT=60*60*2;
    //Token置换保护时间，默认1h
    public final int REPLACE_PROTECTION_TOMEN=60*60;
    //IEtoken延长时间
    public final int REPLACE_DELAY=2*60;

    /**
     * Token 生成
     * @param agent http请求头信息
     * @param itripUser  用户信息
     * @return 字符串
     * PC:PC-
     * 移动端:
     *
     */
    public String genertelToken(String agent, ItripUser itripUser);

    /**
     * 将Token信息保存到redis缓存服务器
     * @param token token字符串
     * @param itripUser 用户信息
     */
    public void save(String token,ItripUser itripUser);

    /**
     * Token置换
     * @param agent HTTP请求头信息
     * @param oldToken 要被替换的token字符串
     * @return 生成新的字符串
     * 1.首先判断token字符串是否有效
     * 2.判断生成的token是否在一个小时之内
     * 3.置换Token是
     *
     */
    public String replaceToken(String agent,String oldToken) throws Exception;

    /**
     * 删除token
     * @param token
     */
    public void delate(String token);

    /**
     * 验证token
     * @param agent
     * @param token
     * @return
     */
    public boolean validate(String agent,String token);

    public ItripUser load(String token) ;



}
