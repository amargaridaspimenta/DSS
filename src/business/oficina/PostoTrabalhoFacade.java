package business.oficina;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import DAOS.*;
import business.Notificacao;
import business.oficina.PostoTrabalho;
import Execptions.*;

public class PostoTrabalhoFacade implements IPostoTrabalhoFacade {

    private PostoTrabalhoDAO postoTrabalhoDAO;
    private PostoTrabalho postoTrabalho;
    private ServicoDAO servicoDAO;
    private  PedidoDAO pedidoDAO;
    private Map<String, Notificacao> notificacao;


    public PostoTrabalhoFacade() {
        this.postoTrabalhoDAO = PostoTrabalhoDAO.getInstance();
        this.servicoDAO = ServicoDAO.getInstance();
        this.pedidoDAO = PedidoDAO.getInstance();
        this.notificacao = NotificacaoDAO.getInstance();
    }

    public void ExistePosto(int idPosto) throws PostoException {
        if (!this.postoTrabalhoDAO.containsKey(String.valueOf(idPosto))) {
            throw new PostoException("Posto não existe");
        }
    }

    public void MecanicoExiste(int idMencaico) throws MecanicoException {
        if (!this.servicoDAO.containsKey(String.valueOf(idMencaico))) {
            throw new MecanicoException("Mecanico não existe");
        }
    }

    @Override
    public List<String> getPedidos(int posto) throws PostoException {
        this.ExistePosto(posto);
        return this.postoTrabalhoDAO.get(String.valueOf(posto))
                .getPedidosEmEspera()
                .stream()
                .sorted(Comparator.comparing(Pedido::getTempo))
                .map(Pedido::toString)
                .collect(Collectors.toList());
    }
    @Override
    public List<String> getPedidosARealizar(int posto) throws PostoException {
        this.ExistePosto(posto);
        return this.postoTrabalhoDAO.get(String.valueOf(posto)).getPedidosARealizar().stream()
                .map(Pedido::toString)
                .collect(Collectors.toList());
    }

    @Override
    public void verifyLogIn(int idMecanico, int posto) throws MecanicoException {
        this.postoTrabalhoDAO.get(String.valueOf(posto)).verifyLogIn(idMecanico);
    }

    private List<PostoTrabalho> getPostos(String servico) {
        return this.postoTrabalhoDAO.getPostos(servico);
    }

    private void ServicoExiste(String servico) throws ServicoException {
        if (!this.servicoDAO.containsKey(servico)) {
            throw new ServicoException("Serviço não existe");
        }
    }
    public void hasServico(int idPosto, String servico)
            throws PostoException, ServicoException{
        this.ExistePosto(idPosto);
        this.ServicoExiste(servico);
        if (!this.postoTrabalhoDAO.get(String.valueOf(idPosto)).hasServico(servico))
            throw new PostoException("Posto Trabalho sem serviços");
    }
    @Override
    public String getFirstSlot(String servico) {
        int duracao = this.servicoDAO.get(servico).getDuracao();

        Optional<PostoTrabalho> optionalFirstPosto = getPostos(servico).stream()
                .min(Comparator.comparing(posto -> posto.getPrediction(duracao).getInicio()));

        if (optionalFirstPosto.isPresent()) {
            PostoTrabalho firstPosto = optionalFirstPosto.get();
            Tempo firstSlot = firstPosto.getPrediction(duracao);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            String firstSlotFormatted = firstSlot.getInicio().format(formatter);
            return firstSlotFormatted + ";" + firstPosto.getIdPosto();
        }

        return null;
    }

    @Override
    public Pedido registarPedido(String cliente, String veiculo, String servico, String prazo_posto) throws PostoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (prazo_posto == null) {
            prazo_posto = LocalDateTime.now().plusMinutes(5).format(formatter);
        }

        String postoEprazo[] = prazo_posto.split(";");
        PostoTrabalho posto = null;
        Pedido p = null;
        LocalDateTime inicio = LocalDateTime.parse(postoEprazo[0], formatter);
        Tempo prazo = new Tempo(inicio, inicio.plusMinutes(this.servicoDAO.get(servico).getDuracao()));
        if (!prazo.isValido()) {
            throw new PostoException("Prazo inválido");
        }
        if (postoEprazo.length == 1) {
            for (PostoTrabalho p1 : getPostos(servico)) {
                if (p1.isFree(prazo)) {
                    posto = p1;
                    break;
                }
            }
            if (posto == null) {
                throw new PostoException("Não há postos disponíveis neste prazo");
            }
            p = new Pedido(prazo, cliente, posto.getIdPosto(), veiculo, servico);
        }
        else {
            p = new Pedido(prazo, cliente, Integer.parseInt(postoEprazo[1]), veiculo, servico);
        }
        return p;
    }
    @Override
    public void mandarNotificacao(String mensagem, String cliente)  {
        Notificacao notificacao = new Notificacao(mensagem, cliente);
        this.notificacao.put(String.valueOf(notificacao.getIdNot()), notificacao);
    }


}