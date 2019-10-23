package com.example.demo.dtos;

import com.example.demo.enums.ContactType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private Integer personId;
    private ContactType contactType;
    private String contactDetail;

}