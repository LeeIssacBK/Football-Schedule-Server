package com.fs.configs;

import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.context.annotation.Configuration;

@org.mapstruct.MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.JAKARTA)
@Configuration
public interface MapperConfig {

}
