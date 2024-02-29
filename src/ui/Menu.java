package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Menu {

    public interface Handler {
        void execute();
    }

    public interface PreCondition {
        boolean validate();
    }

    private static final Scanner is = new Scanner(System.in);

    private final String titulo;
    private final List<String> opcoes;
    private final List<PreCondition> disponivel;
    private final List<Handler> handlers;


    public Menu(String titulo) {
        this.titulo = titulo;
        this.opcoes = new ArrayList<>();
        this.disponivel = new ArrayList<>();
        this.handlers = new ArrayList<>();
    }

    public void option(String name, PreCondition p, Handler h) {
        this.opcoes.add(name);
        this.disponivel.add(p);
        this.handlers.add(h);
    }


    public void run(int n) {
        int op;
        int count = 1;
        do {
            show();
            op = readOption();
            if (op > 0 && !this.disponivel.get(op-1).validate()) {
                System.out.println("Opção indisponível! Tente novamente.");
            } else if (op > 0) {
                this.handlers.get(op-1).execute();
            }
        } while (op != 0 && n != count++);
    }


    private void show() {
        System.out.println("\n *** "+this.titulo+" *** ");
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.disponivel.get(i).validate()?this.opcoes.get(i):"---");
        }
        System.out.println("0 - Sair");
    }

    private int readOption() {
        int opcao;

        System.out.print("Opção: ");
        try {
            String line = is.nextLine();
            opcao = Integer.parseInt(line);
        }
        catch (NumberFormatException e) {
            opcao = -1;
        }
        if (opcao<0 || opcao>this.opcoes.size()) {
            System.out.println("Opção Inválida");
            opcao = -1;
        }
        return opcao;
    }
}
