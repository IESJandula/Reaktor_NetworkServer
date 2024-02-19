package es.iesjandula.reaktor.network_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.iesjandula.reaktor.network_server.models.Red;

public interface IRedRepository extends JpaRepository<Red, Long>
{

}
