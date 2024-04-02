package tw.idv.frank.users.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.common.dto.LoginRequest;
import tw.idv.frank.common.dto.LoginResponse;
import tw.idv.frank.common.service.TokenService;
import tw.idv.frank.users.model.dto.AddUserDto;
import tw.idv.frank.users.model.dto.UserRes;
import tw.idv.frank.users.model.entity.Users;
import tw.idv.frank.users.service.UsersService;

import java.net.http.HttpRequest;
import java.util.Collection;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public CommonResult<LoginResponse> login(@RequestBody LoginRequest request) {
        return new CommonResult<>(CommonCode.SUCCESS, tokenService.createToken(request));
    }

    @PostMapping("/add")
    public CommonResult<UserRes> addUser(@RequestBody @Valid AddUserDto user) {
        return usersService.addUser(user);
    }

    @GetMapping("/list/{id}")
    public CommonResult<UserRes> getUserById(@PathVariable Integer id) {
        return usersService.getUserById(id);
    }

    @GetMapping("/list")
    public CommonResult<List<UserRes>> getUserList() {
        return usersService.getUserList();
    }

}
