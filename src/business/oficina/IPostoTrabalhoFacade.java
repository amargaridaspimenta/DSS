package business.oficina;

import Execptions.MecanicoException;
import Execptions.PedidoException;
import Execptions.PostoException;
import Execptions.TempoException;

import java.util.List;

public interface IPostoTrabalhoFacade {
    public List<String> getPedidos(int idPosto) throws PostoException;
    public List<String> getPedidosARealizar(int idPosto) throws PostoException;
    public void verifyLogIn(int idMecanico, int posto) throws MecanicoException;
    public Pedido registarPedido(String cliente, String veiculo, String servico, String prazo_posto) throws PostoException, TempoException;
    public String getFirstSlot(String servico);
    public void mandarNotificacao(String mensagem, String cliente);

    }
