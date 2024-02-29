package business.oficina;


import DAOS.ClienteDAO;
import DAOS.PedidoDAO;
import DAOS.ServicoDAO;
import DAOS.VeiculoDAO;
import Execptions.PedidoException;
import Execptions.ServicoException;
import Execptions.ClienteException;
import Execptions.VeiculoException;


public class PedidoFacade implements IPedidoFacade {

    private ClienteDAO clienteDAO;
    private ServicoDAO servicoDAO;
    private VeiculoDAO veiculoDAO;
    private PedidoDAO pedidoDAO;

    public PedidoFacade() throws VeiculoException, ServicoException, ClienteException {
        this.clienteDAO = ClienteDAO.getInstance();
        this.servicoDAO = ServicoDAO.getInstance();
        this.veiculoDAO = VeiculoDAO.getInstance();
        this.pedidoDAO = PedidoDAO.getInstance();
    }

    private void ClienteExiste(String nif, String matricula) throws ClienteException {
        if (!this.clienteDAO.containsKey(nif)) {
            throw new ClienteException("Cliente não existe");
        }
        this.clienteDAO.get(nif).contains(matricula);
    }

    private void ServicoExiste(String servico) throws ServicoException {
        if (!this.servicoDAO.containsKey(servico)) {
            throw new ServicoException("Serviço não existe");
        }
    }

    private void VeiculoExiste(String matricula, String servico) throws VeiculoException {
        if (!this.veiculoDAO.containsKey(matricula)) throw new VeiculoException("Veiculo nao existe");
        this.veiculoDAO.get(matricula).VeiculoServico(this.servicoDAO.get(servico));
    }

    @Override
    public void PedidoPossivel(String cliente, String veiculo, String servico) throws ClienteException, ServicoException, VeiculoException {
        this.ClienteExiste(cliente, veiculo);
        this.ServicoExiste(servico);
        this.VeiculoExiste(veiculo, servico);
    }

    @Override
    public void putPedido(Pedido pedido) {
        this.pedidoDAO.put(null, pedido);
    }

    @Override
    public void iniciarPedido(int posto, int pedido) throws PedidoException {
        checkPedido(posto, pedido);
        this.pedidoDAO.mudarPedido(pedido, "ARealizar", true, false);
    }
    @Override
    public String finalizarPedido(int posto, int pedido, boolean concluido) throws PedidoException {
        checkPedido(posto, pedido);
        String estado = "Incompleto";
        if (concluido) {
            estado = "Concluido";
        }
        this.pedidoDAO.mudarPedido(pedido, estado, false, true);
        return this.pedidoDAO.get(String.valueOf(pedido)).getCliente();
    }

    private void checkPedido(int posto, int pedido) throws PedidoException {
        if (!this.pedidoDAO.containsKey(String.valueOf(pedido))) {
            throw new PedidoException("Pedido não existe");
        }
        if (this.pedidoDAO.get(String.valueOf(pedido)).getPosto() != posto) {
            throw new PedidoException("Pedido não pertence a este posto");
        }
    }

    private void PedidoExiste(int posto, int pedido) throws PedidoException {
        if (!this.pedidoDAO.containsKey(String.valueOf(pedido))) {
            throw new PedidoException("Pedido não existe");
        }
        if (this.pedidoDAO.get(String.valueOf(pedido)).getPosto() != posto) {
            throw new PedidoException("Pedido não pertence a este posto");
        }
    }

}
