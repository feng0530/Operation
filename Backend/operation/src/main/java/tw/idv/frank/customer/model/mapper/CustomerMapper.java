package tw.idv.frank.customer.model.mapper;

import org.apache.ibatis.annotations.Mapper;
import tw.idv.frank.customer.model.dto.UpdateCustomerDto;
import tw.idv.frank.customer.model.entity.Customer;

import java.util.List;

@Mapper
public interface CustomerMapper {

    List<Customer> getCustomerList(Customer selectCondition);

    void updateCustomers(UpdateCustomerDto customer);

    void deleteCustomers(Customer deleteCustomer);

}
