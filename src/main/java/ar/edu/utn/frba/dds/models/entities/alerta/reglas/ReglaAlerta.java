package ar.edu.utn.frba.dds.models.entities.alerta.reglas;

import ar.edu.utn.frba.dds.models.entities.alerta.Alerta;
import ar.edu.utn.frba.dds.models.entities.clima.DatosClimaticos;
import java.util.Optional;

public interface ReglaAlerta {
    Optional<Alerta> evaluar(DatosClimaticos clima);
}