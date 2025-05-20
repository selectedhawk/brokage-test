package com.ing.brokagetest.service;

import com.ing.brokagetest.dto.BaseDTO;

import java.util.List;

public interface BaseService<DTO extends BaseDTO> {
    DTO create(DTO dto);

    DTO update(DTO dto, long id);

    List<DTO> list(DTO dto, Long startDate, Long endDate, Integer page, Integer size);

    DTO getById(long id);

    void delete(long id);
}
