package es.iesjandula.reaktor.network_server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import es.iesjandula.reaktor.network_server.interfaces.Iparser;
import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.models.Red;
import es.iesjandula.reaktor.network_server.parser.Parser;
import es.iesjandula.reaktor.network_server.repository.IEquipoRepository;
import es.iesjandula.reaktor.network_server.repository.IRedRepository;

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

	
	@Override
	public void run(String... args)
	{
		
	}
	
}
