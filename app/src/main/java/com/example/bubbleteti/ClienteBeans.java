package com.example.bubbleteti;

public class ClienteBeans {

    private String nome, cpf, email;

    ClienteBeans(){

    }

    ClienteBeans(String nome, String cpf, String email){
        nome = this.nome;
        cpf = this.cpf;
        email = this.email;
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
}
