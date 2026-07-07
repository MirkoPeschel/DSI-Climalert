package ar.edu.utn.frba.dds.models.entities.alerta;

import ar.edu.utn.frba.dds.models.entities.clima.DatosClimaticos;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Alerta {
    private String id;
    private TipoAlerta tipo;
    private String mensaje;
    private LocalDateTime fechaHoraGeneracion;
    private Boolean enviada;
    private DatosClimaticos datosClimaticos;

    public Alerta(TipoAlerta tipo, String mensaje, DatosClimaticos datosClimaticos) {
        this.id = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.datosClimaticos = datosClimaticos;
        this.fechaHoraGeneracion = LocalDateTime.now();
        this.enviada = false;
    }

    public void marcarComoEnviada() {
        this.enviada = true;
    }
}