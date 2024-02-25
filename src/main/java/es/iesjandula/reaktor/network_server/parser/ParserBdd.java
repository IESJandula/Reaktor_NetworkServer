package es.iesjandula.reaktor.network_server.parser;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.interfaze_parser.IParserEquipo;
import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.models.Red;
import es.iesjandula.reaktor.network_server.repository.IEquipoRepository;
import es.iesjandula.reaktor.network_server.repository.IRedRepository;
import es.iesjandula.reaktor.network_server.utils.Utils;
@Service
public class ParserBdd implements IParserEquipo 
{
	@Autowired
	private IEquipoRepository iEquipoRepository;
	@Autowired
	private IRedRepository iRedRepository;
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	@Override
	public void  executeNmapSN(Red red) throws NetworkException 
	{
		Parser parser= new Parser();
		Utils utils = new Utils();
		red.setNombre("MiFibra-D2CC");
		
		red.setRutaRed("192.168.1.0/24");
		String command="nmap -sn "+"192.168.1.0/24";
		try 
		{
			String content=utils.executeCommand(command);
			List<Equipo> equipos=parser.parseoNmapSN(content);
			red.setEquipos(equipos);
			iRedRepository.save(red);
			
			for(Equipo equipo : equipos)
			{
				equipo.setRed(red);
			}
			iEquipoRepository.saveAllAndFlush(equipos);
		}
		catch (NetworkException exception) {
			String error ="Error al ejecutar nmap";
			log.error(error,exception);
			throw new NetworkException(2, error);
		}
		

	}

}
