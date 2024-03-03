package es.iesjandula.reaktor.network_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan( basePackages = "es.iesjandula.reaktor.network_server")
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

	
	

}
