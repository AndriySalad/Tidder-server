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
    private String messageText;
    private Long countLikes;
    private Long countComments;
    private String attachmentPath;
    @CreationTimestamp
    private LocalDateTime creationDate;
    @Enumerated(value = EnumType.ORDINAL)
    private PostStatus postStatus;


    @ManyToMany
    @JoinTable(
            name = "liker_and_post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likers;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> listComments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
