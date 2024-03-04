package es.iesjandula.reaktor.network_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.models.Red;

/**
 * @author David Martinez
 *
 */
public interface IEquipoRepository extends JpaRepository<Equipo, Long>
{
	/**
	 * Method findByRed
	 * 
	 * @param  red
	 * @return
	 */
	public List<Equipo> findByRed(Red red);
}
