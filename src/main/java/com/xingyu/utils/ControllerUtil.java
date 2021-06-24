package com.xingyu.utils;

import com.xingyu.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.security.sasl.AuthenticationException;

public class ControllerUtil {
    public static void currentUserVerification(String username) throws AuthenticationException {
        if (!username.equals(((User)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).getUsername())) {
            throw new AuthenticationException("You are not allowed to access the resource");
        }
    }
}
