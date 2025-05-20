package com.ing.brokagetest.handler;

import com.ing.brokagetest.dto.CustomerAssetDTO;
import com.ing.brokagetest.dto.CustomerOrderDTO;
import com.ing.brokagetest.entity.CustomerOrder;
import com.ing.brokagetest.enums.EnumOrderSide;
import com.ing.brokagetest.enums.EnumOrderStatus;
import com.ing.brokagetest.mapper.BaseMapper;
import com.ing.brokagetest.mapper.CustomerOrderMapper;
import com.ing.brokagetest.repository.BaseRepository;
import com.ing.brokagetest.repository.CustomerOrderRepository;
import com.ing.brokagetest.service.CustomerAssetService;
import com.ing.brokagetest.service.CustomerOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class CustomerOrderHandlerTest extends BaseHandlerTest<CustomerOrderDTO, CustomerOrder, CustomerOrderService> {

    @Mock
    private CustomerOrderRepository repository;

    @Mock
    private CustomerAssetService customerAssetService;

    @BeforeEach
    void setup() {
        mapper = getMapper();
        super.repository = getRepository();
        service = Mockito.spy(getService());
        CustomerAssetDTO assetDTO = getAssetDTO();
        Mockito.lenient().when(customerAssetService.getById(anyLong())).thenReturn(assetDTO);
        Mockito.lenient().when(repository.findById(anyLong())).thenReturn(Optional.of(mapper.serialize(getDTO())));
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
    protected CustomerOrderService getService() {
        return new CustomerOrderHandler(new CustomerOrderMapper(new ModelMapper()), repository, customerAssetService);
    }

    @Override
    protected BaseRepository<CustomerOrder> getRepository() {
        return repository;
    }
}
