package com.newsun.Newsun.service;

import com.newsun.Newsun.domain.Member;
import com.newsun.Newsun.dto.jwt.JwtTokenDto;
import com.newsun.Newsun.exception.CustomException;
import com.newsun.Newsun.repository.MemberRepository;
import com.newsun.Newsun.type.ELoginProvider;
import com.newsun.Newsun.type.ErrorCode;
import com.newsun.Newsun.util.OAuth2Util;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2Service {

    public final OAuth2Util oAuth2Util;
    public final MemberRepository memberRepository;

    public String getRedirectUrl(ELoginProvider provider) {
        if (provider == ELoginProvider.GOOGLE) {
            return oAuth2Util.getGoogleRedirectUrl();
        }
        if(provider == ELoginProvider.KAKAO){
            return oAuth2Util.getKakaoRedirectUrl();
        }
        return null;
    }

    public String getAccessToken(String authorizationCode, ELoginProvider provider) {
        String accessToken = null;
        if (provider == ELoginProvider.GOOGLE) {
            accessToken = oAuth2Util.getGoogleAccessToken(authorizationCode);
        }
        if (provider == ELoginProvider.KAKAO) {
            accessToken = oAuth2Util.getKakaoAccessToken(authorizationCode);
        }
        return accessToken;
    }

    public void login(HttpServletResponse response, String accessToken, ELoginProvider provider) throws IOException {
        String tempId = null;
        if (provider == ELoginProvider.GOOGLE) {
            tempId = oAuth2Util.getGoogleUserInfo(accessToken);
        } else if (provider == ELoginProvider.KAKAO) {
            tempId=oAuth2Util.getKakaoUserInfo(accessToken);
        }

        if (tempId == null) {
            throw new CustomException(ErrorCode.NOT_FOUND_END_POINT);
        }

        final String socialId = tempId;
        final String password = UUID.randomUUID().toString();

        //category id 없이 save 안됨 -> null값 허용 or .category_id(0)으로 초기에 통일,,
        Member member = memberRepository.findBySocialIdAndProvider(socialId, provider)
                .orElseGet(() -> memberRepository.save(
                        Member.builder()
                        .socialId(socialId)
                        .password(password)
                        .provider(provider)
                        .build()));

//        JwtTokenDto jwtToken = jwtProvider.createTokens(member.getSocialId());
//        member.updateRefreshToken(jwtToken.refreshToken());
//
//        sendAccessTokenAndRefreshToken(user, response, jwtToken.accessToken(), jwtToken.refreshToken());
    }
}
