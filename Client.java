import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{

    private String ip;
    private int port;

    public static void main(String[] args) throws UnknownHostException, IOException{
        new Client("127.0.0.1", 12345).run();
        return;
    }

    private Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    private void run() throws UnknownHostException, IOException{
        Socket client = new Socket(ip, port);
        System.out.println("Connected to server.");

        PrintStream output = new PrintStream(client.getOutputStream());

        Scanner scan2 = new Scanner(System.in);
        System.out.print("Enter a nickname: ");
        String username = scan2.nextLine();
        output.println(username);

        new Thread(new ClientMessage(client.getInputStream())).start();

        while(scan2.hasNextLine()){
            output.println(scan2.nextLine());
        }

        output.close();
        scan2.close();
        client.close();
        return;
    }
}

class ClientMessage implements Runnable{
    private InputStream server;

    public ClientMessage(InputStream server){
        this.server = server;
    }

    public void run(){
        Scanner scan = new Scanner(server);
        String message;
        while(scan.hasNextLine()){
            message = scan.nextLine();
            System.out.println(message);
        }
        scan.close();
        return;
    }
}