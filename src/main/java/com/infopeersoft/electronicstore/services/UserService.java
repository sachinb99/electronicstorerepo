package com.infopeersoft.electronicstore.services;

import com.infopeersoft.electronicstore.dtos.PageableResponse;
import com.infopeersoft.electronicstore.dtos.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto,long userid);
    void deleteUser(long userid);
    PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    UserDto getUserById(long userid);
    UserDto getUserByEmail(String email);
    List<UserDto> searchUser(String keyword);


}
