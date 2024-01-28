package ru.itgirl.libraryproject.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name= "username",nullable = false)
    @Setter
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority", nullable = false)
    private AuthorityType authorityType;

}
