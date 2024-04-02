package tw.idv.frank.customer.service;

import tw.idv.frank.common.exception.BaseException;
import tw.idv.frank.customer.model.dto.UpdateCustomerDto;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.customer.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    CommonResult<Customer> addCustomer(Customer customer);

    Customer getCustomerById(Integer id);

    CommonResult<List<Customer>> getCustomerList(Customer selectCondition) throws BaseException;

    CommonResult<Customer> updateCustomer(UpdateCustomerDto updateCustomer);

    CommonResult<Customer> deleteCustomers(Customer deleteCondition);

}
