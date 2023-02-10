package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Data;

@Data
public class File {
    private int fileId;
    private String filename;
    private String contenttype;
    private String filesize;
    private int userid;
    private File filedata;
    private int userId;

}
