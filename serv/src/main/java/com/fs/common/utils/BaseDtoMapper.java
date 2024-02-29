package com.fs.common.utils;

import org.mapstruct.MappingTarget;

public interface BaseDtoMapper<E, R, C, U> {

    R toResponse(E entity);

    E create(C dto);

    void update(U dto, @MappingTarget E entity);
}
