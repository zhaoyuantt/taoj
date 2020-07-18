package com.landasoft.taoj.service.impl;

import com.landasoft.taoj.mapper.TUserInfoMapper;
import com.landasoft.taoj.pojo.TUserInfo;
import com.landasoft.taoj.pojo.TUserInfoExample;
import com.landasoft.taoj.service.AdminUserService;
import com.landasoft.taoj.utils.IDUtils;
import com.landasoft.taoj.utils.MyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 管理用户Service接口实现
 * @author zhaoyuan
 * @date 2020,July 13
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private TUserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public MyResult registerAdminUser(TUserInfo userInfo) {
        //verify must parameters
        if(StringUtils.isBlank(userInfo.getUsername()))
            throw new RuntimeException("Must param username is null!");
        if(StringUtils.isBlank(userInfo.getPasswd()))
            throw new RuntimeException("Must param passwd is null!");
        if(StringUtils.isBlank(userInfo.getPhoneNumber()))
            throw new RuntimeException("Must param phone number is null!");
        
        //判断用户是否存在
        TUserInfo tUserInfo = getAdminUserByName(userInfo.getUsername());
        if(null != tUserInfo)
            return MyResult.build(105,"This username is already!");


        //encryption password
        String pwMd5 = DigestUtils.md5DigestAsHex(userInfo.getPasswd().getBytes());
        userInfo.setPasswd(pwMd5);

        //fill attribute
        userInfo.setId(IDUtils.getGeneId());
        userInfo.setState("01");//01：有效 02：锁定
        userInfo.setRemark("sfy");
        userInfo.setCreated(new Date());
        userInfo.setUpdated(new Date());

        //execute insert
        int iResult = userInfoMapper.insert(userInfo);

        //handle return result
        if(0 == iResult)
            return MyResult.build(101,"No records were inserted!");

        return MyResult.ok();
    }

    @Override
    public MyResult checkLogin(TUserInfo userInfo) {
        //verify must parameters
        if(StringUtils.isBlank(userInfo.getUsername()))
            throw new RuntimeException("Must param username is null!");
        if(StringUtils.isBlank(userInfo.getPasswd()))
            throw new RuntimeException("Must param passwd is null!");

        TUserInfo tUserInfo = getAdminUserByName(userInfo.getUsername());
        if(null == tUserInfo)
            return MyResult.build(105,"Username or password is error!");

        //输入的密码
        String pPdMd5 = DigestUtils.md5DigestAsHex(userInfo.getPasswd().getBytes());
        //数据中的加密密码
        String dPdMd5 = tUserInfo.getPasswd();

        if(!pPdMd5.equals(dPdMd5)){
            return MyResult.build(105,"Username or password is error!");
        }

        return MyResult.ok();
    }

    @Override
    public TUserInfo getAdminUserByName(String username) {
        TUserInfoExample example = new TUserInfoExample();
        TUserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);

        List<TUserInfo> userInfoList = userInfoMapper.selectByExample(example);

        if(null == userInfoList || 0 == userInfoList.size())
            //throw new RuntimeException("No records were found according to the username!");
            return null;

        return userInfoList.get(0);
    }

    @Override
    public String getUserNameByAlias(String alias) {
        if(StringUtils.isBlank(alias))
            throw new RuntimeException("Must param alias is null!");
        TUserInfoExample example = new TUserInfoExample();
        TUserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andAliesEqualTo(alias);

        List<TUserInfo> userInfoList = userInfoMapper.selectByExample(example);

        if(0 != userInfoList.size())
            return userInfoList.get(0).getUsername();

        return null;
    }
}
