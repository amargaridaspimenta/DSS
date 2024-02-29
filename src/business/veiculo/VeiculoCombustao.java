package business.veiculo;

import business.oficina.Servico;
import  business.veiculo.Veiculo;

import java.sql.Date;



public class VeiculoCombustao extends Veiculo{

    private Motor motor;


    public VeiculoCombustao(String matricula, String marca, Motor motor){
        super(matricula,marca,Tipo.Combustao);
        this.motor = motor;
    }


    public Motor getMotor(){
        return this.motor;
    }

    public boolean VeiculoServico(Servico servico) {
        if (servico.getTipo() == Servico.Tipo.Geral) return true;
        else if (servico.getTipo() == Servico.Tipo.Checkup) return true;
        else if (servico.getTipo() == Servico.Tipo.Combustao) return true;
        else if (this.motor == Motor.Gasoleo && servico.getTipo() == Servico.Tipo.CombustaoGasoleo) return true;
        else if (this.motor == Motor.Gasolina && servico.getTipo() == Servico.Tipo.CombustaoGasolina) return true;
        return false;
    }

    public String toString(){
        StringBuilder buffer = new StringBuilder();
        buffer.append(super.toString());
        buffer.append("\tMotor: ").append(this.motor.name());
        return buffer.toString();
    }
}