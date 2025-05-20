package com.ing.brokagetest.mapper;

import com.ing.brokagetest.dto.BaseDTO;
import com.ing.brokagetest.entity.BaseEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class BaseMapper<DTO extends BaseDTO, ENTITY extends BaseEntity> {

    private final ModelMapper mapper;
    private final Class<DTO> dtoClass;
    private final Class<ENTITY> entityClass;

    public DTO deserialize(ENTITY entity) {
        return mapper.map(entity, dtoClass);
    }

    public List<DTO> deserialize(List<ENTITY> list) {
        return Objects.requireNonNullElse(list, new ArrayList<ENTITY>()).stream().map(entity-> mapper.map(entity, dtoClass)).collect(Collectors.toList());
    }

    public ENTITY serialize(DTO dto) {
        return mapper.map(dto, entityClass);
    }

}
