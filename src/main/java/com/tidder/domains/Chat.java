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
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime dateCreation;

    @ManyToMany
    @JoinTable(
            name = "users_chats",
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            joinColumns = @JoinColumn(name = "chat_id")
    )
    private Set<User> userSet;
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messageList;
}
