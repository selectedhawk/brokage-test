package com.ing.brokagetest.handler;

import com.ing.brokagetest.configuration.HeaderConfig;
import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.entity.CustomerAsset;
import com.ing.brokagetest.enums.EnumOrderSide;
import com.ing.brokagetest.mapper.BaseMapper;
import com.ing.brokagetest.mapper.CustomerAssetMapper;
import com.ing.brokagetest.repository.BaseRepository;
import com.ing.brokagetest.repository.CustomerAssetRepository;
import com.ing.brokagetest.service.UserCustomerAssetService;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UserCustomerAssetHandlerTest extends BaseHandlerTest<CustomerAssetDTO, CustomerAsset, UserCustomerAssetService> {

    @Mock
    private CustomerAssetRepository repository;

    @Mock
    private HeaderConfig headerConfig;

    private final Long customerId = 1L;

    @BeforeEach
    void setup() {
        mapper = getMapper();
        super.repository = getRepository();
        service = Mockito.spy(getService());
        Mockito.lenient().when(headerConfig.getCustomerId()).thenReturn(customerId);
        Mockito.lenient().when(repository.findById(anyLong())).thenReturn(Optional.of(mapper.serialize(getDTO())));
    }

    @Override
    protected CustomerAssetDTO getDTO() {
        CustomerAssetDTO dto = new CustomerAssetDTO();
        dto.setId(1L);
        dto.setCustomerId(customerId);
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
    protected UserCustomerAssetService getService() {
        return new UserCustomerAssetHandler(new CustomerAssetMapper(new ModelMapper()), repository, headerConfig);
    }

    @Override
    protected BaseRepository<CustomerAsset> getRepository() {
        return repository;
    }

    @Test
    void testGetById_Success_whenCustomerIdMatches() {
        long id = 1L;

        CustomerAssetDTO result = service.getById(id);

        assertEquals(customerId, result.getCustomerId());
        Mockito.verify(service).getById(id);
        Mockito.verify(headerConfig).getCustomerId();
    }

    @Test
    void testGetById_ThrowsException_whenCustomerIdDoesNotMatch() {
        long id = 1L;
        Long dtoCustomerId = 200L;

        CustomerAssetDTO dto = getDTO();
        dto.setCustomerId(dtoCustomerId);

        Mockito.lenient().when(repository.findById(anyLong())).thenReturn(Optional.of(mapper.serialize(dto)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.getById(id);
        });

        assertEquals("Record Not Found!", thrown.getMessage());
        Mockito.verify(service).getById(id);
        Mockito.verify(headerConfig).getCustomerId();
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
