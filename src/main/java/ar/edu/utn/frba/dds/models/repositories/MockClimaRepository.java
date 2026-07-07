package ar.edu.utn.frba.dds.models.repositories;

import ar.edu.utn.frba.dds.models.entities.clima.DatosClimaticos;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Repository
public class MockClimaRepository implements ClimaRepository {

    private final List<DatosClimaticos> historial = new ArrayList<>();

    @Override
    public void guardar(DatosClimaticos clima) {
        historial.add(clima);
    }

    @Override
    public Optional<DatosClimaticos> obtenerUltimo() {
        return historial.stream().max(Comparator.comparing(DatosClimaticos::getFechaHoraRegistro));
    }
}