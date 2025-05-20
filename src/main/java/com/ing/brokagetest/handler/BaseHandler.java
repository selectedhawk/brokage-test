package com.ing.brokagetest.handler;

import com.ing.brokagetest.dto.BaseDTO;
import com.ing.brokagetest.entity.BaseEntity;
import com.ing.brokagetest.mapper.BaseMapper;
import com.ing.brokagetest.repository.BaseRepository;
import com.ing.brokagetest.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@AllArgsConstructor
public abstract class BaseHandler<DTO extends BaseDTO, ENTITY extends BaseEntity> implements BaseService<DTO> {
    private final BaseMapper<DTO, ENTITY> mapper;
    private final BaseRepository<ENTITY> repository;

    @Override
    public DTO create(DTO dto) {
        ENTITY entity = repository.save(mapper.serialize(dto));
        return mapper.deserialize(entity);
    }

    @Override
    public DTO update(DTO dto, long id) {
        dto.setId(id);
        ENTITY entity = repository.save(mapper.serialize(dto));
        return mapper.deserialize(entity);
    }

    @Override
    public List<DTO> list(DTO dto, Long startDate, Long endDate, Integer page, Integer size) {
        ENTITY entity = mapper.serialize(dto);
        Specification<ENTITY> entitySpecification = HandlerHelper.getEntitySpecification(entity);
        Specification<ENTITY> dateSpecification = HandlerHelper.getDateSpecification(startDate, endDate);
        Specification<ENTITY> finalSpecification = entitySpecification.and(dateSpecification);
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(initiatePage(page) - 1, size, sort);
        Page<ENTITY> pageList = repository.findAll(finalSpecification, pageRequest);
        List<ENTITY> list = pageList.toList();
        return mapper.deserialize(list);
    }

    @Override
    public DTO getById(long id) {
        ENTITY entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Record Not Found!"));
        return mapper.deserialize(entity);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

    private Integer initiatePage(Integer page) {
        if (page == null || page < 1) return 1;
        return page;
    }

}
