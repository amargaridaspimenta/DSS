package DAOS;
import java.sql.*;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import business.veiculo.Veiculo;
import business.veiculo.VeiculoCombustao;
import business.veiculo.VeiculoEletrico;
import business.veiculo.VeiculoHibrido;
import business.veiculo.Veiculo.Bateria;
import business.veiculo.Veiculo.Motor;


public class VeiculoDAO implements Map<String, Veiculo> {

    private static VeiculoDAO veiculoDAO = null;

    private VeiculoDAO() {
    }

    public static VeiculoDAO getInstance() {
        if (VeiculoDAO.veiculoDAO == null) VeiculoDAO.veiculoDAO = new VeiculoDAO();
        return VeiculoDAO.veiculoDAO;
    }

    @Override
    public int size() {

        int result = 0;

        try {

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM Veiculo");

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
            ResultSet rst = stm.executeQuery("SELECT Matricula FROM Veiculo WHERE Matricula = '" + key.toString() + "'");

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
        Veiculo veiculo = (Veiculo) value;
        return this.containsKey(veiculo.getMatricula());
    }

    @Override
    public Veiculo put(String key, Veiculo veiculo) {
        throw new NullPointerException("put Veiculo não implementado");
    }

    @Override
    public Veiculo get(Object key) {

        Veiculo veiculo = null;

        try (Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD)) {
            String selectVeiculoQuery = "SELECT * FROM Veiculo WHERE Matricula=?";
            try (PreparedStatement stm = connection.prepareStatement(selectVeiculoQuery)) {
                stm.setString(1, key.toString());
                stm.execute();

                try (ResultSet rst = stm.getResultSet()) {
                    if (rst.next()) {
                        String matricula = rst.getString("Matricula");
                        String marca = rst.getString("Marca");
                        Veiculo.Tipo tipo = Veiculo.Tipo.valueOf(rst.getString("Tipo"));

                        switch (tipo) {
                            case Eletrico:
                                veiculo = getVeiculoEletrico(connection, matricula, marca);
                                break;

                            case Combustao:
                                veiculo = getVeiculoCombustao(connection, matricula, marca);
                                break;

                            case Hibrido:
                                veiculo = getVeiculoHibrido(connection, matricula, marca);
                                break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return veiculo;
    }

    private VeiculoEletrico getVeiculoEletrico(Connection connection, String matricula, String marca) throws SQLException {
        String selectQuery = "SELECT * FROM VeiculoEletrico WHERE Matricula=?";
        try (PreparedStatement stm = connection.prepareStatement(selectQuery)) {
            stm.setString(1, matricula);

            try (ResultSet rstResult = stm.executeQuery()) {
                if (rstResult.next()) {
                    return new VeiculoEletrico(
                            matricula,
                            marca,
                            Bateria.valueOf(rstResult.getString("Bateria"))
                    );
                }
            }
        }

        return null;
    }

    private VeiculoCombustao getVeiculoCombustao(Connection connection, String matricula, String marca) throws SQLException {
        String selectQuery = "SELECT * FROM VeiculoCombustao WHERE Matricula=?";
        try (PreparedStatement stm = connection.prepareStatement(selectQuery)) {
            stm.setString(1, matricula);

            try (ResultSet rstResult = stm.executeQuery()) {
                if (rstResult.next()) {
                    return new VeiculoCombustao(
                            matricula,
                            marca,
                            Motor.valueOf(rstResult.getString("Motor"))
                    );
                }
            }
        }

        return null;
    }

    private VeiculoHibrido getVeiculoHibrido(Connection connection, String matricula, String marca) throws SQLException {
        String selectQuery = "SELECT * FROM VeiculoHibrido WHERE Matricula=?";
        try (PreparedStatement stm = connection.prepareStatement(selectQuery)) {
            stm.setString(1, matricula);

            try (ResultSet rstResult = stm.executeQuery()) {
                if (rstResult.next()) {
                    return new VeiculoHibrido(
                            matricula,
                            marca,
                            Motor.valueOf(rstResult.getString("Motor")),
                            Bateria.valueOf(rstResult.getString("Bateria"))
                    );
                }
            }
        }

        return null;
    }

    @Override
    public Veiculo remove(Object key) {
        throw new NullPointerException("remove Veiculo não implementado");
    }

    @Override
    public void putAll(Map<? extends String, ? extends Veiculo> veiculos) {
        throw new NullPointerException("put all Veiculo não implementado");
    }

    @Override
    public Set<Map.Entry<String, Veiculo>> entrySet() {

        Set<Map.Entry<String, Veiculo>> result = new HashSet<Map.Entry<String, Veiculo>>();

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT Matricula FROM Veiculo");

            while (rst.next()) {

                result.add(
                        new AbstractMap.SimpleEntry<String, Veiculo>(
                                rst.getString("Matricula"),
                                get(rst.getString("Matricula"))
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

    @Override
    public void clear() {
        throw new NullPointerException("clear Veiculo não implementado");
    }

    @Override
    public Set<String> keySet() {
        throw new NullPointerException("keySet Veiculo não implementado");
    }

    @Override
    public Collection<Veiculo> values() {
        Collection<Veiculo> res = new HashSet<>();
        try {
            Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Matricula FROM Veiculo");
            while (rs.next()) {
                String idt = rs.getString("Matricula");
                Veiculo veiculo = this.get(idt);
                res.add(veiculo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }
}
