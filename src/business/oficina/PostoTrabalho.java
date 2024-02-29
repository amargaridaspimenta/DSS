package business.oficina;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import DAOS.*;
import Execptions.MecanicoException;
import Execptions.PedidoException;
import Execptions.TempoException;
import business.oficina.Tempo;
import business.oficina.Servico;
import business.pessoa.Mecanico;


public class PostoTrabalho {

    private int idPosto;
    private Set<String> servicos;
    private ServicoDAO servicoDAO;
    private Set<String> especializacoes;
    private EspecializacaoDAO especializacaoDAO;
    private MecanicoDAO mecanicoDAO;
    private PedidoDAO pedidoDAO;
    private  Mecanico mecanico;


    public PostoTrabalho(int idPosto) throws TempoException {
        this.idPosto = idPosto;
        this.servicos = new HashSet<String>();
        this.servicoDAO = ServicoDAO.getInstance();
        this.especializacoes = new HashSet<String>();
        this.especializacaoDAO = EspecializacaoDAO.getInstance();
        this.mecanicoDAO = MecanicoDAO.getInstance();
        this.pedidoDAO= PedidoDAO.getInstance();
    }


    public int getIdPosto() {
        return this.idPosto;
    }

    public Set<Servico> getServicos() {
        return this.servicos.stream().map(x -> this.servicoDAO.get(x)).collect(Collectors.toSet());
    }

    public Set<Especializacao> getEspecializacao() {
        return this.especializacoes.stream().map(x -> this.especializacaoDAO.get(x)).collect(Collectors.toSet());
    }


    public List<Mecanico> getMecanico() {
        return this.mecanicoDAO.entrySet().stream().map(x -> x.getValue()).filter(x -> x.getIdPosto() == this.idPosto).collect(Collectors.toList());
    }


    public void addServico(String servico) {
        if (this.servicoDAO.containsKey(servico))
            this.servicos.add(servico);
    }


    public void addEspecializacao(String especializacao) {
        if (this.especializacaoDAO.containsKey(especializacao))
            this.especializacoes.add(especializacao);
    }

    public boolean hasServico(String servico) {
        return this.servicos.contains(servico);
    }


    public void verifyLogIn(int idMecanico) throws MecanicoException {
        if (!this.mecanicoDAO.containsKey(String.valueOf(idMecanico))) {
            throw new MecanicoException("Funcionário não existe");
        }
        if (this.mecanicoDAO.get(String.valueOf(idMecanico)).getIdPosto() != this.idPosto) {
            throw new MecanicoException("Funcionário não pertence a este posto");
        }
    }

    public Tempo getPrediction(int duracao) {
        Tempo p = getLastPrazo(duracao);
        if (p.getFim().isBefore(LocalDateTime.now())) {
            return new Tempo(LocalDateTime.now(), LocalDateTime.now());
        }
        return p;
    }

    private Tempo getLastPrazo(int duracao) {
        Tempo prazo = null;
        Pedido last = null;
        for (Pedido p : getPedidosEmEspera()) {
            LocalDateTime fimAtual = p.getTempo().getFim().plusMinutes(5 + duracao);
            if (fimAtual.getHour() >= 17 || fimAtual.isBefore(LocalDateTime.now())) {
                continue;
            }
            if (last != null && last.getTempo().getFim().plusMinutes(5 + duracao).isBefore(p.getTempo().getInicio())) {
                prazo = new Tempo(last.getTempo().getFim().plusMinutes(5), fimAtual);
                break;
            }
            last = p;
        }
        if (prazo == null) {
            LocalDateTime now = LocalDateTime.now();
            if (now.plusMinutes(5+duracao).getHour() >= 17) {
                do {
                    now = now.plusDays(1);
                } while (now.getDayOfWeek() == DayOfWeek.SATURDAY || now.getDayOfWeek() == DayOfWeek.SUNDAY);
                now = now.with(LocalTime.of(9, 5));
                return new Tempo(now, now.plusMinutes(duracao));
            }
            return new Tempo(LocalDateTime.now().plusMinutes(5), LocalDateTime.now().plusMinutes(5+duracao));
        }

        return prazo;
    }
    public boolean isFree(Tempo prazo) {
        for (Pedido p : getPedidosEmEspera()) {
            if (p.getTempo().getFim().plusMinutes(5).isBefore(prazo.getInicio())) {
                continue;
            }
            if (p.getTempo().getInicio().isAfter(prazo.getFim().plusMinutes(5))) {
                return true;
            }
            return false;
        }
        return false;
    }
    public List<Pedido> getPedidosEmEspera() {
        return this.pedidoDAO.entrySet()
                .stream()
                .map(x -> x.getValue())
                .filter(x -> x.getPosto() == this.idPosto && x.getEstado() == Pedido.Estado.PorRealizar)
                .sorted(Comparator.comparing(Pedido::getTempo))
                .collect(Collectors.toList());
    }

    public List<Pedido> getPedidosARealizar() {
        return this.pedidoDAO.entrySet()
                .stream()
                .map(x -> x.getValue())
                .filter(x -> x.getPosto() == this.idPosto && x.getEstado() == Pedido.Estado.ARealizar)
                .sorted(Comparator.comparing(Pedido::getTempo))
                .collect(Collectors.toList());
    }
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Nome: ").append(this.idPosto);
        buffer.append("Especializações: ").append(this.especializacoes);
        return buffer.toString();
    }
}