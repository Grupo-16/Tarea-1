package mensajeria;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.print.attribute.standard.OutputDeviceAssigned;
import org.apache.log4j.Logger;


public class Client{
    private String ip;
    private int port;
    final static Logger logger = Logger.getLogger("client");

    public Client(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void Run(){
        // Conectarse al servidor
        Socket client = null;
        try{
            client = new Socket(ip, port);
        }catch(Exception e){
            logger.fatal( e.getMessage() );
            return;
        }
        System.out.println("Conectado al servidor.");
        logger.info("Conectado al servidor.");
        try{
            new Thread( new ServerMessageReceiver( client.getInputStream(), logger ) ).start(); // Aqui llegan los mensajes
        }catch(Exception e){
            logger.fatal( e.getMessage() );
            try {
                client.close();
            } catch (IOException e1) { e1.printStackTrace(); }
            return;
        }
        PrintStream output = null;
        try{
            output = new PrintStream( client.getOutputStream() );
        }
        catch(Exception e){
            logger.fatal( e.getMessage() );
            try {
                client.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
        }

        Scanner scan2 = new Scanner(System.in);
        while( scan2.hasNextLine() ){ // Esperando input del usuario
            String new_message = scan2.nextLine();
            new_message = mensajeria.Codec.Code(new_message); // Codificar mensaje
            logger.info( "mensaje codificado: " + new_message);
            output.println(new_message); // Enviar mensaje
        }
        output.close();
        scan2.close();
        try {
            client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return;
    }
}

// Clase para recibir mensajes del servidor
class ServerMessageReceiver implements Runnable{
    private InputStream server;
    Logger logger;

    public ServerMessageReceiver(InputStream server, Logger logger){
        this.server = server;
        this.logger = logger;
    }

    public void run(){
        Scanner scan = new Scanner(server);
        String message;
        while(scan.hasNextLine()){
            message = scan.nextLine();
            System.out.println(message);
            logger.info("Nuevo mensaje: " + message );
        }
        scan.close();
        return;
    }

}