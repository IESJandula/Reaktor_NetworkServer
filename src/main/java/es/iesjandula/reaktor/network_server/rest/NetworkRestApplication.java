package es.iesjandula.reaktor.network_server.rest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.iesjandula.reaktor.network_server.exception.NetworkException;
import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.models.Puerto;
import es.iesjandula.reaktor.network_server.models.Red;
import es.iesjandula.reaktor.network_server.repository.IEquipoRepository;
import es.iesjandula.reaktor.network_server.repository.IPuertoRepository;
import es.iesjandula.reaktor.network_server.repository.IRedRepository;
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
	@RequestMapping(method = RequestMethod.GET, value = "/get/all/data",produces="application/json")
	public ResponseEntity<List<Red>> getScanData()
	{
		try
		{
			// GET ALL RED LIST
			List<Red> allDataList = this.iRedRepository.findAll();
            List<Red> redesHoy = new ArrayList<>();
            Set<String> ipsAgregadas = new HashSet<>();

            Date fechaHoy = new Date();
            LocalDateTime localDateTimeHoy = fechaHoy.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            for (Red red : allDataList) {
                Date fechaRed = red.getFecha();
                LocalDateTime localDateTimeFechaRed = fechaRed.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

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
                    redesHoy.add(red);
                }
            }

            return ResponseEntity.ok().body(redesHoy);
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
	 * Metodo searchRed busca red por el atributo que le pases
	 * 
	 * @param wLanConnectionName
	 * @param ip
	 * @param so
	 * @param numeroPuerto
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/red/by/search",produces="application/json")
	public ResponseEntity<?> searchRed
	(
			@RequestHeader(required = false) String wLanConnectionName,
			@RequestHeader(required = false) String ip,
			@RequestHeader(required = false) String so,
			@RequestHeader(required = false) Integer numeroPuerto)
	{
		try
		{
			List<Equipo> equiposList = new ArrayList<Equipo>();
			
			if((wLanConnectionName != null) || (ip != null) || (so != null) || (numeroPuerto != null))
			{
				if(wLanConnectionName != null)
				{
					// --- POR NOMBRE DE CONEXIÓN WIRELESS ---
					if (this.checkIsBlankEmpty(wLanConnectionName))
					{
						String error = "wLanConnectionName or nombreRed is Empty or Blank";
						NetworkException networkException = new NetworkException(404, error, null);
						return ResponseEntity.status(404).body(networkException.getMessage());
					}
					
					List<Red> redList = this.iRedRepository.findAll();
					
					for (Red red : redList) 
					{
						if(red.getWlanConectionName() != null && red.getWlanConectionName().equals(wLanConnectionName))
						{
							equiposList.addAll(red.getEquipos());
						}
					}
				}
				else if(ip != null)
				{
					// --- POR IP ---
					if (this.checkIsBlankEmpty(ip))
					{
						String error = "ip is Empty or Blank";
						NetworkException networkException = new NetworkException(404, error, null);
						return ResponseEntity.status(404).body(networkException.getMessage());
					}
					equiposList.addAll(this.iEquipoRepository.findByIp(ip));
				}
				else if (so != null)
				{
					// --- POR SISTEMA OPERATIVO ---
					if(this.checkIsBlankEmpty(so))
					{
						String error = "so is Empty or Blank";
						NetworkException networkException = new NetworkException(404, error, null);
						return ResponseEntity.status(404).body(networkException.getMessage());
					}
					equiposList.addAll(this.iEquipoRepository.findBySo(so));
				}
				else if (numeroPuerto != null)
				{
					// --- POR PUERTO ---
					if(this.checkIsBlankEmpty(numeroPuerto.toString()))
					{
						String error = "numeroPuerto is Empty or Blank";
						NetworkException networkException = new NetworkException(404, error, null);
						return ResponseEntity.status(404).body(networkException.getMessage());
					}
					List<Puerto> puertosList = this.iPuertoRepository.findAll();
					
					for (Puerto puerto : puertosList) 
					{
						if(puerto.getPuertoId().getNumero().equals(numeroPuerto))
						{
							equiposList.add(puerto.getEquipo());
						}
					}
				}
			}
			else
			{
				// Si no viene ningún parámetro se mostrarán todos los equipos dsiponibles
				equiposList.addAll(this.iEquipoRepository.findAll());
			}
			
			return ResponseEntity.ok().body(equiposList);
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
	 * Metodo checkIsBlankEmpty que comprueba si los campos vienen vacíos o en blanco
	 * 
	 * @param stringParameter
	 * @return true si lo son, false si no
	 */
	private boolean checkIsBlankEmpty(String stringParameter)
	{
		if (stringParameter.isBlank() || stringParameter.isEmpty())
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo deleteRedBeforeDate que borra la información de todas las redes anteriores a un día
	 * 
	 * @param numeroDias
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/red/deleteAllBefore",produces="application/json")
	public ResponseEntity<?> deleteRedBeforeDate(@RequestParam(required = false) String numeroDiasString)
	{
		try
		{
			if (numeroDiasString != null && !numeroDiasString.isEmpty()) 
			{
	            // Convertir la cadena a un entero
	            Integer numeroDias = Integer.parseInt(numeroDiasString);
	            if (numeroDias >= 0) 
	            {
	                LocalDate hoy = LocalDate.now();
	                LocalDateTime localDateTimeHoy = hoy.atStartOfDay();
	                LocalDateTime localDateTimeBorrarAntesDe = localDateTimeHoy.minusDays(numeroDias);
	                log.info("Se pretende borrar toda la información de redes registradas antes de la fecha: " + localDateTimeBorrarAntesDe);

	                List<Red> redesList = this.iRedRepository.findByFechaBefore(localDateTimeBorrarAntesDe);

	                for (Red red : redesList) 
	                {
	                    this.iRedRepository.delete(red);
	                    log.info("Red borrada: " + red.getId() + " --> " + red.getWlanConectionName());
	                }
	            } 
	            else 
	            {
	                String error = "El número de días debe ser mayor o igual que 0";
	                log.error(error);
	            }
	        } 
			else 
	        {
	            String error = "El numero de días debe ser mayor que 0";
	            log.error(error);
	            return ResponseEntity.status(500).body(error);
	        }
			
			return ResponseEntity.ok().body("OK");
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
