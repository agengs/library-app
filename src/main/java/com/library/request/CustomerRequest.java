package com.library.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CustomerRequest {

    public String name;
    public Date dob;
    public String address;
}
