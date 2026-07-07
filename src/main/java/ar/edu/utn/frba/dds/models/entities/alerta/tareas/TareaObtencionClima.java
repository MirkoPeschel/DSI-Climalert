package ar.edu.utn.frba.dds.models.entities.alerta.tareas;

import ar.edu.utn.frba.dds.clients.ApiClimaClient;
import ar.edu.utn.frba.dds.models.entities.clima.DatosClimaticos;
import ar.edu.utn.frba.dds.models.repositories.ClimaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class TareaObtencionClima {

    private final ApiClimaClient ApiClimaclient;
    private final ClimaRepository climaRepository;
    private final String ubicacionObjetivo;

    public TareaObtencionClima(
            ApiClimaClient ApiClimaclient,
            ClimaRepository climaRepository,
            @Value("${climalert.ubicacion.objetivo}") String ubicacionObjetivo) {
        this.ApiClimaclient = ApiClimaclient;
        this.climaRepository = climaRepository;
        this.ubicacionObjetivo = ubicacionObjetivo;
    }

    @Scheduled(fixedRate = 300000)
    public void ejecutarCadaCincoMinutos() {
        System.out.println("[" + LocalDateTime.now() + "] Ejecutando tarea: Consultando clima para " + ubicacionObjetivo + "...");

        Optional<DatosClimaticos> climaOpt = ApiClimaclient.obtenerClimaActual(ubicacionObjetivo);

        if (climaOpt.isPresent()) {
            DatosClimaticos clima = climaOpt.get();
            climaRepository.guardar(clima);
            System.out.println("Clima guardado en historial. Temp: "
                    + clima.getCondicionClimatica().temperatura() + "°C");
        } else {
            System.err.println("Falló la consulta a la API. Se reintentará en 5 minutos.");
        }
    }
}