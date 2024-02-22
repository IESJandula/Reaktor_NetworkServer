package es.iesjandula.reaktor.network_server.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.parser.Parser;

public class Utils
{

	private static Logger log = LogManager.getLogger();
	
	
	
	public Utils()
	{
		super();
		// TODO Auto-generated constructor stub
	}



	public String getNetworkAddress(String ipAddress, String subnetMask) throws NetworkException
	{
		// Convert the IP and subnet mask strings to InetAddress objects
		
		
		InetAddress ip;
		try
		{
			ip = InetAddress.getByName(ipAddress);
			InetAddress mask = InetAddress.getByName(subnetMask);

			// Get the bytes of the IP and subnet mask addresses
			byte[] ipBytes = ip.getAddress();
			byte[] maskBytes = mask.getAddress();

			// Perform the logical AND operation between the IP and subnet mask bytes
			byte[] networkBytes = new byte[ipBytes.length];
			for (int i = 0; i < ipBytes.length; i++)
			{
				networkBytes[i] = (byte) (ipBytes[i] & maskBytes[i]);
			}

			// Create an InetAddress object with the resulting bytes
			InetAddress networkAddress = InetAddress.getByAddress(networkBytes);

			// Calculate the subnet mask prefix length
			int prefixLength = 0;
			for (byte b : maskBytes)
			{
				for (int i = 7; i >= 0; i--)
				{
					if ((b & (1 << i)) != 0)
					{
						prefixLength++;
					} else
					{
						break;
					}
				}
			}
			// Return the network address and subnet mask prefix in string format
			return networkAddress.getHostAddress() + "/" + Integer.toString(prefixLength);
		} catch (UnknownHostException e)
		{
			log.error("The IP address entered is not correct / Error obtaining the Network address");
			throw new NetworkException(1, "Error al obtener la ruta de red");
		}
	}
	public void scanEquipo(Equipo equipo)
	{
		
		Parser parser = new Parser();
		try
		{
			String ip = equipo.getIp();
			String comandoNmap = "nmap -Pn -O" + ip;
			
			String respuestaComandoNmap = tarea2(comando);
			
			parser.parseNmapPNO(equipo, respuestaComando);
			obtainType(equipo);
			if(equipo.getTipo().equals("impresora"))
			{
				log.info("El equipo es una impresora");
			}
			else
			{
				String comandoNetView = "net view" + ip;
				String respuestaComandoNetView = tarea2(comandoNetView);
				tarea11(equipo,respuestaComandoNetView);
			}
		}catch(NetworkException exception)
		{
			log.error("Error al escanear el equipo");
			throw new NetworkException(1, exception.getMessage());
		}
		
		
		
	}
	
	
}
