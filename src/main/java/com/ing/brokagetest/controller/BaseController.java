package com.ing.brokagetest.controller;

import com.ing.brokagetest.dto.BaseDTO;
import com.ing.brokagetest.service.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
public abstract class BaseController<DTO extends BaseDTO> {
    private final BaseService<DTO> service;

    @PostMapping("/create")
    public DTO create(@RequestBody DTO dto){
        return service.create(dto);
    }

    @PutMapping("/update/{id}")
    public DTO update(@RequestBody DTO dto, @PathVariable(name="id") long id){
        return service.update(dto, id);
    }

    @GetMapping("/list")
    public List<DTO> list(DTO dto, Long startDate, Long endDate, Integer page, Integer size){
        return service.list(dto, startDate, endDate, page, size);
    }

    @GetMapping("/take/{id}")
    public DTO getById(@PathVariable(name="id") long id){
        return service.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name="id")long id){
        service.delete(id);
    }
}
