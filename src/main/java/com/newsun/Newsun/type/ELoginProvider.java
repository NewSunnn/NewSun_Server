package com.newsun.Newsun.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ELoginProvider {
    GOOGLE("google");

    private final String name;
}
