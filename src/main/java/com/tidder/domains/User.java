package com.tidder.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String mail;
    private String password;
    private boolean isActive;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @CreationTimestamp
    private LocalDateTime creationDateTime;
    private Long postsCount;
    private Long subscribersCount;
    private Long subscriptionsCount;
    private String profilePicturePath;

    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<User> friends;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        return this.mail;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return this.isActive;
    }
    @Override
    public String getPassword(){
        return this.password;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
