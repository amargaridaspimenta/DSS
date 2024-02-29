package ui;
import java.util.Scanner;

import Execptions.*;
import business.GestorFacade;
import business.IGestorFacade;


import java.util.Scanner;

public class MenuText {
    private static Scanner scanner;

    private IGestorFacade gestorFacade;


    public MenuText() throws VeiculoException, ServicoException, ClienteException {
        scanner = new Scanner(System.in);
        this.gestorFacade = new GestorFacade();
    }

    public void run() {
        Menu menu = new Menu("Escolha o tipo de Posto");
        menu.option("Posto de veículos Elétricos", () -> true, () -> menuPrincipal(1));
        menu.option("Posto de veículos CombustãoGasóleo", () -> true, () -> menuPrincipal(2));
        menu.option("Posto de veículos CombustãoGasolina", () -> true, () -> menuPrincipal(3));
        menu.option("Posto de veículos híbridos", () -> true, () -> menuPrincipal(4));

        menu.run(-1);
    }


    private void menuPrincipal(int posto) {
        System.out.println("Bem vindo ao E.S.Ideal!");
        if (!logInMenu(posto)) return;
        Menu menu = new Menu("Menu Principal");
        menu.option("Registar pedido", () -> true, () -> registarPedido());
        menu.option("Iniciar pedido", () -> true, () -> iniciarPedido(posto));
        menu.option("Finalizar checkup", () -> false, () -> {});
        menu.option("Finalizar pedido", () -> true, () -> finalizarPedido(posto));
        menu.option("Entregar aviso", () -> false, () -> {});
        menu.option("Ver Pedidos por realizar", () -> true, () -> getPedidos(posto));
        menu.option("Ver Pedidos a realizar", () -> true, () -> getPedidosARealizar(posto));
        menu.run(1);
    }


    private void iniciarPedido(int posto) {
        getPedidos(posto);
        System.out.println("Insere o id do pedido que deseja dar início (0 - Sair):");
        int pedido = scanner.nextInt();
        if (pedido == 0) return;
        try {
            this.gestorFacade.iniciarPedido(posto, pedido);
        } catch (PedidoException | MecanicoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void finalizarPedido(int posto) {
        getPedidosARealizar(posto);
        System.out.println("Insere o id do pedido (0 - Sair):");
        int pedido = scanner.nextInt();
        scanner.nextLine();
        if (pedido == 0) return;
        System.out.println("Foi realizado com sucesso? (Y/n):");
        String resposta = scanner.nextLine();
        System.out.println("Insere a mensagem:");
        String mensagem = scanner.nextLine();
        boolean sucesso = true;
        if (resposta.equals("n") || resposta.equals("N")) {
            sucesso = false;
        }
        try {
            this.gestorFacade.finalizarPedido(posto, pedido, mensagem, sucesso);
        } catch (PedidoException | PostoException e) {
            System.out.println(e.getMessage());
        }
    }


    private boolean logInMenu(int posto) {
        try {
            System.out.println("Para iniciar turno insira o seu id:");
            int idMecanico = (scanner.nextInt());
            this.gestorFacade.verifyLogIn(idMecanico, posto);
            return true;
        } catch (MecanicoException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void getPedidos(int posto) {
        try {
            this.gestorFacade.getPedidos(posto).forEach(x -> System.out.println(x));
        } catch (PostoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getPedidosARealizar(int posto) {
        try {
            this.gestorFacade.getPedidosARealizar(posto).forEach(x -> System.out.println(x));
        } catch (PostoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registarPedido() {

        try {
            System.out.println("Insere o cliente:");
            String cliente = scanner.nextLine();
            cliente = scanner.nextLine();
            System.out.println("Insere o veiculo:");
            String veiculo = scanner.nextLine();
            System.out.println("Insere o serviço:");
            String servico = scanner.nextLine();

            this.gestorFacade.PedidoPossivel(cliente, veiculo, servico);

            agreementMenu(cliente, veiculo, servico, this.gestorFacade.getFirstSlot(servico));
        }

        catch (ClienteException
               | ServicoException
               | VeiculoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void agreementMenu(String cliente, String veiculo, String servico, String prazo_posto) {
        System.out.println("Disponivel em " + prazo_posto.split(";")[0]);
        Menu menu = new Menu("O cliente aceita a hora?");
        menu.option("Sim", () -> true, () -> registarPedido(cliente, veiculo, servico, prazo_posto));
        menu.option("Não", () -> true, () -> registarPedido(cliente, veiculo, servico, definePrazo(cliente, veiculo, servico)));
        menu.run(1);
    }


    private String definePrazo(String cliente, String veiculo, String servico) {
        System.out.println("Insira a nova hora de início (DD/MM/AAAA HH:MM):");
        String inicio = scanner.nextLine();
        return inicio;
    }

    private void registarPedido(String cliente, String veiculo, String servico, String prazo_posto) {
        try {
            this.gestorFacade.registarPedido(cliente, veiculo, servico, prazo_posto);
        } catch (PostoException | TempoException e) {
            System.out.println(e.getMessage());
        }
    }
}
