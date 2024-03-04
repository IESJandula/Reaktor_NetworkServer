package es.iesjandula.reaktor.network_server.interfaces;

import java.util.List;
import java.util.Map;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.models.Equipo;

public interface IParser 
{

	/**
	 * Method parseIpConfig
	 * @param content
	 * @return
	 * @throws NetworkException
	 */
	public Map<String, List<String>> parseIpConfig(String content) throws NetworkException;
	
	/**
	 * Method parseoNmapSN
	 * @param content
	 * @return
	 */
	public List<Equipo> parseoNmapSN(String content);
	
	/**
	 * Method parseNetView
	 * @param equipo
	 * @param content
	 */
	public void parseNetView(Equipo equipo, String content);
	
	/**
	 * Method parseNmapPNO
	 * @param equipo
	 * @param content
	 */
	public void parseNmapPNO(Equipo equipo, String content);
	
	/**
	 * Method parseWlanNames
	 * @return Map<String,String> map with MAC , and connection name
	 */
	public Map<String,String> parseWlanNames(String content);
	
}
