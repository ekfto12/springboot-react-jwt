package com.jy.react.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jy.react.domain.User;
import com.jy.react.model.CustomUserDetails;
import com.jy.react.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomUserDetailService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Override
    public CustomUserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
    	
    	User user = userRepository.findByUid(uid).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    	System.out.println("??");
    	return new CustomUserDetails(
              String.valueOf(user.getId()),
                user.getPassword(),
                user.getRoles());
    
    }

}
