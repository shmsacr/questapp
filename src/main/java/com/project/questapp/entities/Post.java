package com.project.questapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name ="post")
@Data
public class Post {
    @Id
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)//user objesini hemen çekme postu çektiğimizde user gelmesin
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) //Kullanıcı silinirse o kullanıcının bütün postları da silinsin
    @JsonIgnore
    User user;
    String title;
    @Lob
    @Column(columnDefinition = "text")
    String text;
}
