package es.iesjandula.reaktor.network_server.interfaze_parser;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.models.Red;

/**
 * This interface represent unimplemented method parser for Equipo
 * @author alvar
 */
public interface IParserEquipo {
	public void executeNmapSN(Red red)throws NetworkException;
	
}
