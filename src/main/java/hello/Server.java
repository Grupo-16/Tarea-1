package hello;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {

    private ServerSocket server;
    private int port;

    private List<User> online_users;

    /* 
    public static void main(String[] args) throws IOException{
        new Server(12345).Run();
        return;
    }
    */

    public Server(int port){
        this.port = port;
        this.online_users = new ArrayList<User>();
    }

    public void Run() throws IOException{
        server = new ServerSocket(port);
        // [Manejar excepciones del servidor aqui]
        System.out.println("Servidor corriendo en el puerto " + Integer.toString(port));

        // Escuchando nuevas conexiones
        while(true){
            Socket client = server.accept();

            Scanner scan2 = new Scanner(client.getInputStream());
            String username = scan2.nextLine();

            User user = new User(client, username);
            this.online_users.add(user);
            System.out.println(username + " se ha conectado.");
            // [ Registrar en log este usuario ]
            new Thread( new ServerMessage(this, user)).start(); // Un thread de escucha para este usuario
        }
    }

    public void RemoveUser(User user){
        this.online_users.remove(user);
        return;
    }

    public void SendServerMsg(String message){
        for(User user : this.online_users){
            user.SendMessage(message);
        }
        return;
    }

    public void SendUserMsg(String message, User sender){
        for(User user : this.online_users){
            if (user != sender){
                user.SendMessage(sender.GetName() + ": " + message);
            }
        }
        return;
    }
}

class ServerMessage implements Runnable {
    public Server server;
    public User user;

    public ServerMessage(Server server, User user){
        this.server = server;
        this.user = user;
    }

    public void run(){
        server.SendServerMsg(user.GetName() + " se ha conectado.");

        // Escuchar nuevos mensajes del usuario
        Scanner scan = new Scanner( this.user.GetInputStream() ); 
        while( scan.hasNextLine() ){
            String user_message = scan.nextLine();
            if(user_message.length() > 0){
                server.SendUserMsg(user_message, user);
            }
        }

        server.SendServerMsg(user.GetName() + " se ha desconectado.");
        // [LOG Registrar en el log usuario se desconecto]
        server.RemoveUser(user);
        scan.close();
        return;
    }
}

// Representa al usuario en el servidor
class User{
    private InputStream inputstream;
    private PrintStream printstream;
    private String username;

    public User(Socket client, String username) throws IOException {
        this.inputstream = client.getInputStream();
        this.printstream = new PrintStream(client.getOutputStream());
        this.username = username;
    }

    public InputStream GetInputStream(){
        return this.inputstream;
    }

    public void SendMessage(String message){
        this.printstream.println( message );
    }

    public String GetName(){
        return this.username;
    }
}