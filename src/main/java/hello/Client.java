package hello;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.print.attribute.standard.OutputDeviceAssigned;



public class Client{

    private String ip;
    private int port;

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void Run() throws UnknownHostException, IOException{
        // Conectarse al servidor
        Socket client = new Socket(ip, port);
        System.out.println("Conectado al servidor.");
        // [Log Conectado al servidor]

        PrintStream output = new PrintStream( client.getOutputStream() ); // Para enviar mensajes

        // Obtener nombre de usuario
        Scanner scan2 = new Scanner(System.in);
        System.out.print("Ingrese su nombre: ");
        String username = scan2.nextLine();
        output.println(username);
        // [Log usuario "registrado"]

        new Thread( new ServerMessageReceiver( client.getInputStream() ) ).start(); // Aqui llegan los mensajes

        // Esperando input del usuario
        while( scan2.hasNextLine() ){
            String new_message = scan2.nextLine();
            new_message = hello.Codec.Code(new_message); // Codificar mensaje
            output.println(new_message); // Enviar mensaje
        }

        output.close();
        scan2.close();
        client.close();
        return;
    }
}

// Clase para recibir mensajes del servidor
class ServerMessageReceiver implements Runnable{
    private InputStream server;

    public ServerMessageReceiver(InputStream server){
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