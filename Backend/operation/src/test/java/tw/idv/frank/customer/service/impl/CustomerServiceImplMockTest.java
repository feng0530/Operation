package tw.idv.frank.customer.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.exception.BaseException;
import tw.idv.frank.customer.model.dao.CustomerRepository;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.customer.model.dto.UpdateCustomerDto;
import tw.idv.frank.customer.model.entity.Customer;
import tw.idv.frank.customer.model.mapper.CustomerMapper;
import tw.idv.frank.customer.service.CustomerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerServiceImplMockTest {

    @Autowired
    CustomerService customerService;

    @MockBean
    CustomerMapper customerMapper;

    @MockBean
    CustomerRepository customerRepository;

    @Test
    public void addCustomerSuccess() {
        Customer addCustomer = new Customer(null, "Mock", "0912345678", "test");
        Customer mockCustomer = new Customer(100, "Mock", "0912345678", "test");
        Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(mockCustomer);

        CommonResult<Customer> customerCommonResult = customerService.addCustomer(addCustomer);

        assertEquals(CommonCode.CREATE.getMsg(), customerCommonResult.getMessage());
        assertEquals(100, customerCommonResult.getResult().getId());
        assertEquals("Mock", customerCommonResult.getResult().getName());
        assertEquals("0912345678", customerCommonResult.getResult().getPhone());
        assertEquals("test", customerCommonResult.getResult().getLocation());

    }

    @Test
    public void getCustomerByIdSuccess() {
        Customer mockCustomer = new Customer(100, "Mock", "0912345678", "test");
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(mockCustomer));

        Customer customer = customerService.getCustomerById(1);
        assertEquals(100, customer.getId());
        assertEquals("Mock", customer.getName());
        assertEquals("0912345678", customer.getPhone());
        assertEquals("test", customer.getLocation());
    }

    @Test
    public void getCustomerByIdIsNull() {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));

        Customer customer = customerService.getCustomerById(1);
        assertNull(customer);
    }

    @Test
    public void getCustomerListSuccess() throws BaseException {
        Customer selectCondition = new Customer();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer(1, "aff", "0912345678", "data1"));
        customerList.add(new Customer(2, "ffa", "0987654321", "data2"));

        Mockito.when(customerMapper.getCustomerList(Mockito.any())).thenReturn(customerList);

        CommonResult<List<Customer>> result = customerService.getCustomerList(selectCondition);

        assertEquals(CommonCode.READ.getMsg(), result.getMessage());
        assertEquals(2, result.getResult().size());
    }

    @Test
    public void getCustomerListNotFound() throws BaseException {
        Customer selectCondition = new Customer();
        List<Customer> customerList = new ArrayList<>();

        Mockito.when(customerMapper.getCustomerList(Mockito.any(Customer.class))).thenReturn(customerList);

        BaseException e = assertThrows(BaseException.class, () -> customerService.getCustomerList(selectCondition));

        assertEquals(CommonCode.NOT_FOUND, e.getCommonCode());
    }

    @Test
    public void updateCustomerSuccess() {
        UpdateCustomerDto updateCustomer = new UpdateCustomerDto(18, "update", "0911111111", "uu");

        // 模擬 customerMapper.updateCustomers 方法不返回值
        Mockito.doNothing().when(customerMapper).updateCustomers(Mockito.any(UpdateCustomerDto.class));
        // 模擬 manageRepository.findById 方法返回一個存在的客戶
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Customer(1)));

        CommonResult<Customer> commonResult = customerService.updateCustomer(updateCustomer);

        assertEquals(CommonCode.UPDATE.getMsg(), commonResult.getMessage());
        assertNull(commonResult.getResult());

        // 驗證 updateCustomers 方法被正確調用了一次
        Mockito.verify(customerMapper, Mockito.times(1)).updateCustomers(updateCustomer);
    }

    @Test
    public void updateCustomerParameterIsNull() {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Customer(1)));

        BaseException e = assertThrows(BaseException.class, () -> customerService.updateCustomer(new UpdateCustomerDto(1)));

        assertEquals(CommonCode.UPDATE_CONTENT_IS_NULL, e.getCommonCode());
    }

    @Test
    public void updateCustomerNotFound() {
        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));

        BaseException e = assertThrows(BaseException.class, () -> customerService.updateCustomer(new UpdateCustomerDto(1)));
        assertEquals(CommonCode.NOT_FOUND, e.getCommonCode());

    }

    @Test
    public void deleteCustomersSuccess() {
        Customer deleteCustomer = new Customer(100, null, null, null);

        CommonResult<Customer> commonResult = customerService.deleteCustomers(deleteCustomer);

        assertEquals(CommonCode.DELETE.getMsg(), commonResult.getMessage());
        assertNull(commonResult.getResult());
    }

    @Test
    public void deleteCustomerParameterIsNull() {
        BaseException e = assertThrows(BaseException.class, () -> customerService.deleteCustomers(new Customer()));
        assertEquals(CommonCode.DELETE_CONTENT_IS_NULL, e.getCommonCode());
    }
}