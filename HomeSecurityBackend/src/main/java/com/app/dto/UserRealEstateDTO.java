package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class UserRealEstateDTO {
    private Integer id;
    private String name;
    private Boolean access;
    private Integer userId;
}
