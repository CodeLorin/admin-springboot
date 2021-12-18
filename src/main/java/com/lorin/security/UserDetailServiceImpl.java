package com.lorin.security;

import com.lorin.entity.User;
import com.lorin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/17 10:25
 */

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或者密码不正确");
        }
        return new AccountUser(user.getId(), user.getUsername(), user.getPassword(), getUserAuthority(user.getId()));
    }

    /**
     * 获取用户权限
     *
     * @param userId
     * @return java.util.List<org.springframework.security.core.GrantedAuthority>
     * @author lorin
     */
    public List<GrantedAuthority> getUserAuthority(Long userId) {
        String authority = userService.getUserAuthority(userId);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
