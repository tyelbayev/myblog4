package com.example.myblog.model;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private Long id;
    private String title;
    private String content;
    private String imgUrl;
    private LocalDateTime createdAt;
    private String tags;
    private int likeCount;

    public Post() {
    }

    public void setId (Long id){
        this.id = id;
    }

    public void setTitle (String title){
        this.title = title;
    }

    public void setContent (String content){
        this.content = content;
    }

    public void setImgUrl (String imgUrl){
        this.imgUrl = imgUrl;
    }

    public void setCreatedAt (LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public void setTags (String tags){
        this.tags = tags;
    }

    public void setLikeCount (int likeCount){
        this.likeCount = likeCount;
    }

    public String getTitle(){
        return title;
    }

    public String getContent(){
        return content;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public String getTags(){
        return tags;
    }

    public int getLikeCount(){
        return likeCount;
    }

    public Long getId(){
        return id;
    }

}
