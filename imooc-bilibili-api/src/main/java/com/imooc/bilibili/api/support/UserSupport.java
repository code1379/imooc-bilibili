package com.imooc.bilibili.api.support;

import com.imooc.bilibili.domain.exception.ConditionException;
import com.imooc.bilibili.service.util.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component
public class UserSupport {
    public Long getCurrentUserId(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        // 拿里面的 token
        String token = requestAttributes.getRequest().getHeader("token");
        Long userId = TokenUtil.verifyToken(token);
        // 我们数据库的 id 是自增的，所以不会出现小于 0 的情况
        if(userId < 0) {
            throw new ConditionException("非法用户！");
        }
        return userId;
    }
}
