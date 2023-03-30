package com.infopeersoft.electronicstore.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long categoryId;
    @NotBlank(message = "title is required")
    @Size(min=4,message = "title must be of minimum 4 character")
    private String title;
    @NotBlank(message = "Description required")
    private String description;
    private String coverImage;
}
