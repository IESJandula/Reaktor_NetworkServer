package es.iesjandula.reaktor.network_server.scheduled_task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.utils.Utils;
import lombok.extern.slf4j.Slf4j;


/**
 * @author David Martinez
 *
 */
@Component
@Slf4j
public class ScanNetworkTask
{
    /**
     * Method scanNetworkTask , scheluded task every 24 hours
     * @throws NetworkException 
     */
    @Scheduled(fixedDelayString = "86400000", initialDelay = 2000)
    public void scanNetworkTask() throws NetworkException
    {
    	try
		{
    		Utils util = new Utils();
    		util.saveNetworks();
    		//util.methodTarea8();
    		//util.methodTarea13();
		}
		catch (NetworkException exception)
		{
			// Log and throw an exception for interruption errors
			log.error("Error on scanNetworkTask scheluded", exception);
			throw exception;
		}
    }
}
