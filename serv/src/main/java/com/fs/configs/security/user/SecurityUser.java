package com.fs.configs.security.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecurityUser extends User {

    private Object extraData;

    public SecurityUser(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(username, password, authorities);
    }

    public SecurityUser(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            Object extraData
    ) {
        super(username, password, authorities);
        this.extraData = extraData;
    }

//  public AccountUser(User user) {
//    super(
//        user.getUserId(),
//        user.getUserPassword(),
//        true,
//        user.isNonExpired(),
//        user.isNonExpired(),
//        user.isNonLock(),
//        getAuthorities(user)
//    );
//    this.users = user.toSimple();
//  }

//  private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
//    List<String> authorities = Lists.newArrayList();
//    authorities.add("ROLE_USER");
//
//    for (RoleGroup group : user.getRoleGroups()) {
//      String role = "ROLE_GROUP:" + group.getId();
//      if (!authorities.contains(role)) {
//        authorities.add(role);
//      }
//    }
//    return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//  }
//
//  public UserDto.Simple getUserDto() {
//    return users;
//  }

}
