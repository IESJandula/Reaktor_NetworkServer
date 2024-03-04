package es.iesjandula.reaktor.network_server.scheduled_task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.interfaces.IUtils;
import es.iesjandula.reaktor.network_server.models.Red;
import es.iesjandula.reaktor.network_server.repository.IRedRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author David Martinez
 *
 */
@Component
@Slf4j
public class ScanNetworkTask
{
	/** Attribute util */
	@Autowired
	private IUtils util;

	/** Attribute redRepository */
	@Autowired
	private IRedRepository redRepository;

	/**
	 * Method scanNetworkTask , scheluded task every 24 hours
	 *
	 * @throws NetworkException
	 */
	@Scheduled(fixedDelayString = "86400000", initialDelay = 2000)
	public void scanNetworkTask() throws NetworkException
	{
		try
		{
			this.util.saveNetworks();

			// GETTING ALL
			List<Red> redes = this.redRepository.findAll();
			
			// FOR EACH
			for (Red red : redes)
			{
				// EXECUTE NMAP SN
				this.util.executeNmapSN(red);
				
				// SCAN
				this.util.scanEquipos(red);
			}
			
			// LOG THE END
			log.info("Fin scan redes");

		}
		catch (NetworkException exception)
		{
			// IF ANY ERROR
			String error = "Error on scanNetworkTask scheluded";
			log.error(error, exception);
			throw exception;
		}
	}
}
