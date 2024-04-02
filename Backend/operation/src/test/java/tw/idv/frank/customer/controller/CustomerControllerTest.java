package tw.idv.frank.customer.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.exception.BaseException;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.customer.model.entity.Customer;
import tw.idv.frank.customer.service.CustomerService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    @Test
    @Transactional
    public void addCustomerSuccess() throws Exception {
        Customer customer = new Customer(1, "你好", "0944444444", "test");
        CommonResult<Customer> commonResult = new CommonResult<>(CommonCode.CREATE, customer);

        Mockito.when(customerService.addCustomer(Mockito.any(Customer.class))).thenReturn(commonResult);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JSONObject()
                        .put("name", "你好")
                        .put("phone", "0944444444")
                        .put("location", "test")
                        .toString());

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.CREATE.getMsg())))
                .andExpect(jsonPath("$.result.name", equalTo("你好")))
                .andExpect(jsonPath("$.result.phone", equalTo("0944444444")))
                .andExpect(jsonPath("$.result.location", equalTo("test")))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println("返回的結果 : " + body);
    }

    @Test
    public void addCustomerInvalidNameWithEmpty() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"   \",\n" +
                        "  \"phone\" : \"\",\n" +
                        "  \"location\" : \"\"\n" +
                        "}");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andReturn();
    }

    @Test
    public void addCustomerInvalidNameWithSpecialSymbols() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \",\",\n" +
                        "  \"phone\" : \"\",\n" +
                        "  \"location\" : \"\"\n" +
                        "}");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andReturn();
    }

    @Test
    public void addCustomerInvalidPhoneWithLength() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"test\",\n" +
                        "  \"phone\" : \"09\",\n" +
                        "  \"location\" : \"\"\n" +
                        "}");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andReturn();
    }

    @Test
    public void addCustomerInvalidPhoneWithNotInteger() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"test\",\n" +
                        "  \"phone\" : \"s\",\n" +
                        "  \"location\" : \"\"\n" +
                        "}");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andReturn();
    }

    @Test
    public void addCustomerInvalidPhoneWithSpecialSymbols() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"test\",\n" +
                        "  \"phone\" : \",\",\n" +
                        "  \"location\" : \"\"\n" +
                        "}");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andReturn();
    }

    @Test
    public void addCustomerInvalidLocationWithSpecialSymbols() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customer/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"name\" : \"test\",\n" +
                        "  \"phone\" : \"\",\n" +
                        "  \"location\" : \",\"\n" +
                        "}");

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andReturn();
    }

    @Test
    @Transactional
    public void getCustomerListSuccess() throws Exception {
        List<Customer> mockCustomerList = new ArrayList<>();
        mockCustomerList.add(new Customer(1, "Frank", "0987654321", "test1"));
        mockCustomerList.add(new Customer(2, "Feng", "0912345678", "test2"));
        CommonResult<List<Customer>> mockCommonResult = new CommonResult<>(CommonCode.READ, mockCustomerList);

        Mockito.when(customerService.getCustomerList(Mockito.any(Customer.class)))
                .thenReturn(mockCommonResult);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/list");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.READ.getMsg())))
                .andExpect(jsonPath("$.result", hasSize(2)));
    }

    @Test
    @Transactional
    public void getCustomerListByCondition() throws Exception {
        // 自訂義 Mock結果
        List<Customer> mockCustomerList = new ArrayList<>();
        mockCustomerList.add(new Customer(1, "Frank", "0987654321", "test1"));
        CommonResult<List<Customer>> mockCommonResult = new CommonResult<>(CommonCode.READ, mockCustomerList);

        // 當 getCustomerList被呼叫時，返回自訂義的 Mock結果
        Mockito.when(customerService.getCustomerList(Mockito.any(Customer.class)))
                .thenReturn(mockCommonResult);

        // 建立請求
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/list")
                .queryParam("id", "1");

        // 斷言
        mockMvc.perform(requestBuilder) // 發送請求
                .andDo(print()) // 印出 Request 和 Response的詳細內容
                .andExpect(jsonPath("$.message", equalTo(CommonCode.READ.getMsg())))
                .andExpect(jsonPath("$.result", hasSize(1)))
                .andExpect(jsonPath("$.result[0].id", equalTo(1)));
    }

    @Test
    @Transactional
    public void getCustomerListNotFound() throws Exception {
        CommonResult<List<Customer>> mockCommonResult = new CommonResult<>(CommonCode.NOT_FOUND, null);

        Mockito.when(customerService.getCustomerList(Mockito.any(Customer.class)))
                .thenThrow(new BaseException(CommonCode.NOT_FOUND));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/list")
                .queryParam("id", "2");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.NOT_FOUND.getMsg())))
                .andExpect(jsonPath("$.result", nullValue()));
    }

    @Test
    @Transactional
    public void getCustomerListInvalidIdWithSpecialSymbols() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/list")
                .queryParam("id", ",");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_TYPE_ERROR.getMsg())))
                .andExpect(jsonPath("$.result", nullValue()));
    }

    @Test
    @Transactional
    public void getCustomerListInvalidIdWithNotInteger() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/list")
                .queryParam("id", "s");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_TYPE_ERROR.getMsg())))
                .andExpect(jsonPath("$.result", nullValue()));
    }

    @Test
    @Transactional
    public void getCustomerListInvalidNameWithSpecialSymbols() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/list")
                .queryParam("name", ",");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andExpect(jsonPath("$.result", nullValue()));
    }

    @Test
    @Transactional
    public void getCustomerListInvalidPhoneWithLength() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/list")
                .queryParam("phone", "0987654321555");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andExpect(jsonPath("$.result", nullValue()));
    }

    @Test
    @Transactional
    public void getCustomerListInvalidPhoneWithNotInteger() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/list")
                .queryParam("phone", "s");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andExpect(jsonPath("$.result", nullValue()));
    }

    @Test
    @Transactional
    public void getCustomerListInvalidPhoneWithSpecialSymbols() throws Exception {

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/customer/list")
                .queryParam("phone", ",");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(jsonPath("$.message", equalTo(CommonCode.PARAMETER_ERROR.getMsg())))
                .andExpect(jsonPath("$.result", nullValue()));
    }
//
//    @Test
//    @Transactional
//    public void updateCustomer() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .put("/customer/update")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        "  \"id\" : \"50\",\n" +
//                        "  \"name\" : \"b\",\n" +
//                        "  \"phone\" : \"0900000000\",\n" +
//                        "  \"location\" : \"b\"\n" +
//                        "}");
//
//        mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(jsonPath("$.code", equalTo(200)));
//    }
//
//    @Test
//    @Transactional
//    public void deleteCustomer() throws Exception {
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .delete("/customer/delete");
//
//        mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(jsonPath("$.code", equalTo(400)))
//                .andExpect(jsonPath("$.message", equalTo("請輸入欲刪除之資訊!")));
//
//    }
}