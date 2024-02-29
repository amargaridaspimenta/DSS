package business.veiculo;

import business.oficina.Servico;

public class VeiculoEletrico extends Veiculo{

    private Bateria bateria;

    public VeiculoEletrico(String matricula, String marca, Bateria bateria){
        super(matricula,marca,Tipo.Eletrico);
        this.bateria = bateria;
    }

    public Bateria getBateria(){
        return this.bateria;
    }

    public String toString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("\tBateria: ").append(this.bateria.name());
        return buffer.toString();
    }

    public boolean VeiculoServico(Servico servico) {
        if (servico.getTipo() == Servico.Tipo.Geral) return true;
        else if (servico.getTipo() == Servico.Tipo.Checkup) return true;
        else if (servico.getTipo() == Servico.Tipo.Eletrico) return true;
        return false;
    }
}