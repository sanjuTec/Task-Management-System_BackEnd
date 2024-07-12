package com.tms.task_springboot.dto;

import lombok.Data;

import java.util.Date;
@Data
public class CommentDTO {

    private Long id;

    private String content;

    private Date createdAt;

    private Long taskId;

    private Long userId;

    private String postedBy;


}
