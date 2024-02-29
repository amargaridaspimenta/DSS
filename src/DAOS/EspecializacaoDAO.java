package DAOS;
import java.sql.Statement;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import business.oficina.Especializacao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;


public class EspecializacaoDAO implements Map<String, Especializacao> {

    private static EspecializacaoDAO especializacaoDAO = null;

    private EspecializacaoDAO() {
    }

    public static EspecializacaoDAO getInstance() {
        if (EspecializacaoDAO.especializacaoDAO == null) EspecializacaoDAO.especializacaoDAO = new EspecializacaoDAO();
        return EspecializacaoDAO.especializacaoDAO;
    }

    @Override
    public int size() {

        int result = 0;

        try {

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM especializacao");

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
            ResultSet rst = stm.executeQuery("SELECT TipoEsp FROM especializacao WHERE TipoEsp = '" + key.toString() + "'");

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
        Especializacao especializacao = (Especializacao) value;
        return this.containsKey(especializacao.getTipoesp());
    }

    @Override
    public Especializacao put(String key, Especializacao especializacao) {
        throw new NullPointerException("put Especializacao não implementado");
    }

    @Override
    public Especializacao get(Object key) {

        Especializacao especializacao = null;

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM especializacao WHERE TipoEsp = " + key);

            if (rst.next()) {

                especializacao = new Especializacao(
                        rst.getString("TipoEsp")
                );
            }

            rst.close();
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return especializacao;
    }

    @Override
    public Especializacao remove(Object key) {
        throw new NullPointerException("remove especializacao não implementado");
    }

    @Override
    public void putAll(Map<? extends String, ? extends Especializacao> especializacoes) {
        throw new NullPointerException("put all Especializacao não implementado");
    }

    @Override
    public Set<Map.Entry<String, Especializacao>> entrySet() {

        Set<Map.Entry<String, Especializacao>> result = new HashSet<Map.Entry<String, Especializacao>>();

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT TipoEsp FROM especializacao");

            while (rst.next()) {

                result.add(
                        new AbstractMap.SimpleEntry<String, Especializacao>(
                                rst.getString("TipoEsp"),
                                get(rst.getString("TipoEsp"))
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
        throw new NullPointerException("clear Especializacao não implementado");
    }

    @Override
    public Set<String> keySet() {
        throw new NullPointerException("keySet Especializacao não implementado");
    }

    @Override
    public Collection<Especializacao> values() {
        Collection<Especializacao> res = new HashSet<>();
        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT TipoEsp FROM especializacao");
            while (rs.next()) {
                String idt = rs.getString("TipoEsp");
                Especializacao especializacao = this.get(idt);
                res.add(especializacao);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }
}