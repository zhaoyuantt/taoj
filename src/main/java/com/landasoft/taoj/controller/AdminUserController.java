package com.landasoft.taoj.controller;

import com.landasoft.taoj.common.pojo.CustonAdminUser;
import com.landasoft.taoj.service.AdminUserService;
import com.landasoft.taoj.utils.MyResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理用户Controller
 *
 * @author zhaoyuan
 * @date 2020, July 13
 */
@Controller
@RequestMapping("/admin/user")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;
    @Value("${ADMIN_INDER_PAGE_URL}")
    private String ADMIN_INDER_PAGE_URL;
    @Value("${ADMIN_LOGIN_PAGE_URL}")
    private String ADMIN_LOGIN_PAGE_URL;

    @RequestMapping(value = "/login/S", method = RequestMethod.POST)
    @ResponseBody
    public MyResult checkLogin(CustonAdminUser userInfo, HttpServletRequest request) {
        MyResult myResult = adminUserService.checkLogin(userInfo);

        if (200 == myResult.getStatus()) {
            //用户名、密码正确，存入session
            HttpSession session = request.getSession();
            session.setAttribute("TUSERDETAIL", adminUserService.getAdminUserByName(userInfo.getUsername()));

            if (StringUtils.isBlank(userInfo.getRedirect()))
                myResult.setData(ADMIN_INDER_PAGE_URL);
            else
                myResult.setData(userInfo.getRedirect());
        }

        return myResult;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public MyResult logout(HttpServletRequest request){
        request.getSession().removeAttribute("TUSERDETAIL");
        return MyResult.ok(ADMIN_LOGIN_PAGE_URL);
    }

}
