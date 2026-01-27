package com.nagarro.nagp.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String mailId;

}
