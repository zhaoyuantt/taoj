package com.landasoft.taoj.common.pojo;

import com.landasoft.taoj.pojo.TUserInfo;

import java.io.Serializable;

/**
 * 后台用户扩展类
 * @author zhaoyuan
 * @date 2020,July 13
 */
public class CustonAdminUser extends TUserInfo implements Serializable {

    //跳转的路径
    private String redirect;

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
