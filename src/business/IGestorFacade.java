package business;


import Execptions.*;

import java.util.List;

import business.oficina.Pedido;

public interface IGestorFacade {

    public List<String> getPedidos(int posto) throws PostoException;

    public  List<String> getPedidosARealizar(int posto) throws PostoException;

    public void PedidoPossivel(String cliente, String veiculo, String servico) throws ClienteException, ServicoException, VeiculoException;

    public void verifyLogIn(int idMecanico, int posto) throws MecanicoException;

    public void iniciarPedido(int idMecanico, int idPedido) throws PedidoException, MecanicoException;

    public void registarPedido(String cliente, String veiculo, String servico, String prazo_posto) throws PostoException, TempoException;

    public void finalizarPedido(int posto, int pedido, String mensagem, boolean sucesso) throws PedidoException, PostoException;

    public String getFirstSlot(String servico);

    }
