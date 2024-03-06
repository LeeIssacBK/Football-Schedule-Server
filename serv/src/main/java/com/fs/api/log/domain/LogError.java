package com.fs.api.log.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.util.IOUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@NoArgsConstructor
@Getter
@Entity
public class LogError extends BaseDomain {

    private String name;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(columnDefinition = "TEXT")
    private String raw;

    @Column(columnDefinition = "TEXT")
    private String queryString;

    @Column(columnDefinition = "TEXT")
    private String requestBody;

    public LogError(Exception e) {
        this.name = e.getClass().getName();
        this.message = e.getMessage();
        currentHttpRequest().ifPresent(httpServletRequest -> {
            //set query string
            this.queryString = httpServletRequest.getQueryString();
            //set data
            try {
                this.raw = IOUtils.toString(httpServletRequest.getReader());
            } catch (IOException | IllegalStateException readError) {
                log.error(readError.getMessage());
            }
            //set request body
            if (httpServletRequest instanceof ContentCachingRequestWrapper cachingRequest) {
                this.requestBody = new String(cachingRequest.getContentAsByteArray());
            }
        });
    }

    @Transient
    protected static Optional<HttpServletRequest> currentHttpRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .filter(ServletRequestAttributes.class::isInstance)
                .map(ServletRequestAttributes.class::cast)
                .map(ServletRequestAttributes::getRequest);
    }

}
