package com.itrip.service.impl;

import com.alibaba.fastjson.JSON;
import com.itrip.pojo.ItripUser;
import com.itrip.service.TokenService;
import com.itrip.utils.MD5;
import com.itrip.utils.RedisAPI;
import com.itrip.utils.UserAgentUtil;
import cz.mallat.uasparser.UserAgentInfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TokenServiceImpl implements TokenService {

    //创建RedisAPI实例对象
    RedisAPI redis=new RedisAPI();
    //Token前缀
    private String tokenSuffix="token:";


    @Override
    public String genertelToken(String agent, ItripUser itripUser) {
        StringBuffer sb=new StringBuffer();
        sb.append(tokenSuffix);
        //获取用户请求头信息
        try {
            UserAgentInfo agentInfo = UserAgentUtil.getUasParser().parse(agent);
            if(agentInfo.getDeviceType().equals(UserAgentInfo.UNKNOWN)){
                if(UserAgentUtil.CheckAgent(agent)){
                    sb.append("MOBILE-");
                }else{
                    sb.append("PC-");
                }
            }else if(agentInfo.getDeviceType().equals("Personal computer")){
                sb.append("PC-");
            }else{
                sb.append("MOBILE-");
            }
            sb.append(MD5.getMd5(itripUser.getUsercode(),32)+"-");
            sb.append(itripUser.getId()+"-");
            sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"-");
            sb.append(MD5.getMd5(agent,6));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    @Override
    public void save(String token, ItripUser itripUser) {

    }

    @Override
    public String replaceToken(String agent, String oldToken) throws Exception{
        if(!redis.exists(oldToken)){
            throw new Exception("token已经过期，请从新登陆");
        }
        //Token生成时间
        String[] tokens=oldToken.split("-");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        Date tokenGenTime =sdf.parse(tokens[3]);
        //获取token的使用时间
        long passed= Calendar.getInstance().getTimeInMillis()-tokenGenTime.getTime();
        if(passed<REPLACE_PROTECTION_TOMEN){//token还在保护时间里，不能进行置换
            throw new Exception("token处于保护阶段，不能够进行置换");
        }
        //置换token
        //根据token字符串在redis上加载Itrip_User对象
        ItripUser itrip_user=this.load(oldToken);
        String newToken=null;
        long ttl=redis.ttl(oldToken);
        if(ttl>0||ttl==-1){
            newToken = this.genertelToken(agent,itrip_user);
            this.save(newToken,itrip_user);
            redis.set(oldToken,JSON.toJSONString(itrip_user),REPLACE_DELAY);
        }
        return newToken;

    }

    @Override
    public void delate(String token) {

    }

    @Override
    public boolean validate(String agent, String token) {
        return false;
    }

    @Override
    public ItripUser load(String token) {
        return null;
    }
}
