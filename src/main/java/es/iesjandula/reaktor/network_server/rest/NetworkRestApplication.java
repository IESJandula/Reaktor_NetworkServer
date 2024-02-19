package es.iesjandula.reaktor.network_server.rest;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.parser.Parser;

@RestController
@RequestMapping( value = "/net", produces="application/json")
/**
 * Sutil cambialo a tu manera luego esto es solo la plantilla para la tarea
 */
public class NetworkRestApplication 
{
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	
	/**
	 * Endpoint que ejecuta el comando ipconfig y devuelve su resultado en un mapa de <String,List<String>>
	 * @return mapa con la informacion del comando ipconfig
	 */
	@RequestMapping( method = RequestMethod.GET ,value = "/parse-command")
	public ResponseEntity <?>  parseIpConfig()
	{
		try
		{
			Parser parser = new Parser();
			Map<String,List<String>> parseoComando = parser.parseIpConfig();
			return ResponseEntity.ok().body(parseoComando);
		}
		catch(NetworkException exception)
		{
			log.error("Error obteniendo la informacion del ordenador",exception);
			return  ResponseEntity.status(500).body(exception.getBodyException());
		}
		
	}
}
