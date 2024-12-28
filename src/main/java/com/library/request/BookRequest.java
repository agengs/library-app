package com.library.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookRequest {

    public String title;
    public String author;
    public String description;
    public String category;
    public Date releaseDate;
}
