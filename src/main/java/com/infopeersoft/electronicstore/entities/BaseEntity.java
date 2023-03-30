package com.infopeersoft.electronicstore.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;
@Data
@MappedSuperclass
public class BaseEntity {
    @Column(name="create_date",updatable = false)
    @CreationTimestamp
    private LocalDate create;
    @Column(name="update_date",insertable = false)
    @UpdateTimestamp
    private LocalDate update;

}
