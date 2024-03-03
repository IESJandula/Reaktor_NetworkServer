package es.iesjandula.reaktor.network_server.interfaces;

import java.util.List;
import java.util.Map;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.models.Red;

public interface IUtils
{

	public String getNetworkAddress(String ipAddress, String subnetMask) throws NetworkException;
	public void scanEquipo(Equipo equipo) throws NetworkException;
	public String executeCommand(String command) throws NetworkException;
	public void insertRedes(Map<String, List<String>> map) throws NetworkException;
	public void saveNetworks() throws NetworkException;
	public void scanEquipos(Red red) throws NetworkException;
	public void  executeNmapSN(Red red) throws NetworkException;
}
