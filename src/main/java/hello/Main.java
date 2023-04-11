package hello;

import org.joda.time.LocalTime;
import java.io.IOException;
import org.apache.log4j.Logger;

public class Main {
    final static Logger logger = Logger.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        if(args.length > 0){
            System.out.println("Los args sonn: " + args[0]);
            if(args[0].equals("server")){
                Server new_server = new Server(12345);

                try{
                    new_server.Run();
                }catch(IOException e){
                    
                }
            }
            else if(args[0].equals("client")){
                Client new_client= new Client("127.0.0.1", 12345);
                new_client.Run();
            }
        }
    }

}
