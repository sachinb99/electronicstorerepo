package com.infopeersoft.electronicstore.dtos;

import com.infopeersoft.electronicstore.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends BaseDto {

    private long userid;
    @Size(min=3, max=20, message="Invalid Name")
    private String name;
//
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message="Invalid User Email")
    @NotBlank(message="Email is required")
    private String email;
    @NotBlank(message="Password is required")
    private String password;
    @Size(min=4,max=6,message="Invalid Gender")
    private String gender;
    @NotBlank(message="Write something about yourself")
    private String about;
    @ImageNameValid
    private String imagename;

}
