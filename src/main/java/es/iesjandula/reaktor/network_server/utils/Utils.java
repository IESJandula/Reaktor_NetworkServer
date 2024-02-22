package es.iesjandula.reaktor.network_server.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.iesjandula.reaktor.network_server.exception.NetworkException;

public class Utils
{

	private static Logger log = LogManager.getLogger();

	public static String getNetworkAddress(String ipAddress, String subnetMask) throws NetworkException
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
}
