package com.fs.configs;

import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {

    @Bean
    public AuditorAware<String> auditorProviderSimple() {
        return new AuditorAwareSimpleImpl();
    }

    public static class AuditorAwareSimpleImpl implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (null == authentication || !authentication.isAuthenticated()) {
                return null;
            }
            if (authentication.getPrincipal() instanceof String) {
                return null;
            } else {
                User user = (User) authentication.getPrincipal();
                return Optional.of(user.getUsername());
            }
        }
    }

}
