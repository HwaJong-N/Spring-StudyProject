package com.ghkwhd.shop.domain.user;

import com.ghkwhd.shop.dto.UserDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @NotBlank
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public User(String id, String name, String password, String email, UserRole role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User() {

    }

    // User 가 생성되기 이전 DTO 로 User 를 생성할 때 사용
    // 비밀번호 암호화까지 동시에 수행
    public static User createUser(UserDTO dto, PasswordEncoder passwordEncoder) {
        User user = User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))    // 암호화해서 User 생성
                .role(UserRole.USER)    // 역할 지정
                .build();
        return user;
    }
}
