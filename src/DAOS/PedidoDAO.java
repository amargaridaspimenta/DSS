package DAOS;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import business.oficina.Pedido;
import business.oficina.Pedido.Estado;
import business.oficina.Tempo;

public class PedidoDAO implements Map<String, Pedido> {
    private static PedidoDAO pedidoDAO = null;

    private PedidoDAO() {}

    public static PedidoDAO getInstance() {
        if (PedidoDAO.pedidoDAO == null) PedidoDAO.pedidoDAO = new PedidoDAO();
        return PedidoDAO.pedidoDAO;
    }

    @Override
    public int size() {

        int result = 0;

        try{

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM pedido");

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
            ResultSet rst = stm.executeQuery("SELECT idPedido FROM pedido WHERE idPedido = '" + key.toString() + "'");

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
        Pedido p = (Pedido) value;
        return this.containsKey(Integer.toString(p.getIdPedido()));
    }

    @Override
    public Pedido put(String key, Pedido p) {
        Pedido pedido = null;

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();

            CallableStatement insertStm = connection.prepareCall("INSERT INTO pedido (idPedido, Estado, Inicio, Fim, Cliente, Posto, Veiculo, Servico) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            insertStm.setString(1, key);
            insertStm.setString(2, p.getEstado().toString());
            insertStm.setTimestamp(3, Timestamp.valueOf(p.getTempo().getInicio()));
            insertStm.setTimestamp(4, Timestamp.valueOf(p.getTempo().getFim()));
            insertStm.setString(5, p.getCliente());
            insertStm.setInt(6, p.getPosto());
            insertStm.setString(7, p.getVeiculo());
            insertStm.setString(8, p.getServico());
            insertStm.executeUpdate();
            insertStm.close();


            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
    public Pedido get(Object key) {

        Pedido pedido = null;

        try{

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            CallableStatement callStm = connection.prepareCall("SELECT * FROM pedido WHERE idPedido='" + key + "'");
            Statement stm = connection.createStatement();

            callStm.execute();

            ResultSet rst = callStm.getResultSet();


            if (rst.next()) {
                Tempo tempo = new Tempo(
                        rst.getTimestamp("Inicio").toLocalDateTime(),
                        rst.getTimestamp("Fim").toLocalDateTime());

                pedido = new Pedido(
                        rst.getInt("idPedido"),
                        Estado.valueOf(rst.getString("Estado")),
                        tempo,
                        rst.getString("Cliente"),
                        rst.getInt("Posto"),
                        rst.getString("Veiculo"),
                        rst.getString("Servico"));
            }

            rst.close();
            stm.close();
            callStm.close();
            connection.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return pedido;
    }


    @Override
    public Pedido remove(Object key) {
        Pedido removedPedido = null;

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            CallableStatement callStm = connection.prepareCall("DELETE FROM pedido WHERE idPedido = ?");
            callStm.setString(1, key.toString());
            callStm.executeUpdate();

            removedPedido = get(key);

            callStm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return removedPedido;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Pedido> p) {
        for (Pedido entry : p.values()) {
            put(Integer.toString(entry.getIdPedido()), entry);
        }
    }

    @Override
    public Set<Map.Entry<String,Pedido>> entrySet() {
        Set<Map.Entry<String,Pedido>> result = new HashSet<Map.Entry<String,Pedido>>();

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT idPedido FROM pedido");

            while (rst.next()) {

                result.add(
                        new AbstractMap.SimpleEntry<String,Pedido>(
                                rst.getString("idPedido"),
                                get(rst.getString("idPedido"))
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
            stm.executeUpdate("TRUNCATE TABLE pedido");
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<String> keySet() {
        throw new NullPointerException("keySet Pedido não implementado");
    }

    @Override
    public Collection<Pedido> values() {
        Collection<Pedido> res = new HashSet<>();
        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idPedido FROM pedido");
            while (rs.next()) {
                String idt = rs.getString("idPedido");
                Pedido f = this.get(idt);
                res.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public void mudarPedido(int pedido, String estado, boolean inicio, boolean fim) {
        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            CallableStatement callStm = connection.prepareCall("UPDATE pedido SET estado = ? WHERE idPedido = ?");
            callStm.setString(1, estado);
            callStm.setInt(2, pedido);
            callStm.executeUpdate();

            if (inicio) {
                callStm = connection.prepareCall("UPDATE pedido SET inicio = ? WHERE idPedido = ?");
                callStm.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                callStm.setInt(2, pedido);
                callStm.executeUpdate();
            }

            if (fim) {
                callStm = connection.prepareCall("UPDATE pedido SET fim = ? WHERE idPedido = ?");
                callStm.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                callStm.setInt(2, pedido);
                callStm.executeUpdate();
            }

            callStm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
