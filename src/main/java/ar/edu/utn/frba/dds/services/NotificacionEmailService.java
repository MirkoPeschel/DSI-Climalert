package ar.edu.utn.frba.dds.services;

import ar.edu.utn.frba.dds.models.entities.alerta.Alerta;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacionEmailService {

    private final JavaMailSender mailSender;

    private final String[] destinatarios = {
            "admin@clima.com",
            "emergencias@clima.com",
            "meteorologia@clima.com"
    };

    public NotificacionEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarAlerta(Alerta alerta) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatarios);
        mensaje.setSubject("Climalert: Nueva Alerta Meteorológica Generada");

        String cuerpo = String.format(
                "%s\n\n--- DETALLE COMPLETO DEL CLIMA ---\n" +
                        "Ubicación: %s\n" +
                        "emperatura: %.1f°C\n" +
                        "Humedad: %d%%\n" +
                        "Fecha y Hora de registro: %s",
                alerta.getMensaje(),
                alerta.getDatosClimaticos().getUbicacion(),
                alerta.getDatosClimaticos().getCondicionClimatica().temperatura(),
                alerta.getDatosClimaticos().getCondicionClimatica().humedad(),
                alerta.getDatosClimaticos().getFechaHoraRegistro()
        );

        mensaje.setText(cuerpo);

        try {
            mailSender.send(mensaje);
            alerta.marcarComoEnviada();
            System.out.println("Correo de alerta enviado exitosamente a los destinatarios.");
        } catch (Exception e) {
            System.err.println("Error al enviar el correo de alerta: " + e.getMessage());
        }
    }
}