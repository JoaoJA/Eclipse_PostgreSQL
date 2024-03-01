package maven.demo;

import java.util.Scanner;

public class Xapp {

    public static void main(String[] args) {
        XDAO dao = new XDAO();
        dao.conectar();
        
        Scanner scanner = new Scanner(System.in);
        int opcao;
        
        do {
            System.out.println("Selecione a operação:");
            System.out.println("1. Inserir usuário");
            System.out.println("2. Atualizar usuário");
            System.out.println("3. Excluir usuário");
            System.out.println("4. Mostrar usuários");
            System.out.println("5. Mostrar usuários do sexo masculino");
            System.out.println("0. Sair");
            System.out.print("Opção: ");
            opcao = scanner.nextInt();
            
            switch(opcao) {
                case 1:
                    inserirUsuario(dao, scanner);
                    break;
                case 2:
                    atualizarUsuario(dao, scanner);
                    break;
                case 3:
                    excluirUsuario(dao, scanner);
                    break;
                case 4:
                    mostrarUsuarios(dao);
                    break;
                case 5:
                    mostrarUsuariosMasculinos(dao);
                    break;
                case 0:
                    System.out.println("Encerrando o programa.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while(opcao != 0);
        
        scanner.close();
        dao.close();
    }
    
    public static void inserirUsuario(XDAO dao, Scanner scanner) {
        System.out.print("Digite o código do usuário: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        
        System.out.print("Digite o login do usuário: ");
        String login = scanner.nextLine();
        
        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();
        
        System.out.print("Digite o sexo do usuário (M/F): ");
        char sexo = scanner.nextLine().charAt(0);
        
        X usuario = new X(codigo, login, senha, sexo);
        if (dao.inserirUsuario(usuario)) {
            System.out.println("Usuário inserido com sucesso!");
        } else {
            System.out.println("Falha ao inserir usuário.");
        }
    }
    
    public static void atualizarUsuario(XDAO dao, Scanner scanner) {
        System.out.print("Digite o código do usuário que deseja atualizar: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        
        System.out.print("Digite o novo login do usuário: ");
        String login = scanner.nextLine();
        
        System.out.print("Digite a nova senha do usuário: ");
        String senha = scanner.nextLine();
        
        System.out.print("Digite o novo sexo do usuário (M/F): ");
        char sexo = scanner.nextLine().charAt(0);
        
        X usuario = new X(codigo, login, senha, sexo);
        if (dao.atualizarUsuario(usuario)) {
            System.out.println("Usuário atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar usuário.");
        }
    }
    
    public static void excluirUsuario(XDAO dao, Scanner scanner) {
        System.out.print("Digite o código do usuário que deseja excluir: ");
        int codigo = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        
        if (dao.excluirUsuario(codigo)) {
            System.out.println("Usuário excluído com sucesso!");
        } else {
            System.out.println("Falha ao excluir usuário.");
        }
    }
    
    public static void mostrarUsuarios(XDAO dao) {
        X[] usuarios = dao.getUsuarios();
        if (usuarios.length > 0) {
            System.out.println("Lista de usuários:");
            for (X usuario : usuarios) {
                System.out.println(usuario.toString());
            }
        } else {
            System.out.println("Nenhum usuário encontrado.");
        }
    }
    
    public static void mostrarUsuariosMasculinos(XDAO dao) {
        X[] usuarios = dao.getUsuariosMasculinos();
        if (usuarios.length > 0) {
            System.out.println("Lista de usuários do sexo masculino:");
            for (X usuario : usuarios) {
                System.out.println(usuario.toString());
            }
        } else {
            System.out.println("Nenhum usuário masculino encontrado.");
        }
    }
}
