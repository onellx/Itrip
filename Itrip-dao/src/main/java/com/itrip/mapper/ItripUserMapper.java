package com.itrip.mapper;

import com.itrip.pojo.ItripUser;

import java.util.List;
import java.util.Map;

public interface ItripUserMapper {


    /**
     * 查询用户记录
     * @param parms
     * @return
     */
    public List<ItripUser> selectItripUser(Map<String,Object> parms);

    /**
     * 添加一条用户记录
     * @param itripUser
     * @return
     */
    public Integer insertItripUser(ItripUser itripUser);

    /**
     * 更新一个用户记录
     * @param itripUser
     * @return
     */
    public Integer updateItripUser(ItripUser itripUser);
}
