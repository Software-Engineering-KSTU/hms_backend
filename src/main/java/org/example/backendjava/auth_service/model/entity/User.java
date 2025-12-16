package org.example.backendjava.auth_service.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@EntityListeners(org.springframework.data.jpa.domain.support.AuditingEntityListener.class)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    /**
     * Флаг блокировки пользователя.
     */
    @Column(nullable = false)
    private boolean isBlocked = false;

    /**
     * Дата и время блокировки.
     */
    private LocalDateTime blockedAt;

    /**
     * Причина блокировки.
     */
    @Column(length = 500)
    private String blockReason;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private transient Patient patient;

    @OneToMany(mappedBy = "user")
    private transient List<Token> tokens;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Возвращает true, если аккаунт активен (не заблокирован).
     */
    @Override
    public boolean isEnabled() {
        return !isBlocked;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}