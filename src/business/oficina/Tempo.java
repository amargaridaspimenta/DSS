package business.oficina;


import Execptions.TempoException;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Tempo implements Comparable<Tempo>{
    private LocalDateTime inicio;
    private LocalDateTime fim;


    public Tempo(LocalDateTime inicio, LocalDateTime fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Tempo clone() {
        try {
            return new Tempo(this.inicio,this.fim);
        }
        catch (Exception e) {
            return null;
        }
    }
    public Tempo add(int duracao){
        return new Tempo(this.fim.plusMinutes(5), this.fim.plusMinutes(5+duracao));
    }

    public LocalDateTime getInicio() {
        return this.inicio;
    }


    public LocalDateTime getFim() {
        return this.fim;
    }


    private static boolean tempoPossivel(LocalDateTime inicio, LocalDateTime fim){
        return inicio.isBefore(fim);
    }



    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\nInicio: ").append(this.inicio);
        buffer.append("\nFim: ").append(this.fim);
        return buffer.toString();
    }

    @Override
    public int compareTo(Tempo other) {
        return this.fim.compareTo(other.getFim());
    }

    public boolean isValido() {
        boolean mesmoDia = this.getInicio().toLocalDate().isEqual(this.getFim().toLocalDate());
        DayOfWeek diaDaSemanaInicio = this.getInicio().getDayOfWeek();
        int horaInicio = this.getInicio().getHour();
        int horaFim = this.getFim().getHour();

        return mesmoDia &&
                (diaDaSemanaInicio.compareTo(DayOfWeek.MONDAY) >= 0 && diaDaSemanaInicio.compareTo(DayOfWeek.FRIDAY) <= 0) &&
                (horaInicio >= 9 && horaFim <= 17);
    }
}
