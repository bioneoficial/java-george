package com.javageorge.javageorge.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pessoas")
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigoPessoa;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column
    private String email;

    @Column
    private String endereco;

    // Constructors
    public Pessoa() {
    }

    public Pessoa(String nome, String cpf, String email, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.endereco = endereco;
    }

    // Getters and Setters
    public Integer getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Integer codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pessoa pessoa = (Pessoa) o;

        return codigoPessoa != null ? codigoPessoa.equals(pessoa.codigoPessoa) : pessoa.codigoPessoa == null;
    }

    @Override
    public int hashCode() {
        return codigoPessoa != null ? codigoPessoa.hashCode() : 0;
    }
}
