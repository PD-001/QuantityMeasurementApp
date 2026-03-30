package com.apps.quantitymeasurement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name= "google_id", nullable= false, unique= true)
    private String googleId;

    @Column(name= "email", nullable= false, unique= true)
    private String email;

    @Column(name= "name")
    private String name;

    @Column(name= "picture")
    private String picture;

    @Column(name= "created_at", nullable= false, updatable= false)
    private LocalDateTime createdAt;

    protected UserEntity() {}

    public UserEntity(String googleId, String email, String name, String picture) {
        this.googleId= googleId;
        this.email= email;
        this.name= name;
        this.picture= picture;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt= LocalDateTime.now();
    }

    public Long getId(){ return id; }
    public String getGoogleId(){ return googleId; }
    public String getEmail() { return email; }
    public String getName(){ return name; }
    public String getPicture(){ return picture; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setName(String name){ this.name= name; }
    public void setPicture(String picture) { this.picture= picture; }
}