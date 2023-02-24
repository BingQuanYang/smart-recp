package com.smart.recp.auth.entity;

import cn.hutool.core.collection.CollectionUtil;
import com.smart.recp.service.user.vo.UserVO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author ybq
 */
@Data
@NoArgsConstructor
public class AuthUserDetail implements UserDetails {
    private Integer userId;

    private String account;

    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证
     */
    private String identificationCard;

    private String mobile;

    /**
     * 类型：1->买家,2->卖家,0->平台管理员
     */
    private Integer type;

    /**
     * 认证状态：0->未认证,1->已认证,2->认证中
     */
    private Integer certStatus;

    private LocalDateTime lastLoginTime;

    private Collection<SimpleGrantedAuthority> authorities;


    public AuthUserDetail(UserVO userVO) {
        this.authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userVO.getType().toString()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
