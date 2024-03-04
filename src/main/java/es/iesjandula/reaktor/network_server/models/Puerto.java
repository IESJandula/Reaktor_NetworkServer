package es.iesjandula.reaktor.network_server.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.iesjandula.reaktor.network_server.models.Id.PuertoId;
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
@Table(name = "puerto")
public class Puerto implements Serializable
{
	/** Attribute serialVersionUID*/
	private static final long serialVersionUID = -3125746195750824463L;

	/**
	 * Attribute - Puerto Id
	 */
	@EmbeddedId
	private PuertoId puertoId;

	/**
	 * Attribute - Nombre del puerto
	 */
	@Column
	private String nombre;

	/** Attribute equipo */
	@ManyToOne
	@MapsId("idEquipo")
	@JsonIgnore
	private Equipo equipo;
}
