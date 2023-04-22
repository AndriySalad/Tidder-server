package com.tidder.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    private boolean isActive;
    private Long postsCount;
    private Long subscribersCount;
    private Long subscriptionsCount;
    private String profilePicturePath;
    @Enumerated(value = EnumType.ORDINAL)
    private Role role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Message> messageSet;
    @ManyToMany(mappedBy = "userSet", cascade = CascadeType.ALL)
    private Set<Chat> chatSet;
    @ManyToMany(mappedBy = "likers", cascade = CascadeType.ALL)
    private List<Post> likedPosts;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> listComment;
}
