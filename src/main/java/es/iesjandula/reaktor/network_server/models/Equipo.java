package es.iesjandula.reaktor.network_server.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "equipo")
public class Equipo
{
	/** CONSTANTE - IMPRESORA */
	public final static String TIPO_IMPRESORA = "IMPRESORA";
	
	/** CONSTANTE - SERVER */
	public final static String TIPO_SERVER = "SERVER";
	
	/** CONSTANTE - STANDARD */
	public final static String TIPO_STANDARD = "STANDARD";
	
	/**
     * Attribute - Id del equipo
     */
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	/**
     * Attribute - Sistema Operativo del equipo
     */
	@Column
	private String so;
	
	/**
     * Attribute - Id de equipo
     */
	@Column
	private String ip;
	
	/**
     * Attribute - Mac
     */
	@Column
	private String mac;
	
	/**
     * Attribute - Tipo de equipo
     */
	@Column
	private String tipo;
	
	@ManyToOne
	private Red red;
	
	@OneToMany(mappedBy = "equipo")
	private List<Recurso> recursos;
	
	@OneToMany(mappedBy = "equipo")
	private List<Puerto> puertos;
}
