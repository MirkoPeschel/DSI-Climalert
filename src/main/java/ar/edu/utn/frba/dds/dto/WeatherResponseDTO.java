package ar.edu.utn.frba.dds.dto;

public record WeatherResponseDTO(LocationDTO location, CurrentDTO current) {

    public record LocationDTO(String name) {}

    public record CurrentDTO(Double temp_c, Integer humidity) {}
}