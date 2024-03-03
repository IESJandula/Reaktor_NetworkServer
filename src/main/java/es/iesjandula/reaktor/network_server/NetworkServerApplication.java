package es.iesjandula.reaktor.network_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication
@EntityScan(basePackages = "es.iesjandula.reaktor.network_server")
public class NetworkServerApplication
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
