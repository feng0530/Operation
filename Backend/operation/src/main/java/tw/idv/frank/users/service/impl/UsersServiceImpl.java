package tw.idv.frank.users.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.common.exception.BaseException;
import tw.idv.frank.users.model.dao.UsersRepository;
import tw.idv.frank.users.model.dto.AddUserDto;
import tw.idv.frank.users.model.dto.UserRes;
import tw.idv.frank.users.model.entity.Users;
import tw.idv.frank.users.service.UsersService;

import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CommonResult<UserRes> addUser(AddUserDto user) {

        Users addUser = modelMapper.map(user, Users.class);
        addUser.setMima(passwordEncoder.encode(user.getMima()));
        addUser.setCreateTime(new Date());
        UserRes userRes = modelMapper.map(usersRepository.save(addUser), UserRes.class);
        return new CommonResult<UserRes>(CommonCode.CREATE, userRes);
    }

    @Override
    public CommonResult<UserRes> getUserById(Integer id) {

        Users user = usersRepository.findById(id).orElse(null);

        if(user == null) {
            throw new BaseException(CommonCode.NOT_FOUND);
        }

        UserRes userRes = modelMapper.map(user, UserRes.class);
        return new CommonResult<UserRes>(CommonCode.READ, userRes);
    }


    @Override
    public CommonResult<List<UserRes>> getUserList() {

        List<Users> usersList = usersRepository.findAll();

        if (usersList.isEmpty()){
            throw new BaseException(CommonCode.NOT_FOUND);
        }

        List<UserRes> userResList = usersList.stream()
                .map(user -> modelMapper.map(user, UserRes.class))
                .collect(Collectors.toList());

        return new CommonResult<List<UserRes>>(CommonCode.READ, userResList);
    }
}
