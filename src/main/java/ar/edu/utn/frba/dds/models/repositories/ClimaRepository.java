package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.clima.DatosClimaticos;
import java.util.Optional;

public interface ClimaRepository {
    void guardar(DatosClimaticos clima);
    Optional<DatosClimaticos> obtenerUltimo();
}