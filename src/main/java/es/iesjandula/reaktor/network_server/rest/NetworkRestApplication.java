package es.iesjandula.reaktor.network_server.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.network_server.constants.Constants;
import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.models.Puerto;
import es.iesjandula.reaktor.network_server.models.Red;
import es.iesjandula.reaktor.network_server.repository.IEquipoRepository;
import es.iesjandula.reaktor.network_server.repository.IPuertoRepository;
import es.iesjandula.reaktor.network_server.repository.IRedRepository;
import es.iesjandula.reaktor.network_server.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/net")
public class NetworkRestApplication
{
	/** Attribute iRedRepository */
	@Autowired
	private IRedRepository iRedRepository;

	/** Attribute iEquipoRepository */
	@Autowired
	private IEquipoRepository iEquipoRepository;

	/** Attribute iPuertoRepository */
	@Autowired
	private IPuertoRepository iPuertoRepository;

	/** Logger de la clase */
	private static Logger log = LogManager.getLogger();

	/**
	 * Method getScanData get all the data
	 *
	 * @return List<Red>
	 */
	@Operation
	@RequestMapping(method = RequestMethod.GET, value = "/get/all/data", produces = "application/json")
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

	/**
	 * Metodo searchRed filtra la red y encuentra equipos segun lo que se le pase en el filtro
	 * 
	 * @param ip
	 * @param mac
	 * @param tipo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/red/by/search", produces = "application/json")
	public ResponseEntity<List<Red>> searchRed(
				@RequestParam(required = false) String ip,
				@RequestParam(required = false) String mac,
				@RequestParam(required = false) String tipo
			)
	{
		try
		{
			// GET ALL RED LIST sorted by date in descending order
			List<Red> allDataList = this.iRedRepository.findAll(Sort.by(Sort.Direction.DESC, "fecha"));

			List<Red> filteredRedes = new ArrayList<>();
			Set<String> ipsAgregadas = new HashSet<>();
			Set<String> macsAgregadas = new HashSet<>();
			
			Date fechaHoy = new Date();
			LocalDateTime localDateTimeHoy = fechaHoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			if(!ip.equals(""))
			{
				for (Red red : allDataList)
				{
					Date fechaRed = red.getFecha();
					LocalDateTime localDateTimeFechaRed = fechaRed.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	
					if (localDateTimeHoy.getDayOfMonth() == localDateTimeFechaRed.getDayOfMonth())
					{
						List<Equipo> equiposUnicos = new ArrayList<>();
						for (Equipo equipo : red.getEquipos())
						{
							if (!ipsAgregadas.contains(equipo.getIp()) && equipo.getIp().equals(ip))
							{
								equiposUnicos.add(equipo);
								ipsAgregadas.add(equipo.getIp());
							}
						}
						red.setEquipos(equiposUnicos);
						filteredRedes.add(red);
					}
				}
			}
			else if(!mac.equals(""))
			{
				for (Red red : allDataList)
				{
					Date fechaRed = red.getFecha();
					LocalDateTime localDateTimeFechaRed = fechaRed.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	
					if (localDateTimeHoy.getDayOfMonth() == localDateTimeFechaRed.getDayOfMonth())
					{
						List<Equipo> equiposUnicos = new ArrayList<>();
						for (Equipo equipo : red.getEquipos())
						{
							if (!macsAgregadas.contains(equipo.getMac()) && equipo.getMac().equals(mac))
							{
								equiposUnicos.add(equipo);
								macsAgregadas.add(equipo.getMac());
							}
						}
						red.setEquipos(equiposUnicos);
						filteredRedes.add(red);
					}
				}
			}
			else if (!tipo.equals(""))
			{
				for (Red red : allDataList)
				{
			        Date fechaRed = red.getFecha();
			        LocalDateTime localDateTimeFechaRed = fechaRed.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

			        if (localDateTimeHoy.getDayOfMonth() == localDateTimeFechaRed.getDayOfMonth())
			        {
			            List<Equipo> equiposUnicos = new ArrayList<>();
			            for (Equipo equipo : red.getEquipos())
			            {
			            	// Añade el equipo si su tipo coincide con el tipo buscado
			                if (equipo.getTipo() != null)
			                {
			                	if(!ipsAgregadas.contains(equipo.getIp()) && equipo.getTipo().equals(tipo))
			                	{
				                    equiposUnicos.add(equipo);
				                    ipsAgregadas.add(equipo.getIp());
			                	}
			                }
			            }
			            red.setEquipos(equiposUnicos);
			            filteredRedes.add(red);
			        }
			    }
			}
			else
			{
				for (Red red : allDataList)
				{
					Date fechaRed = red.getFecha();
					LocalDateTime localDateTimeFechaRed = fechaRed.toInstant().atZone(ZoneId.systemDefault())
							.toLocalDateTime();
	
					if (localDateTimeHoy.getDayOfMonth() == localDateTimeFechaRed.getDayOfMonth())
					{
						List<Equipo> equiposUnicos = new ArrayList<>();
						for (Equipo equipo : red.getEquipos())
						{
							if (!ipsAgregadas.contains(equipo.getIp()))
							{
								equiposUnicos.add(equipo);
								ipsAgregadas.add(equipo.getIp());
							}
						}
						red.setEquipos(equiposUnicos);
						filteredRedes.add(red);
					}
				}
			}
			
			
			return ResponseEntity.ok().body(filteredRedes);
		} catch (Exception exception)
		{
			// IF ANY ERROR, RETURNS EMPTY LIST (FOR THE SWAGGER EXAMPLES)
			String error = "Error getting the info";
			log.error(error, exception);
			return ResponseEntity.status(500).body(new ArrayList<>());
		}
	}
	
	/**
	 * Metodo checkIsBlankEmpty que comprueba si los campos vienen vacíos o en
	 * blanco
	 * 
	 * @param stringParameter
	 * @return true si lo son, false si no
	 */
	private boolean checkIsBlankEmpty(String stringParameter)
	{
		if (stringParameter.isBlank() || stringParameter.isEmpty() || stringParameter == null)
		{
			return true;
		}
		return false;
	}

