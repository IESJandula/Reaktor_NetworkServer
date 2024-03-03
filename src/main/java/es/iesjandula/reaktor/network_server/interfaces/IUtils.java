package es.iesjandula.reaktor.network_server.interfaces;

import java.util.List;
import java.util.Map;

import es.iesjandula.reaktor.network_server.exception.NetworkException;

public interface IUtils
{

	public String getNetworkAddress(String ipAddress, String subnetMask) throws NetworkException;
	public String executeCommand(String command) throws NetworkException;
	public void insertRedes(Map<String, List<String>> map) throws NetworkException;
	public void saveNetworks() throws NetworkException;
}
