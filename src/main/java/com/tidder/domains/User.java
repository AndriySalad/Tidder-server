package com.tidder.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String mail;
    private String password;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    private boolean active;
    private Long postsCount;
    private Long subscribersCount;
    private Long subscriptionsCount;


    @ManyToMany(mappedBy = "likers")
    private List<Post> likedPosts;
    @OneToMany(mappedBy = "user")
    private List<Post> postList;
    @OneToMany(mappedBy = "user")
    private List<Comment> listComment;
}
