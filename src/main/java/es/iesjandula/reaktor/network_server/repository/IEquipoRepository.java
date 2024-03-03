package es.iesjandula.reaktor.network_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.network_server.models.Equipo;
import es.iesjandula.reaktor.network_server.models.Red;

public interface IEquipoRepository extends JpaRepository<Equipo, Long>
{
	public List<Equipo> findByRed(Red red);
}
