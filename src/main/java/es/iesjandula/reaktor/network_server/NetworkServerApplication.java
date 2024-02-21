package es.iesjandula.reaktor.network_server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan( basePackages = "es.iesjandula.reaktor.network_server")
public class NetworkServerApplication implements CommandLineRunner
{
	/**
	 * Method main
	 * @param args
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(NetworkServerApplication.class, args);
	}

	
	@Override
	public void run(String... args)
	{
		
	}
	
}
