package com.newsun.Newsun.controller;

import com.newsun.Newsun.dto.exception.ResponseDto;
import com.newsun.Newsun.service.OAuth2Service;
import com.newsun.Newsun.type.ELoginProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuth2Service oAuth2Service;


    //callback url 정보 담아오기
    @GetMapping("/google")
    public ResponseDto<Map<String,String>> getGoogleRedirectUrl(){
        Map<String, String> map = new HashMap<>();
        map.put("url", oAuth2Service.getRedirectUrl(ELoginProvider.GOOGLE));
        return ResponseDto.ok(map);
    }

    @GetMapping("/google/callback")
    public void getGoogleAccessToken(String code, HttpServletResponse response) throws IOException {
        String accessToken = oAuth2Service.getAccessToken(code, ELoginProvider.GOOGLE);
        oAuth2Service.login(response, accessToken, ELoginProvider.GOOGLE);
    }

    @GetMapping("/kakao")
    public ResponseDto<Map<String,String>> getKakaoRedirectUrl(){
        Map<String, String> map = new HashMap<>();
        map.put("url", oAuth2Service.getRedirectUrl(ELoginProvider.KAKAO));
        return ResponseDto.ok(map);
    }

    @GetMapping("/kakao/callback")
    public void getNaverAccessToken(String code, HttpServletResponse response) throws IOException {
        String accessToken = oAuth2Service.getAccessToken(code, ELoginProvider.KAKAO);
        oAuth2Service.login(response, accessToken, ELoginProvider.KAKAO);
    }
}
