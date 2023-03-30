package com.infopeersoft.electronicstore.services.impl;

import com.infopeersoft.electronicstore.dtos.PageableResponse;
import com.infopeersoft.electronicstore.dtos.UserDto;
import com.infopeersoft.electronicstore.entities.User;
import com.infopeersoft.electronicstore.exceptions.ResourceNotFoundException;
import com.infopeersoft.electronicstore.helper.Helper;
import com.infopeersoft.electronicstore.repositories.UserRepository;
import com.infopeersoft.electronicstore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Value("${user.profile.image.path}")
    private String imagePath;
    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("In UserServiceImpl class  createUser method start");
       User user= dtoToEntity(userDto);
       User savedUser=this.userRepository.save(user);
       UserDto newDto=entityToDto(savedUser);
        log.info("In UserServiceImpl class  createUser method ended");
        return newDto;
    }
    
    @Override
    public UserDto updateUser(UserDto userDto, long userid) {
        log.info("In UserServiceImpl class  updateUser method start with userid"+userid);
        User user=this.userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User not found with given id !!"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImagename(userDto.getImagename());
        User updatedUser=this.userRepository.save(user);
        UserDto updatedDto=entityToDto(updatedUser);
        log.info("In UserServiceImpl class  updateUser method ended");
        return updatedDto ;
    }

    @Override
    public void deleteUser(long userid) {
        log.info("In UserServiceImpl class  deleteUser method start with userid"+userid);
        User user=this.userRepository.findById(userid).orElseThrow(()->new ResourceNotFoundException("User not found with id"));
      //delete user profile image
        String fullPath = imagePath + user.getImagename();
       try {
           Path path= Paths.get(fullPath);
           Files.delete(path);
       }catch (NoSuchFileException ex){
           log.info("User image not found in folder");
           ex.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

        this.userRepository.delete(user);
        log.info("In UserServiceImpl class  deleteUser method ended");
    }

    @Override
    public PageableResponse<UserDto> getAllUser(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        log.info("In UserServiceImpl class  getAllUser method start");
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<User> page = this.userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        log.info("In UserServiceImpl class  getAllUser method ended");
        return response;
    }

    @Override
    public UserDto getUserById(long userid) {
        log.info("In UserServiceImpl class  getUserById method start with userid"+userid);
        User user = this.userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User not found with id"));
        UserDto userDto = entityToDto(user);
        log.info("In UserServiceImpl class  getUserById method ended");
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        log.info("In UserServiceImpl class  getUserByEmail method start with email"+email);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found with email id"));
        log.info("In UserServiceImpl class  getUserByEmail method ended");
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        log.info("In UserServiceImpl class  searchUser method start");
        List<User> users = this.userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("In UserServiceImpl class  searchUser method ended");
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto=UserDto.builder().userid(savedUser.getUserid())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .gender(savedUser.getGender())
//                .imagename(savedUser.getImagename())
//                .build();
        return mapper.map(savedUser,UserDto.class);

    }

    private User dtoToEntity(UserDto userDto) {
//        User user=User.builder().userid(userDto.getUserid())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .imagename(userDto.getImagename())
//                .build();
        return mapper.map(userDto,User.class);
    }

}
