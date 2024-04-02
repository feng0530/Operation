package tw.idv.frank.customer.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.exception.BaseException;
import tw.idv.frank.customer.model.dto.UpdateCustomerDto;
import tw.idv.frank.customer.model.mapper.CustomerMapper;
import tw.idv.frank.customer.model.dao.CustomerRepository;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.customer.model.entity.Customer;
import tw.idv.frank.customer.service.CustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper manageMapper;

    /**
     * 手機號碼僅完成長度驗證
     *
     * @param customer
     * @return
     */
    @Override
    public CommonResult<Customer> addCustomer(Customer customer) {
        return new CommonResult<Customer>(CommonCode.CREATE, customerRepository.save(customer));
    };

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public CommonResult<List<Customer>> getCustomerList(Customer selectCondition) throws BaseException {
        return new CommonResult<List<Customer>>(CommonCode.READ, verifyCustomerListIsEmpty(selectCondition));
    }

    @Override
    public CommonResult<Customer> updateCustomer(UpdateCustomerDto updateCondition) {

        verifyCustomerExist(updateCondition.getId());
        verifyUpdateCondition(updateCondition);

        manageMapper.updateCustomers(updateCondition);
        return new CommonResult<Customer>(CommonCode.UPDATE, null);
    }

    @Override
    public CommonResult<Customer> deleteCustomers(Customer deleteCondition) {

        verifyDeleteCondition(deleteCondition);

        manageMapper.deleteCustomers(deleteCondition);
        return new CommonResult<>(CommonCode.DELETE, null);
    }

    private List<Customer> verifyCustomerListIsEmpty(Customer selectCondition) throws BaseException {

        List<Customer> customerList = manageMapper.getCustomerList(selectCondition);

        if (customerList.isEmpty()) {
            throw new BaseException(CommonCode.NOT_FOUND);
        }

        return customerList;
    }

    private void verifyCustomerExist(Integer id) {

        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            throw new BaseException(CommonCode.NOT_FOUND);
        }
    }

    private void verifyUpdateCondition(UpdateCustomerDto updateCondition) {

        if (
                StringUtils.isBlank(updateCondition.getName()) &&
                StringUtils.isBlank(updateCondition.getPhone()) &&
                StringUtils.isBlank(updateCondition.getLocation())
        ) {
            throw new BaseException(CommonCode.UPDATE_CONTENT_IS_NULL);
        }
    }

    private void verifyDeleteCondition(Customer deleteCondition) {

        if (
                deleteCondition.getId() == null &&
                StringUtils.isBlank(deleteCondition.getName()) &&
                StringUtils.isBlank(deleteCondition.getPhone()) &&
                StringUtils.isBlank(deleteCondition.getLocation())
        ) {
            throw new BaseException(CommonCode.DELETE_CONTENT_IS_NULL);
        }
    }
}
