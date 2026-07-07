package ar.edu.utn.frba.dds.models.entities.alerta.reglas;

import ar.edu.utn.frba.dds.models.entities.alerta.Alerta;
import ar.edu.utn.frba.dds.models.entities.alerta.TipoAlerta;
import ar.edu.utn.frba.dds.models.entities.clima.DatosClimaticos;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ReglaAltaTemperaturaYHumedad implements ReglaAlerta {

    private static final Double temperaturaMax = 35.0;
    private static final Integer humedadMax = 60;

    @Override
    public Optional<Alerta> evaluar(DatosClimaticos clima) {
        Double tempActual = clima.getCondicionClimatica().temperatura();
        Integer humedadActual = clima.getCondicionClimatica().humedad();

        if (tempActual > temperaturaMax && humedadActual > humedadMax) {
            String mensaje = String.format(
                    "¡ALERTA CRÍTICA! En %s se registró una temperatura de %.1f°C y %d%% de humedad.",
                    clima.getUbicacion(), tempActual, humedadActual
            );

            return Optional.of(new Alerta(TipoAlerta.ALERTA_CALOR_HUMEDAD, mensaje, clima));
        }

        return Optional.empty();
    }
}