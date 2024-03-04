package es.iesjandula.reaktor.network_server.exception;

import java.util.HashMap;
import java.util.Map;

public class NetworkException extends Exception
{
	/** Attribute serialVersionUID*/
	private static final long serialVersionUID = -4823242799333492495L;

	/** Codigo de error */
	private int codigo;

	/** Breve descripcion del error */
	private String mensaje;

	/** Causa del error */
	private Exception exception;

	/**
	 * Constructor que crea la excepcion usando un codigo, un mensaje y una causa
	 *
	 * @param codigo
	 * @param mensaje
	 * @param exception
	 */
	public NetworkException(int codigo, String mensaje, Exception exception)
	{
		super(mensaje, exception);
		this.mensaje = mensaje;
		this.codigo = codigo;
		this.exception = exception;
	}

	/**
	 * Constructor que crea la excepcion usando un codigo y un mensaje
	 *
	 * @param codigo
	 * @param mensaje
	 * @param exception
	 */
	public NetworkException(int codigo, String mensaje)
	{
		super(mensaje);
		this.mensaje = mensaje;
		this.codigo = codigo;
		this.exception = null;
	}

	/**
	 * Metodo que devuelve la causa del error en un mapa
	 *
	 * @return mapa con la informacion del error
	 */
	public Map<String, Object> getBodyException()
	{
		Map<String, Object> body = new HashMap<>();

		body.put("code", this.codigo);
		body.put("message", this.mensaje);
		if (this.exception != null)
		{
			body.put("causa", this.exception.getMessage());
		}

		return body;
	}
}