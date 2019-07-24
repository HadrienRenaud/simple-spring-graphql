package com.sipios.projectgraphqlhadrien.models;


import com.sipios.projectgraphqlhadrien.repository.UserRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "medias")
public class MessageModel {
    @Id
    @GeneratedValue
    @Column(name = "media_id")
    private Integer id;

    @Column
    private String content;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author", nullable = false)
    private UserModel author;

    public MessageModel() {
    }

    public MessageModel(String content, UserModel author) {
        this.content = content;
        this.author = author;
        this.createdAt = new Date();
    }

    public MessageModel(Integer id, String content, Date createdAt, UserModel author) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }
}
