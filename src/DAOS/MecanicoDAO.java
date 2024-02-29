package DAOS;

import java.sql.*;
import java.util.*;

import business.pessoa.Mecanico;

public class MecanicoDAO implements Map<String, Mecanico> {

    private static MecanicoDAO mecanicoDAO = null;

    private Connection connection;


    public static MecanicoDAO getInstance() {
        if (MecanicoDAO.mecanicoDAO == null) {
            MecanicoDAO.mecanicoDAO = new MecanicoDAO();
        }
        return MecanicoDAO.mecanicoDAO;
    }


    public int size() {

        int result = 0;

        try {

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM Mecanico");

            if (rst.next()) {
                result = rst.getInt(1);
            }

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
            ResultSet rst = stm.executeQuery("SELECT Mecanico.idMecanico FROM Mecanico WHERE Mecanico.idMecanico = '" + key + "'");

            if (rst.next()) {
                result = true;
            }

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
        Mecanico mecanico = (Mecanico) value;
        return this.containsKey(mecanico.getIdMecanico());
    }

    @Override
    public Mecanico get(Object key) {
        Mecanico m = null;

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Mecanico WHERE idMecanico = " + key);

            if (rst.next()) {
                m = new Mecanico(
                        rst.getInt("idMecanico"),
                        rst.getString("Nome"),
                        rst.getTime("Inicio"),
                        rst.getTime("Fim"),
                        rst.getInt("idPosto")
                );
            }

            String selectEspecializacaoQuery = "SELECT especializacao FROM mecanicoespecializacao WHERE idMecanico=?";
            try (PreparedStatement rstP = connection.prepareStatement(selectEspecializacaoQuery)) {
                rstP.setString(1, key.toString());
                try (ResultSet rstResult = rstP.executeQuery()) {
                    while (rstResult.next()) {
                        m.addEspecializacao(rstResult.getString("Especializacao"));
                    }
                }
            }
            rst.close();
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m;
    }

        @Override
    public Mecanico put(String key, Mecanico value) {
        throw new NullPointerException("put Mecanico não implementado");
    }

    @Override
    public Mecanico remove(Object key) {
        throw new NullPointerException("remove Mecanico não implementado");
    }

    @Override
    public void putAll(Map<? extends String, ? extends Mecanico> especializacao) {
        throw new NullPointerException("put all Cliente não implementado");
    }

    public void clear() {
        throw new NullPointerException("clear Mecanico não implementado");
    }


    @Override
    public Set<String> keySet() {
        throw new NullPointerException("keySet Mecanico não implementado");
    }

    @Override
    public Collection<Mecanico> values() {
        Collection<Mecanico> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idMecanico FROM mecanico");
            while (rs.next()) {
                String idt = rs.getString("Nif");
                Mecanico mecanico = this.get(idt);
                res.add(mecanico);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Map.Entry<String, Mecanico>> entrySet() {

        Set<Map.Entry<String, Mecanico>> result = new HashSet<Map.Entry<String, Mecanico>>();

        try {

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT mecanico.idMecanico FROM mecanico");

            while (rst.next()) {

                result.add(
                        new AbstractMap.SimpleEntry<String, Mecanico>(
                                rst.getString("idMecanico"),
                                get(rst.getInt("idMecanico"))
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
