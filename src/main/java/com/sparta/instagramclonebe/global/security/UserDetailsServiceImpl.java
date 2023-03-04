package com.sparta.instagramclonebe.global.security;

import com.sparta.instagramclonebe.domain.user.entity.User;
import com.sparta.instagramclonebe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    //인증이 완료된 사용자 불러오기
    @Override
    public UserDetails loadUserByUsername(String useremail) {
        User user = userRepository.findByUseremail(useremail)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을수 없습니다."));

        return new UserDetailsImpl(user, user.getUseremail());
    }
}
