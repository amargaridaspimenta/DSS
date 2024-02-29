package business.veiculo;

import business.oficina.Servico;

public class VeiculoHibrido extends Veiculo{
    private Motor motor;
    private  Bateria bateria;

    public VeiculoHibrido(String matricula, String marca, Motor motor, Bateria bateria){
        super(matricula,marca,Tipo.Hibrido);
        this.motor = motor;
        this.bateria = bateria;
    }

    public Bateria getBateria(){
        return this.bateria;
    }

    public Motor getMotor(){
        return this.motor;
    }

    public String toString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append(("\tMotor: ")).append(this.motor.name());
        buffer.append("\tBateria: ").append(this.bateria.name());
        return buffer.toString();
    }

    public boolean VeiculoServico(Servico servico) {
        if (this.motor == Motor.Gasoleo && servico.getTipo() == Servico.Tipo.CombustaoGasoleo) return true;
        else if (this.motor == Motor.Gasolina && servico.getTipo() == Servico.Tipo.CombustaoGasolina) return true;
        else if (servico.getTipo() == Servico.Tipo.Combustao) return true;
        else if (servico.getTipo() == Servico.Tipo.Eletrico) return true;
        else if (servico.getTipo() == Servico.Tipo.Geral) return true;
        else if (servico.getTipo() == Servico.Tipo.Checkup) return true;
        return false;
    }
}