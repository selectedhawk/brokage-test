package com.ing.brokagetest.handler;

import com.ing.brokagetest.dto.BaseDTO;
import com.ing.brokagetest.entity.BaseEntity;
import com.ing.brokagetest.mapper.BaseMapper;
import com.ing.brokagetest.repository.BaseRepository;
import com.ing.brokagetest.service.BaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class BaseHandlerTest<DTO extends BaseDTO, ENTITY extends BaseEntity, SERVICE extends BaseService<DTO>> {
    protected BaseRepository<ENTITY> repository;
    protected BaseMapper<DTO, ENTITY> mapper;
    protected SERVICE service;

    protected abstract DTO getDTO();

    protected abstract BaseMapper<DTO, ENTITY> getMapper();

    protected abstract SERVICE getService();

    protected abstract BaseRepository<ENTITY> getRepository();

    @BeforeEach
    void setup() {
        mapper = getMapper();
        repository = getRepository();
        service = Mockito.spy(getService());
    }

    @Test
    void testCreate() {
        DTO dto = getDTO();
        ENTITY entity = mapper.serialize(dto);
        when(repository.save(any())).thenReturn(entity);
        DTO result = service.create(dto);
        assertEquals(dto.getId(), result.getId());
        verify(repository).save(any());
    }

    @Test
    void testUpdate() {
        DTO dto = getDTO();
        long id = dto.getId();
        ENTITY entity = mapper.serialize(dto);
        when(repository.save(any())).thenReturn(entity);
        DTO result = service.update(dto, id);
        assertEquals(id, result.getId());
        verify(repository).save(any());
    }
    @Test
    @SuppressWarnings("unchecked")
    void testList() {
        DTO dto = getDTO();
        ENTITY entity = mapper.serialize(dto);
        List<ENTITY> entityList = List.of(entity);
        Page<ENTITY> page = new PageImpl<>(entityList);

        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        List<DTO> result = service.list(dto, null, null, 1, 10);

        assertEquals(1, result.size());
        assertEquals(dto.getId(), result.getFirst().getId());

        verify(repository).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testGetById() {
        DTO dto = getDTO();
        ENTITY entity = mapper.serialize(dto);

        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));

        DTO result = service.getById(dto.getId());
        assertEquals(dto.getId(), result.getId());

        verify(repository).findById(dto.getId());
    }

    @Test
    void testGetById_NotFound() {
        long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.getById(id));
        assertEquals("Record Not Found!", exception.getMessage());
    }

    @Test
    void testDelete() {
        long id = 1L;
        service.delete(id);
        verify(repository).deleteById(id);
    }

}