	/**
	 * Metodo deleteRedBeforeDate que borra la información de todas las redes
	 * anteriores a un día
	 * 
	 * @param numeroDias
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/red/deleteAllBefore", produces = "application/json")
	public ResponseEntity<?> deleteRedBeforeDate(@RequestParam(required = false) String numeroDiasString)
	{
		try
		{
			if (numeroDiasString != null && !numeroDiasString.isEmpty())
			{
				// Convertir la cadena a un entero
				Integer numeroDias = Integer.parseInt(numeroDiasString);
				LocalDate hoy = LocalDate.now();
				LocalDateTime localDateTimeHoy = hoy.atStartOfDay();

				LocalDateTime localDateTimeBorrarAntesDe = localDateTimeHoy.minusDays(numeroDias);

				List<Red> redesList = this.iRedRepository.findByFechaBefore(localDateTimeBorrarAntesDe);

				if (numeroDias >= 0)
				{
					log.info("Se pretende borrar toda la información de redes registradas antes de la fecha: "
							+ localDateTimeBorrarAntesDe);

					for (Red red : redesList)
					{
						this.iRedRepository.delete(red);
						log.info("Red borrada: " + red.getId() + " --> " + red.getWlanConectionName());
					}
				} else
				{
					Red ultimaFechaIntroducida = null;
					for (Red red : redesList)
					{
						if (ultimaFechaIntroducida == null || red.getFecha().after(ultimaFechaIntroducida.getFecha()))
						{
							ultimaFechaIntroducida = red;
						}
					}

					log.info("Se borrarán todas las redes, excepto la ultima escaneada o en proceso de escaneo");
					for (Red red : redesList)
					{
						if (!red.equals(ultimaFechaIntroducida))
						{
							this.iRedRepository.delete(red);
							log.info("Red borrada: " + red.getId() + " --> " + red.getWlanConectionName());
						}
					}
				}
			} else
			{
				String error = "El numero de días no puede ser nulo o vacio";
				log.error(error);
				return ResponseEntity.status(500).body(error);
			}

			return ResponseEntity.ok().body("OK");
		} catch (Exception exception)
		{
			// IF ANY ERROR , RETURNS EMPTY LIST (FOR THE SWAGGER EXAMPLES)
			String error = "Error getting the info";
			log.error(error, exception);
			return ResponseEntity.status(500).body(new ArrayList<>());
		}
	}

}
