package business;

import Execptions.*;
import business.oficina.*;
import business.oficina.PostoTrabalhoFacade;
import business.oficina.IPostoTrabalhoFacade;

import java.util.List;


public class GestorFacade implements IGestorFacade {

    private IPostoTrabalhoFacade postoFacade;
    private IPedidoFacade pedidoFacade;

    public GestorFacade() throws VeiculoException, ServicoException, ClienteException {
        this.postoFacade = new PostoTrabalhoFacade();
        this.pedidoFacade = new PedidoFacade();
    }

    @Override
    public List<String> getPedidos(int posto) throws PostoException {
        return this.postoFacade.getPedidos(posto);
    }
    @Override
    public List<String> getPedidosARealizar(int posto) throws PostoException{
        return  this.postoFacade.getPedidosARealizar(posto);
    }

    @Override
    public void PedidoPossivel(String cliente, String veiculo, String servico) throws ClienteException, ServicoException, VeiculoException {
        this.pedidoFacade.PedidoPossivel(cliente, veiculo, servico);
    }

    @Override
    public void verifyLogIn(int idMecanico, int posto) throws MecanicoException {
        this.postoFacade.verifyLogIn(idMecanico, posto);
    }

    @Override
    public void iniciarPedido(int idMecanico, int idPedido) throws PedidoException {
        this.pedidoFacade.iniciarPedido(idMecanico, idPedido);
    }

    @Override
    public void registarPedido(String cliente, String veiculo, String servico, String prazo) throws PostoException, TempoException {
        this.pedidoFacade.putPedido(this.postoFacade.registarPedido(cliente, veiculo, servico, prazo));
    }

    @Override
    public void finalizarPedido(int posto, int pedido, String mensagem, boolean sucesso) throws PedidoException, PostoException {
        this.postoFacade.mandarNotificacao(mensagem, this.pedidoFacade.finalizarPedido(posto, pedido, sucesso));
    }

    @Override
    public String getFirstSlot(String servico){
        return this.postoFacade.getFirstSlot(servico);
    }

    }
