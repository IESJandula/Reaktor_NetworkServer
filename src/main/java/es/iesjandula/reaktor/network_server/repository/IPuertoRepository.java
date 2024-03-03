package es.iesjandula.reaktor.network_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.network_server.models.Puerto;
import es.iesjandula.reaktor.network_server.models.Id.PuertoId;

/**
 * @author David Martinez
 *
 */
public interface IPuertoRepository extends JpaRepository<Puerto, PuertoId>
{

}
