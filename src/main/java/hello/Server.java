package hello;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Server {

    private ServerSocket server;
    private int port;

    private List<User> online_users;
    private int users_count = 0;

    final static Logger logger = Logger.getLogger("server");

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
            User user = AddUser(client);
            System.out.println(user.GetName() + " se ha conectado.");
            logger.info( user.GetName() + " se ha conectado." );
        }
    }

    public User AddUser(Socket client)throws IOException{
        String username = "User" + String.valueOf(users_count);
        User user = new User(client, username);
        this.online_users.add(user);
        users_count += 1;
        new Thread( new ServerMessage(this, user)).start(); // Un thread de escucha para este usuario
        user.SendMessage( "Conectado al servidor como " + user.GetName() );
        return user;
    }

    public void RemoveUser(User user){
        this.online_users.remove(user);
        return;
    }
}

// Hilo para la escucha de cada cliente
class ServerMessage implements Runnable {
    public Server server;
    public User user;

    public ServerMessage(Server server, User user){
        this.server = server;
        this.user = user;
    }

    public void run(){
        Scanner scan = new Scanner( this.user.GetInputStream() ); 
        // Escuchar nuevos mensajes del usuario
        while( scan.hasNextLine() ){
            String user_message = scan.nextLine();
            if(user_message.length() > 0){
                user_message = hello.Codec.Decode(user_message); // Decoficiar mensaje
                System.out.println(  this.user.GetName() + ": " +  user_message); 
            }
        }
        System.out.println(  "Usuario " + this.user.GetName() + "se ha desconectado."); 
        // [LOG Registrar en el log usuario se desconecto]
        server.RemoveUser(user);
        scan.close();
        return;
    }
}


// Representa a un cliente en el servidor
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