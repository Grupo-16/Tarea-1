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
                Server new_server = new Server(12345); // Que pasa si se ejecutan mas servers?
                new_server.Run();
            }
            else if(args[0].equals("client")){
                Client new_client= new Client("127.0.0.1", 12345); // Que pasa si se ejecutan mas servers?
                new_client.Run();
            }


        }


        /* 
        LocalTime currentTime = new LocalTime();
		System.out.println("The current local time is: " + currentTime);

        HelloWorld obj = new HelloWorld();
		obj.log();
        */
    }
	
	private void log(){
		
		if(logger.isDebugEnabled()){
			logger.debug("This is debug log..");
		}
		
		if(logger.isInfoEnabled()){
			logger.info("This is info  log ...");
		}
		
		logger.warn("This is warn log ...");
		logger.error("This is error log... ");
		logger.fatal("This is fatal log ...");
		
	}

}
