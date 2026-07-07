package ar.edu.utn.frba.dds.models.entities.alerta.tareas;

import ar.edu.utn.frba.dds.models.entities.alerta.Alerta;
import ar.edu.utn.frba.dds.models.entities.clima.DatosClimaticos;
import ar.edu.utn.frba.dds.models.repositories.ClimaRepository;
import ar.edu.utn.frba.dds.services.EvaluacionAlertasService;
import ar.edu.utn.frba.dds.services.NotificacionEmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class TareaProcesadorAlertas {

    private final ClimaRepository repositorioClima;
    private final EvaluacionAlertasService evaluador;
    private final NotificacionEmailService notificadorEmail;

    public TareaProcesadorAlertas(
            ClimaRepository repositorioClima,
            EvaluacionAlertasService evaluador,
            NotificacionEmailService notificadorEmail) {
        this.repositorioClima = repositorioClima;
        this.evaluador = evaluador;
        this.notificadorEmail = notificadorEmail;
    }

    @Scheduled(fixedRate = 60000)
    public void evaluarCadaMinuto() {
        System.out.println("[" + LocalDateTime.now() + "] Analizando últimas condiciones climáticas...");

        Optional<DatosClimaticos> climaOpt = repositorioClima.obtenerUltimo();

        if (climaOpt.isPresent()) {
            List<Alerta> alertasGeneradas = evaluador.procesarClima(climaOpt.get());

            if (alertasGeneradas.isEmpty()) {
                System.out.println("Condiciones normales. No se generaron alertas.");
            } else {
                alertasGeneradas.forEach(alerta -> {
                    System.out.println("NUEVA ALERTA GENERADA: " + alerta.getMensaje());
                    notificadorEmail.enviarAlerta(alerta);
                });
            }
        } else {
            System.out.println("Esperando datos... Aún no hay registros climáticos para analizar.");
        }
    }
}