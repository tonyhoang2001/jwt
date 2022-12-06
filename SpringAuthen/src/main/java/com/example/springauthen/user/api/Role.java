package com.example.springauthen.user.api;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false,unique = true,length = 50)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role(int id) {
        this.id = id;
    }
}
