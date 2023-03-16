package com.smart.recp.auth.service;


import com.smart.recp.common.core.base.BaseException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.security.Principal;
import java.util.Map;

public interface AuthService {
    OAuth2AccessToken login(Principal principal, Map<String, String> parameters) throws BaseException, HttpRequestMethodNotSupportedException;

    OAuth2AccessToken register(Map<String, String> parameters) throws BaseException, HttpRequestMethodNotSupportedException;
}
