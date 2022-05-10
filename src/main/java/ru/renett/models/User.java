package ru.renett.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Size(min = 5, message = "В имени/фамилии должно быть не менее 5 знаков")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 5, message = "В имени/фамилии должно быть не менее 5 знаков")
    @Column(name = "second_name")
    private String secondName;

    @Size(min = 5, message = "В имени должно быть не менее 5 знаков")
//    @Pattern(regexp = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Size(min = 5, max = 32, message = "Юзернэйм может содержать от 5 до 32 символов включительно")
    @Column(name = "username", nullable = false, unique = true)
    private String userName;

    @Size(min = 5, message = "Пароль должен содержать не мене 5 символов")
    @Column(name = "password_hash", length = 64, nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @Enumerated(value = EnumType.STRING)
    private State state;

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
