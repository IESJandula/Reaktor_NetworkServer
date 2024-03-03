package es.iesjandula.reaktor.network_server.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "red")
public class Red implements Serializable
{
	/**
     * Attribute - Id de red
     */
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	/**
     * Attribute - Nombre de red
     */
	@Column
	private String nombre;
	
	/**
     * Attribute - Ruta de red
     */
	@Column
	private String rutaRed;
	
	/** Attribute equipos*/
	@OneToMany(mappedBy = "red")
	private List<Equipo> equipos;
}
