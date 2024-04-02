package tw.idv.frank.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.users.model.dao.UsersRepository;
import tw.idv.frank.users.model.dto.LoginUser;
import tw.idv.frank.users.model.dto.UserRes;
import tw.idv.frank.users.model.entity.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository.findByEmail(email);

        if (Objects.isNull(users)) {
            throw new BadCredentialsException("帳號不存在!");
        }

        return new LoginUser(users);
    }
}
