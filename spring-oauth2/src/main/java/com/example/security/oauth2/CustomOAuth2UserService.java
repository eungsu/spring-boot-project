package com.example.security.oauth2;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
// 소셜로그인 최종 단계에서 소셜로그인 제공업체가 제공하는 사용자 정보를 처리하는 클래스
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 소셜로그인 제공업체 코드를 조회한다
        // google, kakao, naver
        String provider = userRequest.getClientRegistration().getRegistrationId();
        
        // 소셜로그인 제공업체게 제공하는 사용자정보를 조회한다.
        Map<String, Object> attributes = oAuth2User.getAttributes();
        // OAuth2UserInfo 객체를 생성한다.
        OAuth2UserInfo userInfo = new OAuth2UserInfo(attributes);
        
        // 유저 아이디를 조회한다.
        String username = userInfo.getUsername(provider);
        // 이메일을 조회한다.
        String email = userInfo.getEmail(provider);
        // 닉네임을 조회한다.
        String nickname = userInfo.getNickname(provider);

        // 데이터베이스에 아이디로 사용자를 조회한다.
        Optional<User> optional = userRepository.findByUsername(username);
        User user = null;
        // 사용자정보가 존재하지 않으면 User객체를 생성해서 사용자 정보를 저장시킨다.
        if (optional.isEmpty()) {
            user = User.builder()
                .username(username)
                .email(email)
                .nickname(nickname)
                .build();

            userRepository.save(user);
        } else {
        // 사용자 정보가 존재하면 사용자정보를 가져온다.
            user = optional.get();
        }

        // OAuth2User 구현객체를 생성해서 반환한다.
        return new CustomOAuth2User(user, attributes);
    }
}
