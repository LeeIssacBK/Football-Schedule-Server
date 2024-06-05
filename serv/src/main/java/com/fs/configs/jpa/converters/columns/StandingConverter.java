package com.fs.configs.jpa.converters.columns;

import com.fs.api.football.dto.StandingDto;
import com.fs.configs.jpa.converters.ObjectAttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StandingConverter extends ObjectAttributeConverter<StandingDto.Response.Standing.Game> {

    @Override
    protected Class<StandingDto.Response.Standing.Game> getInstance() {
        return StandingDto.Response.Standing.Game.class;
    }

}

