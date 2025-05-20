package com.ing.brokagetest.handler;

import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.entity.CustomerAsset;
import com.ing.brokagetest.enums.EnumOrderSide;
import com.ing.brokagetest.mapper.BaseMapper;
import com.ing.brokagetest.mapper.CustomerAssetMapper;
import com.ing.brokagetest.repository.BaseRepository;
import com.ing.brokagetest.repository.CustomerAssetRepository;
import com.ing.brokagetest.service.CustomerAssetService;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CustomerAssetHandlerTest extends BaseHandlerTest<CustomerAssetDTO, CustomerAsset, CustomerAssetService> {

    @Mock
    private CustomerAssetRepository repository;

    @Override
    protected CustomerAssetDTO getDTO() {
        CustomerAssetDTO dto = new CustomerAssetDTO();
        dto.setId(1L);
        dto.setCustomerId(1L);
        dto.setUsableSize(0);
        dto.setSize(0);
        dto.setAssetName("TEST ASSET");
        dto.setPrice(10.0);
        dto.setCreateDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        return dto;
    }

    @Override
    protected BaseMapper<CustomerAssetDTO, CustomerAsset> getMapper() {
        return new CustomerAssetMapper(new ModelMapper());
    }

    @Override
    protected CustomerAssetService getService() {
        return new CustomerAssetHandler(new CustomerAssetMapper(new ModelMapper()), repository);
    }

    @Override
    protected BaseRepository<CustomerAsset> getRepository() {
        return repository;
    }

    @Test
    void testBUYMatched() {
        long id = 1L;
        int size = 10;
        EnumOrderSide side = EnumOrderSide.BUY;

        CustomerAssetDTO dto = getDTO();
        CustomerAssetDTO updateDTO = getDTO();
        updateDTO.setSize(dto.getSize() + size);
        updateDTO.setUsableSize(dto.getUsableSize() + size);

        // service spy/mock objesi üzerinden stub
        Mockito.doReturn(dto).when(service).getById(id);
        Mockito.doReturn(updateDTO).when(service).update(any(CustomerAssetDTO.class), eq(id));

        service.matched(id, size, side);

        verify(service).getById(id);
        verify(service).update(argThat(updated ->
                updated.getSize().equals(size) &&
                        updated.getUsableSize().equals(size)
        ), eq(id));
    }

    @Test
    void testSELLMatched() {
        long id = 1L;
        int size = 10;
        int currentSize = 30;
        EnumOrderSide side = EnumOrderSide.SELL;

        CustomerAssetDTO dto = getDTO();
        dto.setSize(currentSize);
        dto.setUsableSize(currentSize);
        CustomerAssetDTO updateDTO = getDTO();
        updateDTO.setUsableSize(currentSize - size);

        // service spy/mock objesi üzerinden stub
        Mockito.doReturn(dto).when(service).getById(id);
        Mockito.doReturn(updateDTO).when(service).update(any(CustomerAssetDTO.class), eq(id));

        service.matched(id, size, side);

        verify(service).getById(id);
        verify(service).update(argThat(updated ->
                updated.getSize().equals(currentSize) &&
                        updated.getUsableSize().equals(currentSize - size)
        ), eq(id));
    }

    @Test
    void testMatched_WithOptimisticLockException() {
        long id = 1L;
        int size = 10;
        EnumOrderSide side = EnumOrderSide.BUY;

        CustomerAssetDTO dto = getDTO();

        Mockito.doReturn(dto).when(service).getById(id);
        Mockito.doThrow(new OptimisticLockException("Simulated"))
                .doReturn(dto)
                .when(service).update(any(CustomerAssetDTO.class), eq(id));

        service.matched(id, size, side);

        verify(service, times(2)).getById(id);
        verify(service, times(2)).update(any(CustomerAssetDTO.class), eq(id));
    }

}
