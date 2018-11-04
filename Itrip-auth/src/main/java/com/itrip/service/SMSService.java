package com.itrip.service;

import com.itrip.utils.ReturnResult;

/**
 * 短信验证码业务
 */
public interface SMSService {

    /**
     * 发送短信
     * @param mobiles 手机号码
     * @param code 验证码
     * @returnc
     */
    public ReturnResult sendSMS(String mobiles,String code);

    /**
     * 验证用户输入的验证码
     * @param userCode 用户输入的验证码
     * @param clientCode 服务器生成的验证码
     * @return
     */
    public boolean valiCode(String userCode,String clientCode);



}
