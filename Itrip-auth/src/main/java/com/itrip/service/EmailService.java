package com.itrip.service;

/**
 * 邮箱业务接口
 */
public interface EmailService {
    /**
     * 发送邮件
     * @param mailTo 发送者邮箱
     * @param activatedCode 验证码
     */
    public void sendEmail(String mailTo,String activatedCode);


    /**
     * 验证用户输入的验证码
     * @param userCode 用户输入的验证码
     * @param clientCode 服务器生成的验证码
     * @return
     */
    public boolean valiCode(String userCode,String clientCode);
}
