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
	
	
}
