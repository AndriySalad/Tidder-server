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
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long countLikes;
    private Long countComments;
    private String attachmentPath;
    @CreationTimestamp
    private LocalDateTime creationDate;


    @ManyToMany
    @JoinTable(
            name = "likers_posts",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likers;
    @OneToMany(mappedBy = "post")
    private List<Comment> listComments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
