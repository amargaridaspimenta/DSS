package DAOS;

import java.sql.*;
import java.util.*;

import business.oficina.Servico;

public class ServicoDAO implements Map<String, Servico> {

    private Connection connection;

    private static ServicoDAO servicoDAO = null;

    public static ServicoDAO getInstance() {
        if (ServicoDAO.servicoDAO == null) ServicoDAO.servicoDAO = new ServicoDAO();
        return ServicoDAO.servicoDAO;
    }

    public ServicoDAO() {
            this.connection = connection;
        }


    @Override
    public int size() {

        int result = 0;

        try {

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM servico");

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
            ResultSet rst = stm.executeQuery("SELECT nome FROM servico WHERE nome = '" + key + "'");

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
        Servico servico = (Servico) value;
        return this.containsKey(servico.getNome());
    }

    @Override
    public Servico get(Object key) {

        Servico servico = null;

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            CallableStatement stm = connection.prepareCall("SELECT * FROM Servico WHERE Nome='" + key + "'");
            stm.execute();

            ResultSet rst = stm.getResultSet();

            if (rst.next()) {

                servico = new Servico(
                        rst.getString("Nome"),
                        Servico.Tipo.valueOf(rst.getString("Tipo")),
                        rst.getInt("Duracao"),
                        rst.getDouble("Preco"));
            }

            rst.close();
            stm.close();
            connection.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return servico;
    }

    @Override
    public Servico put(String key, Servico servico) {
        throw new NullPointerException("put servico não implementado");
    }

    @Override
    public Servico remove(Object key) {
        throw new NullPointerException("remove servico não implementado");
    }


    @Override
    public void putAll(Map<? extends String, ? extends Servico> servicos) {
        throw new NullPointerException("put all servicos não implementado");
    }

    @Override
    public void clear() {
        throw new NullPointerException("clear Servico não implementado");
    }


    @Override
    public Set<String> keySet() {
        throw new NullPointerException("keySet Servico não implementado");
    }


    @Override
    public Collection<Servico> values() {
        Collection<Servico> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT nome FROM servico");
            while (rs.next()) {
                String idt = rs.getString("nome");
                Servico servico = this.get(idt);
                res.add(servico);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Map.Entry<String, Servico>> entrySet() {

        Set<Map.Entry<String, Servico>> result = new HashSet<Map.Entry<String, Servico>>();

        try {

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT nome FROM servico");

            while (rst.next()) {

                result.add(
                        new AbstractMap.SimpleEntry<String, Servico>(
                                rst.getString("nome"),
                                get(rst.getString("nome"))
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
}
