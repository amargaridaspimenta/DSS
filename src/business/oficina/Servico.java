package business.oficina;

import java.sql.Date;
import java.sql.Time;

public class Servico {

    public enum Tipo {Eletrico, Combustao, CombustaoGasolina, CombustaoGasoleo, Geral, Checkup};

    private String nome;
    private Tipo tipo;
    private int duracao;
    private double preco;


    // Construtor da classe
    public Servico(String nome, Tipo tipo, int duracao, double preco) {
        this.nome = nome;
        this.tipo = tipo;
        this.duracao = duracao;
        this.preco = preco;
    }

    // Métodos getters e setters para acessar e modificar os campos
    public String getNome() {
        return nome;
    }
    public Tipo getTipo() {
        return tipo;
    }
    public int getDuracao() {
        return duracao;
    }
    public Double getPreco(){ return preco;}


    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Servico that = (Servico) obj;
        return this.nome.equals(that.getNome());
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("nomeServico:").append(this.nome);
        stringBuilder.append("\tTipo:").append(this.tipo);
        stringBuilder.append("\tDuracao:").append(this.duracao);
        stringBuilder.append("\tPreco:").append(this.preco);
        return stringBuilder.toString();
    }
}
