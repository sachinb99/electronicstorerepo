package com.infopeersoft.electronicstore.dtos;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
@Data
@MappedSuperclass
public class BaseDto {

    @CreationTimestamp
    private LocalDate create;

    @UpdateTimestamp
    private LocalDate update;
}
