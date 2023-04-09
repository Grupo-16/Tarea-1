import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server {
    private int port;
    private List<User> online;
    private ServerSocket server;

    public static void main(String[] args) throws IOException{
        new Server(12345).run();
        return;
    }

    public Server(int port){
        this.port = port;
        this.online = new ArrayList<User>();
    }

    public void run() throws IOException{
        server = new ServerSocket(port);
        System.out.println(Integer.toString(port));

        while(true){
            Socket client = server.accept();

            Scanner scan2 = new Scanner(client.getInputStream());
            String username = scan2.nextLine();

            User user = new User(client, username);
            this.online.add(user);
            System.out.println(username);

            new Thread(new ServerMessage(this, user)).start();
        }
    }

    public void remove(User user){
        this.online.remove(user);
        return;
    }

    public void sysSend(String message){
        for(User user : this.online){
            user.getOutputStream().println(message);
        }
        return;
    }

    public void send(String message, User sender){
        for(User user : this.online){
            if (user != sender){
                user.getOutputStream().println(sender.getName() + ": " + message);
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
        server.sysSend(user.getName() + " is online.");

        String message;
        Scanner scan3 = new Scanner(this.user.getInputStream());
        while(scan3.hasNextLine()){
            message = scan3.nextLine();
            if(message.length() > 0){
                server.send(message, user);
            }
        }

        server.sysSend(user.getName() + " is offline.");
        server.remove(user);
        scan3.close();
        return;
    }
}

class User{
    private InputStream inputstream;
    private PrintStream printstream;
    private String username;

    public User(Socket client, String username) throws IOException {
        this.inputstream = client.getInputStream();
        this.printstream = new PrintStream(client.getOutputStream());
        this.username = username;
    }

    public InputStream getInputStream(){
        return this.inputstream;
    }

    public PrintStream getOutputStream(){
        return this.printstream;
    }

    public String getName(){
        return this.username;
    }
}