import java.util.Scanner;

import Execptions.ClienteException;
import Execptions.ServicoException;
import Execptions.VeiculoException;
import ui.MenuText;

public class Main {

    public static void main(String[] args) throws VeiculoException, ServicoException, ClienteException {
        MenuText menu = new MenuText();
        menu.run();
    }
}

