package com.fs.configs;

import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Configuration;

@org.mapstruct.MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Configuration
public interface MapperConfig {

}
