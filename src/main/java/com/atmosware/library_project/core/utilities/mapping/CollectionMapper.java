package com.atmosware.library_project.core.utilities.mapping;

import com.atmosware.library_project.business.dtos.CollectionResponse;
import com.atmosware.library_project.entities.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CollectionMapper {
    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);

    CollectionResponse mapToResponse(Collection collection);
}
