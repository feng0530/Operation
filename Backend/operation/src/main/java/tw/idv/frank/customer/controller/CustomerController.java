package tw.idv.frank.customer.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.common.constant.ParameterValidation;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.customer.model.dto.UpdateCustomerDto;
import tw.idv.frank.customer.model.entity.Customer;
import tw.idv.frank.customer.service.CustomerService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public CommonResult<Customer> addCustomer(@RequestBody @Valid Customer customer) {
        return customerService.addCustomer(customer);
    }

    @GetMapping("/list")
    public CommonResult<List<Customer>> getCustomerList(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) @Pattern(regexp = ParameterValidation.SPECIAL_SYMBOLS) String name,
            @RequestParam(required = false) @Pattern(regexp = ParameterValidation.NUMBER_VERIFY) String phone,
            @RequestParam(required = false) @Pattern(regexp = ParameterValidation.SPECIAL_SYMBOLS) String location
    ) {
        Customer selectCondition = new Customer(id, name, phone, location);
        return customerService.getCustomerList(selectCondition);
    }

    /**
     * @param customer
     * @return
     */
    @PutMapping("/update")
    public CommonResult<Customer> updateCustomer(@RequestBody @Valid UpdateCustomerDto customer) {
        return customerService.updateCustomer(customer);
    }

    /**
     * @param id
     * @param name
     * @param phone
     * @param location
     * @return
     */
    @DeleteMapping("/delete")
    public CommonResult<Customer> deleteCustomer(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) @Pattern(regexp = ParameterValidation.SPECIAL_SYMBOLS) String name,
            @RequestParam(required = false) @Pattern(regexp = ParameterValidation.NUMBER_VERIFY) String phone,
            @RequestParam(required = false) @Pattern(regexp = ParameterValidation.SPECIAL_SYMBOLS) String location
    ) {
        Customer deleteCondition = new Customer(id, name, phone, location);
        return customerService.deleteCustomers(deleteCondition);
    }

}
