package com.ing.brokagetest.handler;

import com.ing.brokagetest.configuration.HeaderConfig;
import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.dto.CustomerOrderDTO;
import com.ing.brokagetest.entity.CustomerOrder;
import com.ing.brokagetest.enums.EnumOrderSide;
import com.ing.brokagetest.enums.EnumOrderStatus;
import com.ing.brokagetest.mapper.BaseMapper;
import com.ing.brokagetest.mapper.CustomerOrderMapper;
import com.ing.brokagetest.repository.BaseRepository;
import com.ing.brokagetest.repository.CustomerOrderRepository;
import com.ing.brokagetest.service.UserCustomerAssetService;
import com.ing.brokagetest.service.UserCustomerOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class UserCustomerOrderHandlerTest extends BaseHandlerTest<CustomerOrderDTO, CustomerOrder, UserCustomerOrderService> {

    @Mock
    private CustomerOrderRepository repository;

    @Mock
    private UserCustomerAssetService customerAssetService;

    @Mock
    private HeaderConfig headerConfig;

    private final Long customerId = 1L;

    @BeforeEach
    void setup() {
        mapper = getMapper();
        super.repository = getRepository();
        service = Mockito.spy(getService());
        Mockito.lenient().when(customerAssetService.getById(anyLong())).thenReturn(getAssetDTO());
        Mockito.lenient().when(repository.findById(anyLong())).thenReturn(Optional.of(mapper.serialize(getDTO())));
        Mockito.lenient().when(headerConfig.getCustomerId()).thenReturn(customerId);
    }

    @Override
    protected CustomerOrderDTO getDTO() {
        CustomerOrderDTO dto = new CustomerOrderDTO();
        dto.setId(1L);
        dto.setCustomerId(1L);
        dto.setAssetName("TEST ASSET");
        dto.setAssetId(1L);
        dto.setPrice(10.0);
        dto.setSize(10);
        dto.setStatus(EnumOrderStatus.PENDING);
        dto.setOrderSide(EnumOrderSide.BUY);
        dto.setCreateDate(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        return dto;
    }

    protected CustomerAssetDTO getAssetDTO() {
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
    protected BaseMapper<CustomerOrderDTO, CustomerOrder> getMapper() {
        return new CustomerOrderMapper(new ModelMapper());
    }

    @Override
    protected UserCustomerOrderService getService() {
        return new UserCustomerOrderHandler(new CustomerOrderMapper(new ModelMapper()), repository, customerAssetService, headerConfig);
    }

    @Override
    protected BaseRepository<CustomerOrder> getRepository() {
        return repository;
    }


    @Test
    void testGetById_Success_whenCustomerIdMatches() {
        long id = 1L;

        CustomerOrderDTO result = service.getById(id);

        assertEquals(customerId, result.getCustomerId());
        Mockito.verify(service).getById(id);
        Mockito.verify(headerConfig).getCustomerId();
    }

    @Test
    void testGetById_ThrowsException_whenCustomerIdDoesNotMatch() {
        long id = 1L;
        Long dtoCustomerId = 200L;

        CustomerOrderDTO dto = getDTO();
        dto.setCustomerId(dtoCustomerId);

        Mockito.lenient().when(repository.findById(anyLong())).thenReturn(Optional.of(mapper.serialize(dto)));

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            service.getById(id);
        });

        assertEquals("Record Not Found!", thrown.getMessage());
        Mockito.verify(service).getById(id);
        Mockito.verify(headerConfig).getCustomerId();
    }

}
