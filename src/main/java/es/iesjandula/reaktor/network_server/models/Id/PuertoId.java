package es.iesjandula.reaktor.network_server.models.Id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Embeddable
public class PuertoId implements Serializable
{
	/** Serial Version UID */
	private static final long serialVersionUID = 6942215107233325773L;

	/** Atributo - Numero de puerto */
	private Integer numero;

	/** Atributo - Id Equipo */
	private Long idEquipo;
}
