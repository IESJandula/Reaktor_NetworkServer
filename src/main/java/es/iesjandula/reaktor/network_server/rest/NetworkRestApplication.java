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
			String pruebaComando = "Adaptador de Ethernet Ethernet 3:\n"
					+ "\n"
					+ "   Sufijo DNS específico para la conexión. . :\n"
					+ "   Vínculo: dirección IPv6 local. . . : fe80::d676:177d:b92e:a0a1%10\n"
					+ "   Dirección IPv4. . . . . . . . . . . . . . : 192.168.56.1\n"
					+ "   Máscara de subred . . . . . . . . . . . . : 255.255.255.0\n"
					+ "   Puerta de enlace predeterminada . . . . . :\n"
					+ "\n"
					+ "Adaptador de LAN inalámbrica Conexión de área local* 9:\n"
					+ "\n"
					+ "   Estado de los medios. . . . . . . . . . . : medios desconectados\n"
					+ "   Sufijo DNS específico para la conexión. . :\n"
					+ "\n"
					+ "Adaptador de LAN inalámbrica Conexión de área local* 10:\r\n"
					+ "\n"
					+ "   Estado de los medios. . . . . . . . . . . : medios desconectados\n"
					+ "   Sufijo DNS específico para la conexión. . :\n"
					+ "\n"
					+ "Adaptador de LAN inalámbrica Wi-Fi 2:\n"
					+ "\n"
					+ "   Estado de los medios. . . . . . . . . . . : medios desconectados\n"
					+ "   Sufijo DNS específico para la conexión. . :\n"
					+ "\n"
					+ "Adaptador de Ethernet Ethernet 2:\n"
					+ "\n"
					+ "   Sufijo DNS específico para la conexión. . : 23005694.23.andared.ced.junta-andalucia.es\n"
					+ "   Vínculo: dirección IPv6 local. . . : fe80::ccc9:e155:7334:cd76%17\n"
					+ "   Dirección IPv4. . . . . . . . . . . . . . : 192.168.10.151\n"
					+ "   Máscara de subred . . . . . . . . . . . . : 255.255.248.0\n"
					+ "   Puerta de enlace predeterminada . . . . . : 192.168.8.1";
			
			Map<String,List<String>> parseoComando = parser.parseIpConfig(pruebaComando);
			return ResponseEntity.ok().body(parseoComando);
		}
		catch(NetworkException exception)
		{
			log.error("Error obteniendo la informacion del ordenador",exception);
			return  ResponseEntity.status(500).body(exception.getBodyException());
		}
		
	}
}
