package es.iesjandula.reaktor.network_server;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.interfaze_parser.IParserEquipo;
import es.iesjandula.reaktor.network_server.models.Red;
import es.iesjandula.reaktor.network_server.parser.ParserBdd;


@SpringBootApplication
@EntityScan(basePackages = "es.iesjandula.reaktor.network_server")
@ComponentScan( basePackages = "es.iesjandula.reaktor.network_server")
public class NetworkServerApplication implements CommandLineRunner
{
	@Autowired
	private IParserEquipo iParserEquipo;
	/**
	 * Method main
	 * @param args
	 */
	public static void main(String[] args)
	{
		SpringApplication.run(NetworkServerApplication.class, args);
	}

	
	@Override
	@Transactional
	public void run(String... args) throws NetworkException
	{
		iParserEquipo = new ParserBdd();
		Red red= new Red();
		iParserEquipo.executeNmapSN(red);
	}
	
}
