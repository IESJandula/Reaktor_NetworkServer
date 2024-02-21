package es.iesjandula.reaktor.network_server.utils;

import es.iesjandula.reaktor.network_server.models.Equipo;

public class Utils
{
	/**
	 * MÉTODO QUE PASANDOLE UN EQUIPO, IDENTIFIQUE EL TIPO DE EQUIPO
	 * TIPOS POSIBLES:(PC, SERVER O IMPRESORA)
	 * @param equipo
	 */
	public void obtainType(Equipo equipo)
	{
		int i = 0;
		while(i < equipo.getPuertos().size() && !equipo.getTipo().isEmpty())
		{
			//Si el número del puerto es 1
			if(equipo.getPuertos().get(i).getPuertoId().getNumero() == 1)
			{
				//Será una impresora
				equipo.setTipo(Equipo.TIPO_IMPRESORA);
			}
			//Si el número del puerto es 2
			else if(equipo.getPuertos().get(i).getPuertoId().getNumero() == 2)
			{
				//Será un server
				equipo.setTipo(Equipo.TIPO_SERVER);
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
