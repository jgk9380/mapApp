package com.config.security;

import com.entity.p.LoginUser;
import com.entity.p.SystemPrivilege;
import com.entity.p.SystemRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * 默认密码为空字符串  , 并且设置为启用,没有锁定 . 没有过期.
 */
public class JwtUser implements UserDetails {


    LoginUser loginUser;

    public JwtUser(LoginUser loginUser) {
        this.loginUser = loginUser;
        //        this.authorities = authorities;
        //        this.enabled = enabled;
        //        this.lastPasswordResetDate = lastPasswordResetDate;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<ConfigAuthority> result = new ArrayList<>();
        List<SystemRole> lsr = loginUser.getUserRoles();
        for (SystemRole sr : lsr) {
            List<SystemPrivilege> lsp=sr.getSystemPrivileges();
            for(SystemPrivilege sp:lsp)
            {
                ConfigAuthority ca = new ConfigAuthority(sp.getName());
                result.add(ca);
            }
        }
        return result;
    }

    @Override
    public String getPassword() {
        return this.loginUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.loginUser.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.loginUser.getIsValid();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.loginUser.getIsValid();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.loginUser.getIsValid();
    }

    @Override
    public boolean isEnabled() {
        return this.loginUser.getIsValid();
    }

}


class ConfigAuthority implements GrantedAuthority {
    String auth;

    public ConfigAuthority(String auth) {
        this.auth = auth;
    }

    @Override
    public String getAuthority() {
        return auth;
    }
}