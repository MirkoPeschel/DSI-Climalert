package ar.edu.utn.frba.dds.clients;

import ar.edu.utn.frba.dds.dto.WeatherResponseDTO;
import ar.edu.utn.frba.dds.models.entities.clima.CondicionClimatica;
import ar.edu.utn.frba.dds.models.entities.clima.DatosClimaticos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ApiClimaClient {
    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final String apiKey;

    public ApiClimaClient(
            @Value("${weather.api.url}") String apiUrl,
            @Value("${weather.api.key}") String apiKey) {
        this.restTemplate = new RestTemplate();
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public Optional<DatosClimaticos> obtenerClimaActual(String ubicacion) {
        String url = String.format("%s/current.json?key=%s&q=%s", apiUrl, apiKey, ubicacion);

        try {
            WeatherResponseDTO responseDTO = restTemplate.getForObject(url, WeatherResponseDTO.class);

            return mapearADominio(responseDTO);
        } catch (Exception e) {
            System.err.println("Error al comunicarse con WeatherAPI: " + e.getMessage());
            return Optional.empty();
        }
    }

    private Optional<DatosClimaticos> mapearADominio(WeatherResponseDTO dto) {
        if (dto == null || dto.current() == null || dto.location() == null) {
            return Optional.empty();
        }

        CondicionClimatica condicion = new CondicionClimatica(
                dto.current().temp_c(),
                dto.current().humidity()
        );

        DatosClimaticos clima = new DatosClimaticos(dto.location().name(), condicion);

        return Optional.of(clima);
    }
}
