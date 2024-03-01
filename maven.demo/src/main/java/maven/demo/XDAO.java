package maven.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class XDAO {
    protected Connection conexao;
    
    public XDAO() {
        conexao = null;
    }
    
    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                    
        String serverName = "localhost";
        String mydatabase = "teste";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
        String username = "ti2cc";
        String password = "ti@cc";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) { 
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
        }

        return status;
    }
    
    public boolean close() {
        boolean status = false;
        
        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }
    
    public boolean inserirUsuario(X usuario) {
        String sql = "INSERT INTO minhatab (codigo, login, senha, sexo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, usuario.getCodigo());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, String.valueOf(usuario.getSexo()));
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir usuário: " + e.getMessage());
        }
        return false;
    }
    
    public X[] getUsuariosMasculinos() {
        List<X> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM minhatab WHERE sexo = 'M'";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                char sexo = rs.getString("sexo").charAt(0);
                usuarios.add(new X(codigo, login, senha, sexo));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários do sexo masculino: " + e.getMessage());
        }
        return usuarios.toArray(new X[0]);
    }
    
    public X[] getUsuarios() {
        List<X> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM minhatab";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int codigo = rs.getInt("codigo");
                String login = rs.getString("login");
                String senha = rs.getString("senha");
                char sexo = rs.getString("sexo").charAt(0);
                usuarios.add(new X(codigo, login, senha, sexo));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar usuários: " + e.getMessage());
        }
        return usuarios.toArray(new X[0]);
    }
    
    public boolean atualizarUsuario(X usuario) {
        String sql = "UPDATE minhatab SET login=?, senha=?, sexo=? WHERE codigo=?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, String.valueOf(usuario.getSexo()));
            stmt.setInt(4, usuario.getCodigo());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
        }
        return false;
    }
    
    public boolean excluirUsuario(int codigo) {
        String sql = "DELETE FROM minhatab WHERE codigo=?";
        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, codigo);
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
        }
        return false;
    }
}
