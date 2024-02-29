package DAOS;

import java.sql.Statement;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;

import business.Notificacao;

public class NotificacaoDAO implements Map<String, Notificacao> {

    private static NotificacaoDAO notificacaoDAO = null;

    private NotificacaoDAO() {}

    public static NotificacaoDAO getInstance() {
        if (NotificacaoDAO.notificacaoDAO == null) NotificacaoDAO.notificacaoDAO = new NotificacaoDAO();
        return NotificacaoDAO.notificacaoDAO;
    }

    @Override
    public int size() {

        int result = 0;

        try{

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM notificacao");

            if (rst.next()) result = rst.getInt(1);

            rst.close();
            stm.close();
            connection.close();
        }

        catch (Exception e) {e.printStackTrace();}
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
            ResultSet rst = stm.executeQuery("SELECT idNot FROM notificacao WHERE idNot = '" + key.toString() + "'");

            result = rst.next();

            rst.close();
            stm.close();
            connection.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean containsValue(Object value) {
        Notificacao notificacao = (Notificacao) value;
        return this.containsKey(Integer.toString(notificacao.getIdNot()));
    }

    @Override
    public Notificacao put(String key, Notificacao notificacao) {
        Notificacao res = null;
        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            CallableStatement stm = connection.prepareCall("INSERT INTO Notificacao (idNot, lida, Mensagem, Cliente) VALUES (?, ?, ?, ?)");

            if (notificacao.getIdNot() != -1) stm.setInt(1, notificacao.getIdNot());
            else stm.setNull(1, Types.INTEGER);

            stm.setBoolean(2, notificacao.getLida());
            stm.setString(3, notificacao.getMensagem());
            stm.setString(4, notificacao.getCliente());

            stm.execute();
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Notificacao get(Object key) {

        Notificacao notificacao = null;

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Notificacao WHERE idNot = " + key);

            if (rst.next()){

                notificacao = new Notificacao(
                        rst.getInt("idNot"),
                        rst.getBoolean("Lida"),
                        rst.getString("Mensagem"),
                        rst.getString("Cliente")
                );
            }

            rst.close();
            stm.close();
            connection.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return notificacao;
    }

    @Override
    public Notificacao remove(Object key) {
        Notificacao removedNotificacao = this.get(key);

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            stm.executeUpdate("DELETE FROM Notificacao WHERE idNot = " + key);

            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return removedNotificacao;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Notificacao> notificacaos) {
        for(Notificacao a : notificacaos.values()) {
            this.put(Integer.toString(a.getIdNot()), a);
        }
    }

    @Override
    public Set<Map.Entry<String,Notificacao>> entrySet() {

        Set<Map.Entry<String,Notificacao>> result = new HashSet<Map.Entry<String,Notificacao>>();

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT idNot FROM Notificacao");

            while (rst.next()){

                result.add(
                        new AbstractMap.SimpleEntry<String,Notificacao>(
                                rst.getString("IdNot"),
                                get(rst.getString("IdNot"))
                        ));
            }

            rst.close();
            stm.close();
            connection.close();
        }

        catch (Exception e) {e.printStackTrace();}
        return result;
    }

    @Override
    public void clear() {
        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            stm.executeUpdate("TRUNCATE Notificacao");
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<String> keySet() {
        throw new NullPointerException("keySet Notificacao não implementado");
    }

    @Override
    public Collection<Notificacao> values() {
        Collection<Notificacao> res = new HashSet<>();
        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idNot FROM Notificacao");
            while (rs.next()) {
                String idt = rs.getString("IdNot");
                Notificacao notificacao = this.get(idt);
                res.add(notificacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }
}