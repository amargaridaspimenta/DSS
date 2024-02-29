package business.veiculo;

import business.oficina.Servico;

import java.sql.Date;
public abstract class Veiculo {

    public enum Tipo {Eletrico, Combustao, Hibrido};

    public enum Motor {Gasoleo, Gasolina};

    public enum Bateria {Litio, Niquel};

    private String matricula;
    private String marca;
    private Tipo tipo;


    public Veiculo(String matricula, String marca, Tipo tipo) {
        this.matricula = matricula;
        this.marca = marca;
        this.tipo = tipo;
    }


    public String getMatricula() {
        return this.matricula;
    }

    public void setMatricula(String Matricula) {
        this.matricula = matricula;
    }


    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String Marca) {
        this.marca = marca;
    }

    public Tipo getTipo() {
        return this.tipo;
    }

    public void setTipo(String Tipo) {this.tipo = tipo;}

    public abstract boolean VeiculoServico(Servico servico);


    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Matricula: ").append(this.matricula);
        buffer.append("\tMarca: ").append(this.marca);
        buffer.append("\tTipo: ").append(this.tipo.name());
        return buffer.toString();
    }
}
