package es.iesjandula.reaktor.network_server.interfaces;

import java.util.List;
import java.util.Map;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.models.Red;

/**
 * @author David Martinez
 *
 */
public interface IUtils
{

	/**
	 * Method getNetworkAddress
	 * 
	 * @param  ipAddress
	 * @param  subnetMask
	 * @return
	 * @throws NetworkException
	 */
	public String getNetworkAddress(String ipAddress, String subnetMask) throws NetworkException;

	/**
	 * Method scanEquipo
	 * 
	 * @param  equipo
	 * @throws NetworkException
	 */
	public void scanEquipo(Equipo equipo) throws NetworkException;

	/**
	 * Method executeCommand
	 * 
	 * @param  command
	 * @return
	 * @throws NetworkException
	 */
	public String executeCommand(String command) throws NetworkException;
	
	
	/**
	 * Method executeCommandNonWait
	 * @param command
	 * @return
	 * @throws NetworkException
	 */
	public String executeCommandNonWait(String command) throws NetworkException;

	/**
	 * Method insertRedes
	 * 
	 * @param  map
	 * @throws NetworkException
	 */
	public void insertRedes(Map<String, List<String>> map) throws NetworkException;

	/**
	 * Method saveNetworks
	 * 
	 * @throws NetworkException
	 */
	public void saveNetworks() throws NetworkException;

	/**
	 * Method scanEquipos
	 * 
	 * @param  red
	 * @throws NetworkException
	 */
	public void scanEquipos(Red red) throws NetworkException;

	/**
	 * Method executeNmapSN
	 * 
	 * @param  red
	 * @throws NetworkException
	 */
	public void executeNmapSN(Red red) throws NetworkException;
}
