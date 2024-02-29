package business.pessoa;

import Execptions.PedidoException;
import business.Notificacao;
import business.oficina.Pedido;

import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import DAOS.PedidoDAO;
import DAOS.EspecializacaoDAO;
import business.oficina.Pedido.Estado;
import business.oficina.Pedido;
import DAOS.NotificacaoDAO;


public class Mecanico {
    private int idMecanico;
    private String nome;
    private Time inicio;
    private Time fim;
    private int idPosto;
    private Set<String> especializacoes;
    private EspecializacaoDAO especializacaoDAO;
    private NotificacaoDAO notificacaoDAO;
    private PedidoDAO pedidoDAO;


    // Construtor da classe
    public Mecanico(int idMecanico, String nome, Time inicio, Time fim, int idPosto) {
        this.idMecanico = idMecanico;
        this.nome = nome;
        this.inicio = inicio;
        this.fim = fim;
        this.idPosto = idPosto;
        this.especializacoes = new HashSet<String>();
        this.especializacaoDAO = EspecializacaoDAO.getInstance();
        this.notificacaoDAO = NotificacaoDAO.getInstance();
        this.pedidoDAO = PedidoDAO.getInstance();
    }

    public int getIdMecanico() {
        return idMecanico;
    }

    public String getNome() {
        return nome;
    }

    public Time getInicio() {
        return inicio;
    }

    public Time getFim() {return fim; }

    public int getIdPosto() {
        return idPosto;
    }
    public Set<String> getEspecializacoes(){
        return this.especializacoes.stream().collect(Collectors.toSet());
    }

    public void addEspecializacao(String especializacao){
        this.especializacoes.add(especializacao);
    }

    public void removeEspecializacao(String especializacao){
        this.especializacoes.remove(especializacao);
    }

    public void registarPedido(Pedido pedido){
        this.pedidoDAO.put(null,pedido);
    }
/*
    public void iniciarPedido(int idPedido) throws PedidoException {
        Pedido pedido = this.pedidoDAO.get(String.valueOf(idPedido));
        if (pedido == null)
            throw new PedidoException("Pedido não identificado");
        if (!pedido.equals(this.getPedidosEmEspera().stream().findFirst().orElse(null)))
            throw new PedidoException("Pedido inválido");
        pedido.setEstado(Pedido.Estado.ARealizar);
        this.pedidoDAO.put(null,pedido);
    }

    public void finalizarPedido(int idPedido, boolean terminada) throws PedidoException{
        if (!this.pedidoDAO.containsKey(String.valueOf(idPedido))) throw new PedidoException("Pedido não existe");
        Pedido pedido = this.pedidoDAO.get(String.valueOf(idPedido));
        pedido.setEstado(Pedido.Estado.Incompleto);
        if (terminada) pedido.setEstado(Pedido.Estado.Concluido);
        this.pedidoDAO.put(null,pedido);
    }

    public void notifcarCliente(int idPedido, String mensagem) throws PedidoException{
        if (!this.pedidoDAO.containsKey(String.valueOf(idPedido))) throw new PedidoException("Pedido nao existe");
        this.notificacaoDAO.put(mensagem, this.pedidoDAO.get(String.valueOf(idPedido)).getCliente());
    }


*/


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IDMecânico: ").append(this.idMecanico);
        stringBuilder.append("\nNome: ").append(this.nome);
        stringBuilder.append("\nInicio de turno: ").append(this.inicio);
        stringBuilder.append("\nFim de turno: ").append(this.fim);
        stringBuilder.append("\nIDPosto: ").append(this.idPosto);
        stringBuilder.append("\nEspcializacao: ").append(this.especializacoes);
        return stringBuilder.toString();
    }
}
