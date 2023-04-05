package com.infopeersoft.electronicstore.controller;

import com.infopeersoft.electronicstore.config.AppConstants;
import com.infopeersoft.electronicstore.dtos.ApiResponse;
import com.infopeersoft.electronicstore.dtos.ImageResponse;
import com.infopeersoft.electronicstore.dtos.PageableResponse;
import com.infopeersoft.electronicstore.dtos.UserDto;
import com.infopeersoft.electronicstore.services.FileService;
import com.infopeersoft.electronicstore.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUploadPath;
    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        log.info("In UserController class  createUser method start");
        UserDto userDto1 = this.userService.createUser(userDto);
        log.info("In UserController class  createUser method ended");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{userid}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable long userid, @RequestBody UserDto userDto){
        log.info("In UserController class  updateUser method start with userid"+userid);
        UserDto updateUser = this.userService.updateUser(userDto, userid);
        log.info("In UserController class  updateUser method ended");
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{userid}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable long userid){
        log.info("In UserController class  deleteUser method start with userid"+userid);
        this.userService.deleteUser(userid);
        ApiResponse message = ApiResponse
                .builder()
                .message(AppConstants.DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("In UserController class  deleteUser method ended");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    //getall
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                        @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                        @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
                                                        @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
        log.info("In UserController class  getAllUsers method start");
        PageableResponse<UserDto> allUser = this.userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);
        log.info("In UserController class  getAllUsers method ended");
        return new ResponseEntity<>(allUser,HttpStatus.OK);
    }
    //get single
    @GetMapping("/{userid}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userid){
        log.info("In UserController class  getUserById method start with userid"+userid);
        UserDto userById = this.userService.getUserById(userid);
        log.info("In UserController class  getUserById method ended");
        return new ResponseEntity<>(userById,HttpStatus.OK);
    }
    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email){
        log.info("In UserController class  getUserByEmail method start with email"+email);
        UserDto userByEmail = this.userService.getUserByEmail(email);
        log.info("In UserController class  getUserByEmail method ended");
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);
    }
    //search user
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords){
        log.info("In UserController class  searchUser method start");
        List<UserDto> userDtos = this.userService.searchUser(keywords);
        log.info("In UserController class  searchUser method ended");
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }
    //upload user image
    @PostMapping("/image/{userid}")
    public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,@PathVariable long userid ) throws IOException {
        String imageName = this.fileService.uploadFile(image, imageUploadPath);
        UserDto user = this.userService.getUserById(userid);
        user.setImagename(imageName);
        UserDto userDto = this.userService.updateUser(user, userid);
        ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).message("Image upload successfully").success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
    //serve user image
    @GetMapping("/image/{userid}")
    public void serveUserImage(@PathVariable Long userid, HttpServletResponse response) throws IOException {
        UserDto user = this.userService.getUserById(userid);
        log.info("User image name :{}",user.getImagename());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImagename());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }





}
