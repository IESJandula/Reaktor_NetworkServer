package es.iesjandula.reaktor.network_server.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.interfaze_parser.IParserEquipo;
import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.models.Red;
import es.iesjandula.reaktor.network_server.repository.IEquipoRepository;
import es.iesjandula.reaktor.network_server.utils.Utils;

public class Parser 
{
	
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	/**
	 * Metodo que recibe el contenido del comando ipconfig y lo parsea a un mapa de <String,List<String>>
	 * @param content contenido del comando
	 * @return mapa con la informacion de cada adaptador
	 * @throws NetworkException
	 * @author Pablo Ruiz Canovas
	 */
	public Map<String,List<String>> parseIpConfig(String content) throws NetworkException
	{
		//Declaracion del mapa
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		//Cargado de datos del comando
		//Separamos el contenido por \n
		String [] splitContenido = content.split("\n");
		//Creacion de la lista de datos de cada adaptador 
		List<String> datosConf = new LinkedList<String>();
		//String identificador para identificar el adaptador
		String keySentencia = "";
		for(String sentencia:splitContenido)
		{
			/*
			 * Cada seccion del adaptador acaba siempre en : por ejemplo
			 * Adaptador de Ethernet Ethernet:
			 * puede haber casos en los que se produzca esto
			 * Sufijo DNS específico para la conexión. . :
			 * entonces se controla que no contenga el caracter "."
			 * y se guarda como clave en el mapa
			 * en caso contrario se controla que la key no sea nula para evitar las primeras lineas
			 * y se rellena la lista con la informacion del IPv4 y de la mascara de subred
			*/
			if(sentencia.trim().endsWith(":") && !sentencia.trim().contains("."))
			{
				keySentencia = sentencia;
				datosConf = new LinkedList<String>();
				map.put(keySentencia.trim(), datosConf);
			}
			else if(!keySentencia.isEmpty() && (sentencia.contains("IPv4") || this.containsSubred(sentencia)))
			{
				String [] splitInfo = sentencia.split(":");
				datosConf.add(splitInfo[1].trim());
				map.put(keySentencia, datosConf);
			}
			
		}
		//Por ultimo eliminamos las claves que estan vacias para quedarnos solo
		//con las claves que contengan IPv4 y mascara de subred
		Map<String,List<String>> map2 = new HashMap<String,List<String>>();
		for(Map.Entry<String, List<String>> entry:map.entrySet())
		{
			if(!entry.getValue().isEmpty())
			{
				map2.put(entry.getKey(), entry.getValue());
			}
		}
		log.info("Parseo de informacion del comando ipconfig finalizado");
		return map2;
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
		scanner.close();
		return equipos;
		
	}

	/**
	 * Metodo que comprueba el contenido de la sentencia del comando de ipconfig que
	 * contenga la palavra 'Máscara de subred' en distintos casos
	 * @param sentencia
	 * @return false o true depende de si lo ha encontrado o no
	 */
	private boolean containsSubred(String sentencia)
	{
		boolean found = false;
		if(sentencia.contains("Máscara de subred"))
		{
			found = true;
		}
		else if(sentencia.contains("de subred"))
		{
			found = true;
		}
		else if(sentencia.contains("subred"))
		{
			found = true;
		}
		return found;
	}

	


}
