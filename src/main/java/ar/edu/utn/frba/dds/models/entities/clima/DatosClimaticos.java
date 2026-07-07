package ar.edu.utn.frba.dds.models.entities.clima;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class DatosClimaticos {
    private String id;
    private String ubicacion;
    private LocalDateTime fechaHoraRegistro;
    private CondicionClimatica condicionClimatica;

    public DatosClimaticos(String ubicacion, CondicionClimatica condicionClimatica) {
        this.id = UUID.randomUUID().toString();
        this.ubicacion = ubicacion;
        this.condicionClimatica = condicionClimatica;
        this.fechaHoraRegistro = LocalDateTime.now();
    }
}