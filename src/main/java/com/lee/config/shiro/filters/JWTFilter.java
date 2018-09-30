package com.lee.config.shiro.filters;

import com.lee.common.JsonRes;
import com.lee.config.shiro.JWTToken;
import com.lee.utils.JsonUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class JWTFilter extends BasicHttpAuthenticationFilter {

    private final static Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    /**
     * 进入shiro认证
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("token");

        JWTToken token = new JWTToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        Subject subject = getSubject(request, response);
        subject.login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 都进行认证
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response);
        } catch (Exception e) {
            printRes((HttpServletResponse) response, "missing or invalid token");
        }
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 没有token或者token不正确，打印错误信息
     * @param response 响应
     * @param msg 输出信息
     * @throws IOException
     */
    private void printRes(HttpServletResponse response, String msg) {
        JsonRes jsonRes = new JsonRes();
        jsonRes.setSuccess(false);
        jsonRes.setMsg(msg);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        OutputStream pw = null;
        try {
            pw = response.getOutputStream();
            pw.write(JsonUtil.objectToJson(jsonRes).getBytes());
        } catch (IOException e) {
            logger.error("printRes:", e);
        }finally {
            if(pw!=null){
                try {
                    pw.close();
                } catch (IOException e) {
                    logger.error("pw close failed:", e);
                }
            }
        }
    }
}
