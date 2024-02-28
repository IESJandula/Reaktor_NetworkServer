package es.iesjandula.reaktor.network_server.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "/net", produces="application/json")
/**
 * Sutil cambialo a tu manera luego esto es solo la plantilla para la tarea
 */
public class NetworkRestApplication 
{
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	
}
