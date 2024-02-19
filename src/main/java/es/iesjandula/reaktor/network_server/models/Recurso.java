package es.iesjandula.reaktor.network_server.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "recurso")
public class Recurso
{
	/**
     * Attribute - Numero de recurso
     */
	@Id
	@Column
	private Long numero;
	
	/**
     * Attribute - Nombre del recurso
     */
	@Column
	private String nombre;
	
	@ManyToOne
	private Equipo equipo;
}
