package com.rajesh.interviewjudge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "candidates")
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private String resumePath;
}