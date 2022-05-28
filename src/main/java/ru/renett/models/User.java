package ru.renett.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"commentList", "articles"})
@EqualsAndHashCode(exclude = {"commentList", "articles"})
@Entity
@Table(name = "t_user")
public class User {

    public enum State {
        NOT_CONFIRMED, CONFIRMED, DELETED, BANNED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Column(name = "password_hash", length = 64, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @Enumerated(value = EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> commentList;


    public User(String firstName, String secondName, String email, String username) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.userName = username;
    }

    public User(Long id) {
        this.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(secondName, user.secondName) && Objects.equals(email, user.email) && Objects.equals(userName, user.userName) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, email, userName, password);
    }
}
