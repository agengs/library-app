package com.library.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TransactionRequest {

    public Long CustomerId;
    public Date date;
    public List<Long> bookIdList;
}
