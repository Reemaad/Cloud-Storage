package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Data;

@Data
public class Note {
    private int noteid;
    private String notetitle;
    private String notedescription;
    private int userid;
}
