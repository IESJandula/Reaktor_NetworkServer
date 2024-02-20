package es.iesjandula.reaktor.network_server.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.jandula.tarea.Equipo;

public class Parser
{
	/**
	 * Pendiente implementacion del codigo
	 * 
	 * @author Pablo Ruiz Canovas
	 */
	public void parseIpConfig()
	{

	}

	/**
	 * Parsea el string obtenido del mapeo del Nmap en una lista de Equipos con ip y mac
	 * @param String (busqueda del Nmap)
	 * @return List<Equipos>
	 */
	public List<Equipo> parseoNmapSN(String content)
	{
		// Lista de equipos a delvolver
		List<Equipo> equipos = new ArrayList<Equipo>();
		
		// Inicializamos el scanner
		Scanner scanner = new Scanner(content);
		
		// altamos la primera linea que no es util
		scanner.nextLine();
		
		// Inicializamos los atributos del Equipo
		String ip = "";
		String mac = "";
		
		// Hacemos un bucle mientras haya lineas en el string
		while (scanner.hasNextLine())
		{
			// Guardamos la linea
			String linea = scanner.nextLine();
			String[] info = null;
			
			// Comprobamos si linea que contiene la ip
			if (linea.contains("Nmap scan report"))
			{
				info = linea.split(" ");
				
				if (info.length > 5)
				{
					ip = info[5].replace("(", "").replace(")", "");
				}
				else if (info.length > 4)
				{
					ip = info[4];
				}
			}
			
			// Comprobamos si linea que contiene el MAC address
			else if (linea.contains("MAC Address:"))
			{
				info = linea.split(" ");
				mac = info[2];
				
				// Seteamos los atributos en un nuevo objeto equipo que añadimos a la lista
				Equipo equipo = new Equipo();
				equipo.setIp(ip);
				equipo.setMac(mac);
				
				equipos.add(equipo);
			}			
		}
		return equipos;
	}

}
