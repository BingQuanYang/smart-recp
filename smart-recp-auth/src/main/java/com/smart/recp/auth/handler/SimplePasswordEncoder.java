package com.smart.recp.auth.handler;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ybq
 */
public class SimplePasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        if (charSequence.toString().equals(s)) {
            return true;
        }
        return false;
    }
}
