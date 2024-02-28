package com.fs.configs.property;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties("app")
public class AppProperties {

    @NotEmpty
    private String name;

    @NotEmpty
    private String host;

    @NotEmpty
    private String url;

    private Oauth oauth = new Oauth();

    @Getter
    @Setter
    @ToString
    public static class Oauth {
        private boolean enabled;
        private String clientId;
        private String clientSecret;
        private String tokenSigningKey;
        private int tokenValiditySeconds;
        private int refreshTokenValiditySeconds;
    }

}
