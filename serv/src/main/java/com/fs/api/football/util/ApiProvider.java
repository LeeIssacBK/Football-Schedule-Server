package com.fs.api.football.util;


import com.fs.common.utils.CodeProvider;
import org.springframework.http.HttpHeaders;

import java.util.function.Consumer;

public class ApiProvider {

    public static Consumer<HttpHeaders> getHeader() {
        return httpHeaders -> {
            httpHeaders.add(CodeProvider.apiKey.getK(), CodeProvider.apiKey.getValue());
            httpHeaders.add(CodeProvider.apiHost.getK(), CodeProvider.apiHost.getValue());
        };
    }

}
