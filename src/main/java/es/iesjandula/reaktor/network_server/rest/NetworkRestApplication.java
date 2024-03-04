package es.iesjandula.reaktor.network_server.rest;

import java.util.ArrayList;
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
	/** Attribute iRedRepository */
	@Autowired
	private IRedRepository iRedRepository;

	/** Logger de la clase */
	private static Logger log = LogManager.getLogger();

	/**
	 * Method getScanData get all the data
	 *
	 * @return List<Red>
	 */
	@Operation
	@RequestMapping(method = RequestMethod.GET, value = "/get/all/data",produces="application/json")
	public ResponseEntity<List<Red>> getScanData()
	{
		try
		{
			// GET ALL RED LIST
			List<Red> allDataList = this.iRedRepository.findAll();
			return ResponseEntity.ok().body(allDataList);
		}
		catch (Exception exception)
		{
			// IF ANY ERROR , RETURNS EMPTY LIST (FOR THE SWAGGER EXAMPLES)
			String error = "Error getting the info";
			log.error(error, exception);
			return ResponseEntity.status(500).body(new ArrayList<>());
		}

	}
}
