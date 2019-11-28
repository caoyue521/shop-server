package com.github.caoyue521.shopserver.filter;

import com.github.caoyue521.shopserver.entity.User;
import com.github.caoyue521.shopserver.model.ApiResult;
import com.github.caoyue521.shopserver.service.UserService;
import com.github.caoyue521.shopserver.util.UserUtil;
import com.google.gson.Gson;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebFilter(filterName = "checkAuth",urlPatterns = "/api/*")
@Component
@ConfigurationProperties("security")
@Slf4j
public  class LoginFilter implements Filter {
    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    UserService userService;
    @Setter
    private List<String> anonUrlList;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的访问方法
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        boolean isAnon = anonUrlList.stream()
                .map(anonUrl -> StringUtils.startsWith(request.getRequestURI(), anonUrl))
                .reduce(Boolean::logicalOr)
                .orElse(false);
        if(isAnon){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            Gson gson = new Gson();
            log.info("UnauthorizedEntryPoint. url:{},anonUrlList:{},uri:{}", request.getRequestURL(),gson.toJson(anonUrlList),request.getRequestURI());
            UserUtil.remove();
            try (PrintWriter writer = response.getWriter()) {
                writer.write(gson.toJson(ApiResult.unAuthorized()));
                writer.flush();
            }
            return;
        }else{
            String uid = redisTemplate.opsForValue().get(token);
            User user =  userService.getUser(uid);
            if(user==null){
                Gson gson = new Gson();
                UserUtil.remove();
                try (PrintWriter writer = response.getWriter()) {
                    writer.write(gson.toJson(ApiResult.unAuthorized()));
                    writer.flush();
                }
                return;
            }else{
                UserUtil.set(user);
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
