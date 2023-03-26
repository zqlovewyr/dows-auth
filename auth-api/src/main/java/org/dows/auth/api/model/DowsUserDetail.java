package org.dows.auth.api.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


public class DowsUserDetail implements Serializable, UserDetails {

    private static final long serialVersionUID = -3454421571969004484L;
    
    private String userId;

    private String userName;

    private String userPass;

    private Collection<? extends GrantedAuthority> authorities = new ArrayList<>();;
/*    private SysUser user;

    public DowsUserDetail(SysUser user) {
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userPass = user.getUserPass();
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(this.user.getPermsSet())) {
            this.user.getPermsSet().forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth)));
        }
        return authorities;
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.userPass;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
