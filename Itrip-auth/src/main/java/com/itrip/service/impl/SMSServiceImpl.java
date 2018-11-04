package com.itrip.service.impl;

import com.itrip.service.SMSService;
import com.itrip.utils.HttpClientUtil;
import com.itrip.utils.ReturnResult;
import redis.clients.jedis.Jedis;

public class SMSServiceImpl implements SMSService {


    //用户名
    private static String Uid = "one_llx";

    //接口安全秘钥
    private static String Key = "d41d8cd98f00b204e980";

    private Jedis redis;

    @Override
    public ReturnResult sendSMS(String mobiles, String code) {
        ReturnResult returnResult=new ReturnResult();
        HttpClientUtil client = HttpClientUtil.getInstance();
        //UTF发送
        int result = client.sendMsgUtf8(this.Uid, this.Key, "验证码："+code, mobiles);
        if(result>0){
            returnResult.setMessage("验证码发送成功！！！");
            //System.out.println("UTF8成功发送条数=="+result);
        }else{
            returnResult.setMessage(client.getErrorMsg(result));
            //System.out.println(client.getErrorMsg(result));
        }

        return returnResult;
    }

    @Override
    public boolean valiCode(String userCode, String clientCode) {

        String serverCCode=redis.get(userCode);
        if (userCode.equals(clientCode)){
            return true;
        }else {
            return false;
        }

    }
}
