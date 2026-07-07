package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.models.entities.alerta.Alerta;
import ar.edu.utn.frba.dds.models.entities.alerta.reglas.ReglaAlerta;
import ar.edu.utn.frba.dds.models.entities.clima.DatosClimaticos;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluacionAlertasService {

    private final List<ReglaAlerta> reglas;

    public EvaluacionAlertasService(List<ReglaAlerta> reglas) {
        this.reglas = reglas;
    }

    public List<Alerta> procesarClima(DatosClimaticos ultimoClima) {
        return reglas.stream()
                .map(regla -> regla.evaluar(ultimoClima))
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}