package DAOS;

import java.sql.*;
import java.util.*;
import business.pessoa.Cliente;
import business.pessoa.Mecanico;

public class ClienteDAO implements Map<String, Cliente> {

    private static ClienteDAO clienteDAO = null;

    private ClienteDAO() {}

    public static ClienteDAO getInstance() {
        if (ClienteDAO.clienteDAO == null) ClienteDAO.clienteDAO = new ClienteDAO();
        return ClienteDAO.clienteDAO;
    }

    @Override
    public int size() {

        int result = 0;

        try {

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM cliente");

            if (rst.next()) result = rst.getInt(1);

            rst.close();
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {

        boolean result = false;

        try {

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT Cliente.NIF FROM cliente WHERE cliente.NIF = '" + key + "'");

            result = rst.next();

            rst.close();
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean containsValue(Object value) {
        Cliente cliente = (Cliente) value;
        return this.containsKey(cliente.getNif());
    }

    @Override
    public Cliente get(Object key) {
        Cliente c = null;

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Cliente WHERE nif = " + key);

            if (rst.next()) {
                c = new Cliente(
                        rst.getString("NIF"),
                        rst.getString("Nome"),
                        rst.getString("Morada"),
                        rst.getString("contacto"),
                        rst.getString("email")
                );
            }

            String selectEspecializacaoQuery = "SELECT veiculo FROM clienteveiculo WHERE cliente=?";
            try (PreparedStatement rstP = connection.prepareStatement(selectEspecializacaoQuery)) {
                rstP.setString(1, key.toString());
                try (ResultSet rstResult = rstP.executeQuery()) {
                    while (rstResult.next()) {
                        c.addVeiculo(rstResult.getString("Veiculo"));
                    }
                }
            }
            rst.close();
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public Cliente put(String key, Cliente cliente) {
        throw new NullPointerException("put Cliente não implementado");
    }


    @Override
    public Cliente remove(Object key) {
        throw new NullPointerException("remove Cliente não implementado");
    }

    @Override
    public void putAll(Map<? extends String, ? extends Cliente> veiculos) {
        throw new NullPointerException("put all Cliente não implementado");
    }

    @Override
    public Set<Map.Entry<String, Cliente>> entrySet() {

        Set<Map.Entry<String, Cliente>> result = new HashSet<Map.Entry<String, Cliente>>();

        try {

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT cliente.Nif FROM cliente");

            while (rst.next()) {

                result.add(
                        new AbstractMap.SimpleEntry<String, Cliente>(
                                rst.getString("Nif"),
                                get(rst.getString("Nif"))
                        ));
            }

            rst.close();
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void clear() {
        throw new NullPointerException("clear Cliente não implementado");
    }

    @Override
    public Set<String> keySet() {
        throw new NullPointerException("keySet Cliente não implementado");
    }

    @Override
    public Collection<Cliente> values() {
        Collection<Cliente> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT NIF FROM cliente");
            while (rs.next()) {
                String idt = rs.getString("Nif");
                Cliente cliente = this.get(idt);
                res.add(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

}

