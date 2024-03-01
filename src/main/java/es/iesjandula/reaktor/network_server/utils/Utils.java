package es.iesjandula.reaktor.network_server.utils;

import es.iesjandula.reaktor.network_server.models.Equipo;

public class Utils
{
	/**
	 * MÉTODO QUE PASANDOLE UN EQUIPO, IDENTIFIQUE EL TIPO DE EQUIPO
	 * TIPOS POSIBLES:(PC O IMPRESORA)
	 * @param equipo
	 */
	public void obtainType(Equipo equipo)
	{
		int i = 0;
		while(i < equipo.getPuertos().size() && !equipo.getTipo().isEmpty())
		{
			//Si el número del puerto es el 9100, el 515 o el 631
			if(equipo.getPuertos().get(i).getPuertoId().getNumero() == 9100 || equipo.getPuertos().get(i).getPuertoId().getNumero() == 515 || equipo.getPuertos().get(i).getPuertoId().getNumero() == 631)
			{
				//Será una impresora
				equipo.setTipo(Equipo.TIPO_IMPRESORA);
			}
			i++;
		}
		
		//Si no ha encontrado nada
		if(equipo.getTipo().isEmpty())
		{
			//Será un pc
			equipo.setTipo(Equipo.TIPO_STANDARD);
		}
	}
}
