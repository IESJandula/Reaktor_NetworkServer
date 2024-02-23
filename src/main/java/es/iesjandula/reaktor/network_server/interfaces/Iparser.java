package es.iesjandula.reaktor.network_server.interfaces;

import java.util.List;
import java.util.Map;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.models.Equipo;

public interface Iparser 
{

	public Map<String, List<String>> parseIpConfig(String content) throws NetworkException;
	public List<Equipo> parseoNmapSN(String content);
	public void parseNetView(Equipo equipo, String content);
	
}
