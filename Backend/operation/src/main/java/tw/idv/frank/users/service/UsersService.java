package tw.idv.frank.users.service;

import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.users.model.dto.AddUserDto;
import tw.idv.frank.users.model.dto.UserRes;
import tw.idv.frank.users.model.entity.Users;

import java.util.List;

public interface UsersService {

    CommonResult<UserRes> addUser(AddUserDto user);

    CommonResult<UserRes> getUserById(Integer id);

    CommonResult<List<UserRes>> getUserList();
}
