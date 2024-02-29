package business.oficina;

import Execptions.PedidoException;
import Execptions.ServicoException;
import Execptions.ClienteException;
import Execptions.VeiculoException;
public interface IPedidoFacade {

    public void PedidoPossivel(String cliente, String veiculo, String servico) throws ClienteException, ServicoException, VeiculoException;

    public void putPedido(Pedido pedido);

    public void iniciarPedido(int posto, int pedido) throws PedidoException;

    public String finalizarPedido(int posto, int pedido, boolean concluido) throws PedidoException;


    }
