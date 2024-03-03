package es.iesjandula.reaktor.network_server.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.iesjandula.reaktor.network_server.models.Id.RecursoId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "recurso")
public class Recurso implements Serializable
{
	/**
     * Attribute - Numero de recurso
     */
	@EmbeddedId
	private RecursoId recursoId;
	
	/**
     * Attribute - Nombre del recurso
     */
	@Column
	private String nombre;
	
	/** Attribute equipo*/
	@ManyToOne
	@JsonIgnore
	@MapsId("idEquipo")
	private Equipo equipo;
}
