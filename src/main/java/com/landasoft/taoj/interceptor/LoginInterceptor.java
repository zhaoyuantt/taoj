package com.landasoft.taoj.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * @author zhaoyuan
 * @date 2020,July 13
 */

/**
 * HandlerInterceptor 的功能跟过滤器类似，但是提供更精细的的控制能力：
 * 在request被响应之前、request被响应之后、视图渲染之前以及request全部结束之后。
 * 我们不能通过拦截器修改request内容，但是可以通过抛出异常（或者返回false）来暂停request的执行
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在Controller执行之前调用
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //获取Session
        Object sessionValue = httpServletRequest.getSession().getAttribute("TUSERDETAIL");

        //拦截
        if(null == sessionValue){
            //todo 跳转到指定页面，由于前端框架使用的是layui，有好多的都是基于iframe的，所以跳转到指定页面不太好看
            //StringBuffer url = httpServletRequest.getRequestURL();
            httpServletResponse.sendRedirect("/page/admin/login");
            return false;
        }

        //放行
        return true;
    }

    /**
     * controller执行之后，且页面渲染之前调用
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
