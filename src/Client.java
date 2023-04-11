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

    public static void main(String[] args){
        new Client("127.0.0.1", 12345).Run();
        // [Controlar excepciones aqui]
        return;
    }

    private Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    private void Run(){
        // Conectarse al servidor
        //Socket client = createClient(ip, port);
        Socket client;
        try{
            client = new Socket(ip, port);
            System.out.println("Conectado al servidor.");
            // [Log Conectado al servidor]
        } catch(Exception e){
            System.out.println(e);
            System.out.println("No pudo conectarse al servidor.");
            //log fail
            return;
        }

        PrintStream output;
        try{
            output = new PrintStream( client.getOutputStream() ); // Para enviar mensajes
        } catch(Exception e){
            System.out.println(e);
            System.out.println("");
            //log fail
            closeClient(client);
            return;
        }

        // Obtener nombre de usuario
        Scanner scan2 = new Scanner(System.in);
        System.out.print("Ingrese su nombre: ");
        String username = scan2.nextLine();
        output.println(username);
        // [Log usuario "registrado"]

        try{
            // Aqui llegan los mensajes
            new Thread( new ServerMessageReceiver( client.getInputStream() ) ).start(); 
        } catch(Exception e){
            System.out.println(e);
            System.out.println("No pudo crearse Thread.");
            //log fail
            scan2.close();
            closeClient(client);
            return;
        }

        // Esperando input del usuario
        while( scan2.hasNextLine() ){
            String new_message = scan2.nextLine();
            output.println(new_message);
        }
        
        output.close();
        scan2.close();
        closeClient(client);
        return;
    }

    private void closeClient(Socket client){
        try{
            client.close();
        } catch(Exception e){
            System.out.println(e);
            System.out.println("Error al cerrar Client.");
            //log fail
            return;
        }
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