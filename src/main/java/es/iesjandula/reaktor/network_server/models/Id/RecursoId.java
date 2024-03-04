package es.iesjandula.reaktor.network_server.models.Id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Embeddable
public class RecursoId implements Serializable
{
	/** Serial Version UID */
	private static final long serialVersionUID = -8102093468287399485L;

	/** Atributo - Numero Recurso */
	@GeneratedValue
	private Long numero;

	/** Atributo - Id Equipo */
	private Long idEquipo;

}
