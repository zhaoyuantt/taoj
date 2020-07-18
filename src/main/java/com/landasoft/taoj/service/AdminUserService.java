package com.landasoft.taoj.service;

import com.landasoft.taoj.pojo.TUserInfo;
import com.landasoft.taoj.utils.MyResult;

/**
 * 管理用户Service接口
 * @author zhaoyuan
 * @date 2020,July 13
 */
public interface AdminUserService {

    MyResult registerAdminUser(TUserInfo userInfo);

    /**
     * @param userInfo
     * @return
     */
    MyResult checkLogin(TUserInfo userInfo);

    TUserInfo getAdminUserByName(String username);

    String getUserNameByAlias(String alias);
}
