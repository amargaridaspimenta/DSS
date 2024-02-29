package business.pessoa;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public  class Cliente{

    private String nif;
    private String nome;
    private String morada;
    private String email;
    private String telefone;
    private Set<String> veiculos;



    public Cliente(String nif, String nome, String morada, String email, String telefone){
        this.nif = nif;
        this.nome = nome;
        this.morada = morada;
        this.email = email;
        this.telefone = telefone;
        this.veiculos = new HashSet<String>();
    }


    public String getNome(){
        return this.nome;
    }
    public String getEmail(){
        return this.email;
    }
    public String getMorada(){
        return this.morada;
    }
    public String getTelefone(){
        return this.telefone;
    }
    public String getNif(){
        return this.nif;
    }
    public Set<String> getVeiculos(){
        return this.veiculos.stream().collect(Collectors.toSet());
    }



    public boolean equals(Object obj){
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Cliente that = (Cliente) obj;
        return this.nif.equals(that.getNif());
    }

    public void addVeiculo(String veiculo){
        this.veiculos.add(veiculo);
    }


    public void removeVeiculo(String veiculo){
        this.veiculos.remove(veiculo);
    }

    public boolean contains(String veiculo) {
        return this.veiculos.contains(veiculo);
    }


    public String toString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append("Nome: ").append(this.nome);
        buffer.append("\tEmail: ").append(this.email);
        buffer.append("\tMorada: ").append(this.morada);
        buffer.append("\tTelefone: ").append(this.telefone);
        buffer.append("\tNif: ").append(this.nif);
        buffer.append("\tveiculos: ").append(this.veiculos);
        return buffer.toString();
    }
}
