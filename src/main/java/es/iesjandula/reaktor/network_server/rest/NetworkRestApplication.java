package es.iesjandula.reaktor.network_server.rest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.network_server.models.Red;
import es.iesjandula.reaktor.network_server.repository.IRedRepository;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/net")
public class NetworkRestApplication 
{
	/** Attribute iRedRepository*/
	@Autowired
	private IRedRepository iRedRepository;
	
	/**Logger de la clase */
	private static Logger log = LogManager.getLogger();
	
	/**
	 * Method getScanData get all the data
	 * @return List<Red>
	 */
	@Operation
	@RequestMapping(method = RequestMethod.GET,value = "/get/all/data")
	public ResponseEntity<List<Red>> getScanData()
	{
		List<Red> allDataList = this.iRedRepository.findAll();
		//log.info(allDataList);
		return ResponseEntity.ok().body(allDataList);
	}
}
		