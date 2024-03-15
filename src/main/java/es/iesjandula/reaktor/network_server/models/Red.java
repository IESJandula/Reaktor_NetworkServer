package es.iesjandula.reaktor.network_server.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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
	/** Attribute serialVersionUID */
	private static final long serialVersionUID = -1686157942189219531L;

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
	 * Attribute - Mac de red
	 */
	@Column
	private String mac;

	/**
	 * Attribute - Nombre de la conexion wlans solamente
	 */
	@Column
	private String wlanConectionName;

	/**
	 * Attribute - Ruta de red
	 */
	@Column
	private String rutaRed;
	
	/**
	 * Attribute - fecha de introducción
	 */
	@Column
	private Date fecha;

	/** Attribute equipos */
	@OneToMany(mappedBy = "red")
	@Cascade(CascadeType.ALL)
	private List<Equipo> equipos;
}
