package com.sipios.projectgraphqlhadrien.models;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "medias")
public class MediaModel {

    @Id
    private Integer id;

    @Column
    private String type;

    @Column
    private String name;

    @Column
    private String duration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "added_by", nullable = false)
    private UserModel addedBy;

    @Column
    private String url;

    public MediaModel(int id, String type, String name, String duration, UserModel addedBy, String url) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.duration = duration;
        this.addedBy = addedBy;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public UserModel getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(UserModel addedBy) {
        this.addedBy = addedBy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
