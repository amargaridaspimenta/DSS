package DAOS;

import java.sql.*;
import java.util.*;

import business.oficina.PostoTrabalho;
import business.oficina.Especializacao;
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

public class PostoTrabalhoDAO implements Map<String, PostoTrabalho> {
    private static PostoTrabalhoDAO postoTrabalhoDAO = null;

    private PostoTrabalhoDAO() {}

    public static PostoTrabalhoDAO getInstance() {
        if (PostoTrabalhoDAO.postoTrabalhoDAO == null) PostoTrabalhoDAO.postoTrabalhoDAO = new PostoTrabalhoDAO();
        return PostoTrabalhoDAO.postoTrabalhoDAO;
    }

    @Override
    public int size() {

        int result = 0;

        try{

            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(*) FROM postotrabalho");

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
            ResultSet rst = stm.executeQuery("SELECT idPosto FROM postotrabalho WHERE idPosto = '" + key.toString() + "'");

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
        PostoTrabalho f = (PostoTrabalho) value;
        return this.containsKey(Integer.toString(f.getIdPosto()));
    }

    @Override
    public PostoTrabalho put(String key, PostoTrabalho f) {
        throw new NullPointerException("put PostoTrabalho não implementado");
    }

    @Override
    public PostoTrabalho get(Object key) {
        PostoTrabalho postoTrabalho = null;

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM postotrabalho WHERE idPosto = " + key);

            if (rst.next()) {
                postoTrabalho = new PostoTrabalho(
                        rst.getInt("idPosto")
                );

                rst = stm.executeQuery("SELECT servico FROM PostoServico WHERE idPosto = '" + key + "'");

                while (rst.next()){
                    postoTrabalho.addServico(rst.getString("Servico"));
                }

                rst = stm.executeQuery("SELECT Especializacao FROM postoespecializacao WHERE idPosto = '" + key + "'");

                while (rst.next()){
                    postoTrabalho.addEspecializacao(rst.getString("Especializacao"));
                }
            }

            rst.close();
            stm.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postoTrabalho;
    }


    @Override
    public PostoTrabalho remove(Object key) {
        throw new NullPointerException("remove PostoTrabalho não implementado");
    }

    @Override
    public void putAll(Map<? extends String, ? extends PostoTrabalho> f) {
        throw new NullPointerException("put all PostoTrabalho não implementado");
    }

    @Override
    public Set<Map.Entry<String,PostoTrabalho>> entrySet() {

        Set<Map.Entry<String,PostoTrabalho>> result = new HashSet<Map.Entry<String,PostoTrabalho>>();

        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT idPosto FROM postotrabalho");

            while (rst.next()){

                result.add(
                        new AbstractMap.SimpleEntry<String,PostoTrabalho>(
                                rst.getString("idPosto"),
                                get(rst.getString("idPosto"))
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
        throw new NullPointerException("clear PostoTrabalho não implementado");
    }

    @Override
    public Set<String> keySet() {
        throw new NullPointerException("keySet PostoTrabalho não implementado");
    }

    @Override
    public Collection<PostoTrabalho> values() {
        Collection<PostoTrabalho> res = new HashSet<>();
        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT idPosto FROM postotrabalho");
            while (rs.next()) {
                String idt = rs.getString("idPosto");
                PostoTrabalho f = this.get(idt);
                res.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    public List<PostoTrabalho> getPostos(String servico) {
        List<PostoTrabalho> postos = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM PostoServico WHERE Servico = '" + servico + "'");
            while (rs.next()) {
                String idPosto = rs.getString("idPosto");
                PostoTrabalho postoTrabalho = this.get(idPosto);
                postos.add(postoTrabalho);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return postos;
    }



}
