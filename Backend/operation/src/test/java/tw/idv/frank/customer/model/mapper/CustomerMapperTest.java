package tw.idv.frank.customer.model.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.frank.customer.model.dto.UpdateCustomerDto;
import tw.idv.frank.customer.model.entity.Customer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerMapperTest {

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void getCustomerList() {
        Customer selectCondition = new Customer(null, "a", "1", null);

        List<Customer> customerList = customerMapper.getCustomerList(selectCondition);
        assertEquals("Jack", customerList.get(2).getName());
    }

    @Test
    @Transactional
    public void deleteCustomers() {
        Customer deleteCondition = new Customer(null, "test", null, null);

        customerMapper.deleteCustomers(deleteCondition);
        List<Customer> customerList = customerMapper.getCustomerList(deleteCondition);
        assertEquals(0,customerList.size());
    }

    @Test
    @Transactional
    public void updateCustomers() {
        UpdateCustomerDto updateCondition = new UpdateCustomerDto(6, "sss", "0900000000", null);
        customerMapper.updateCustomers(updateCondition);

        List<Customer> customer = customerMapper.getCustomerList(new Customer(6));
        assertEquals(6, customer.get(0).getId());
        assertEquals("sss", customer.get(0).getName());
        assertEquals("0900000000", customer.get(0).getPhone());
    }
}