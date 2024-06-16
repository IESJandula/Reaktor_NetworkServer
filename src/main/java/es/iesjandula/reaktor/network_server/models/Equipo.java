package es.iesjandula.reaktor.network_server.models;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Equipo implements Serializable
{
	/** Attribute serialVersionUID*/
	private static final long serialVersionUID = -4665765615828242153L;

	/** CONSTANTE - IMPRESORA */
	public final static String TIPO_IMPRESORA = "IMPRESORA";

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

	/** Attribute red */
	@ManyToOne
	@JsonIgnore
	private Red red;

	/** Attribute recursos */
	@OneToMany(mappedBy = "equipo")
	@Cascade(CascadeType.ALL)
	private List<Recurso> recursos;

	/** Attribute puertos */
	@OneToMany(mappedBy = "equipo")
	@Cascade(CascadeType.ALL)
	private List<Puerto> puertos;
}
