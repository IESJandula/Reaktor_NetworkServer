package es.iesjandula.reaktor.network_server.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.interfaces.IUtils;
import es.iesjandula.reaktor.network_server.interfaces.Iparser;
import es.iesjandula.reaktor.network_server.models.Red;
import es.iesjandula.reaktor.network_server.repository.IRedRepository;
import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.parser.Parser;
import es.iesjandula.reaktor.network_server.repository.IEquipoRepository;


@Service
public class Utils implements IUtils
{

	@Autowired
    private Iparser iparse;
	
	@Autowired
    private IRedRepository redRepository;

	@Autowired
	private IEquipoRepository equipoRepository;
	
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
			
			String respuestaComandoNmap = executeCommand(comandoNmap);
			
			parser.parseNmapPNO(equipo, respuestaComando);
			obtainType(equipo);
			if(!equipo.getTipo().equals(Equipo.TIPO_IMPRESORA))
			{
				String comandoNetView = "net view" + ip;
				String respuestaComandoNetView = executeCommand(comandoNetView);
				parser.parseNetView(equipo,respuestaComandoNetView);
			}
			else
			{
				log.info("El equipo es una impresora");
			}
		}catch(NetworkException exception)
		{
			log.error("Error al escanear el equipo");
			throw new NetworkException(1, exception.getMessage());
		}
		
		
		
	}

	public String executeCommand(String command) throws NetworkException
	{
		// Get the runtime
		Runtime rt = Runtime.getRuntime();
		Process execute;
		String resultado = "";

		try
		{
			// Execute the command
			execute = rt.exec("cmd.exe /c " + command);

			// Use try-with-resources to automatically close the Scanner
			try (Scanner scanner = new Scanner(execute.getInputStream()))
			{
				// Wait for the process to finish
				int n = execute.waitFor();

				// Read the output of the command
				while (scanner.hasNextLine())
				{
					resultado += scanner.nextLine() + "\n";
				}

				// Check if the command execution was successful
				if (n != 0)
				{
					log.error("Command execution failed with exit code: " + n);
					throw new NetworkException(4, "Command execution failed with exit code: " + n);
				}
			}

		} catch (IOException exception)
		{
			// Log and throw an exception for IO errors
			log.error("Error getting result", exception);
			throw new NetworkException(2, "Error al obtener el resultado", exception);
		} catch (InterruptedException e)
		{
			// Log and throw an exception for interruption errors
			log.error("Interrupt error in command", e);
			throw new NetworkException(3, "Error de interrupcion en el comando", e);
		}
		// Return the result of the command execution
		return resultado;
	}
	
	/**
	 * Method insertRedes , Methos to insert Redes
	 * @throws NetworkException 
	 */
	public void insertRedes(Map<String, List<String>> map) throws NetworkException 
	{
		
		// Iteration on the map
        for (Map.Entry<String, List<String>> entry : map.entrySet()) 
        {
            String nameRed = entry.getKey();
            List<String> subnetMask  = entry.getValue();

            try 
            {
            	// Get the network path using the getNetworkAddress method
                String rutaRed = getNetworkAddress(subnetMask.get(0), subnetMask.get(1));

                // Create a Network Object
                Red red = new Red();
                red.setNombre(nameRed);
                red.setRutaRed(rutaRed);
                
                // Save the network to the database using the network repository
                redRepository.saveAndFlush(red);
                
                System.out.println("Red insertada en la base de datos: " + red);
            } 
            catch (NetworkException networkException) 
            {
                networkException.printStackTrace();
                // Log and throw an exception for interruption errors
                log.error("Error inserting the network into the database", networkException);
                throw networkException;
            }
        }
    }
	
	/**
	 * Method saveNetworks , Method for save all networks
	 * @throws NetworkException 
	 */
	public void saveNetworks() throws NetworkException 
	{
		try
		{
			// Try to call executeComand to get the ipconfig string, and parse it with parseIpConfig, on the last, try to insert with insertRedes
			this.insertRedes(iparse.parseIpConfig(this.executeCommand("ipconfig")));
			
		}
		catch (NetworkException exception)
		{
			// Log and throw an exception for interruption errors
			log.error("Error on save network", exception);
			throw exception;
     }
	}

  /**
	 * MÉTODO QUE PASANDOLE UN EQUIPO, IDENTIFIQUE EL TIPO DE EQUIPO
	 * TIPOS POSIBLES:(PC O IMPRESORA)
	 * @param equipo
	 */
	public void obtainType(Equipo equipo)
	{
		int i = 0;
		while(i < equipo.getPuertos().size() && !equipo.getTipo().isEmpty())
		{
			//Si el número del puerto es el 9100, el 515 o el 631
			if(equipo.getPuertos().get(i).getPuertoId().getNumero() == 9100 || equipo.getPuertos().get(i).getPuertoId().getNumero() == 515 || equipo.getPuertos().get(i).getPuertoId().getNumero() == 631)
			{
				//Será una impresora
				equipo.setTipo(Equipo.TIPO_IMPRESORA);
			}
			i++;
		}
		
		//Si no ha encontrado nada
		if(equipo.getTipo().isEmpty())
		{
			//Será un pc
			equipo.setTipo(Equipo.TIPO_STANDARD);
		}
	}
  
  public void scanEquipos(Red red) throws NetworkException
	{
		List<Equipo> equipos = this.equipoRepository.findByRed(red);
		for (Equipo equipo : equipos) {
//			this.scanEquipo(equipo);
		}
	}
  
}
