package mensajeria;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class Server {
    private ServerSocket server;
    private int port;
    private List<User> online_users;
    private int users_count = 0;
    public final static Logger logger = Logger.getLogger("server");

    public Server(int port){
        this.port = port;
        this.online_users = new ArrayList<User>();
    }

    public void Run(){
        try{
            server = new ServerSocket(port);
        }catch(IOException e){
            logger.fatal( e.getMessage() );
            return;
        }
        System.out.println("Servidor corriendo en el puerto " + Integer.toString(port));
        logger.info( "Servidor corriendo en el puerto " + Integer.toString(port) );
        // Escuchando nuevas conexiones
        while(true){
            Socket client = null;
            try{
                client = server.accept();
            }catch(Exception e){
                logger.fatal( e.getMessage() );
                return;
            }

            User user = null;
            try{
                user = AddUser(client);
            }catch(IOException e){
                logger.fatal( "Fallo al agregar usuario, el socket esta ocupado o desconectado");
                logger.fatal( e.getMessage() );
                return;
            }

            user.SendMessage( "Conectado al servidor como " + user.GetName() ); // Informar al cliente
            System.out.println(user.GetName() + " se ha conectado.");
            logger.info( user.GetName() + " se ha conectado." ); 
        }
    }

    public User AddUser(Socket client)throws IOException{
        String username = "User" + String.valueOf(users_count);
        User user = new User(client, username);
        this.online_users.add(user);
        users_count += 1;
        new Thread( new ServerMessage(this, user, logger)).start(); // Un thread de escucha para este usuario
        return user;
    }

    public void RemoveUser(User user){
        System.out.println(user.GetName() + " se ha desconectado.");
        logger.info( user.GetName() + " se ha desconectado." );
        this.online_users.remove(user);
        return;
    }
}

// Hilo para la escucha de cada cliente
class ServerMessage implements Runnable {
    public Server server;
    public User user;
    Logger logger;

    public ServerMessage(Server server, User user, Logger logger){
        this.server = server;
        this.user = user;
        this.logger = logger;
    }

    public void run(){
        Scanner scan = new Scanner( this.user.GetInputStream() ); 
        while( scan.hasNextLine() ){ // Escuchar nuevos mensajes del usuario
            String user_message = scan.nextLine();
            if(user_message.length() > 0){
                user_message = mensajeria.Codec.Decode(user_message); // Decoficiar mensaje
                logger.info(this.user.GetName() + " decodificado: " +  user_message);
                System.out.println(  this.user.GetName() + ": " +  user_message); 
            }
        }
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
        this.printstream = new PrintStream( client.getOutputStream() );
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