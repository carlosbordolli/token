package com.utec.pinfranow.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.from}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarBienvenida(String destinatario, String nombreUsuario) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setFrom(from);
        mensaje.setTo(destinatario);
        mensaje.setSubject("¡Bienvenido a la plataforma!");
        mensaje.setText("Hola " + nombreUsuario + ",\n\nGracias por registrarte. ¡Esperamos que disfrutes la experiencia!\n\nSaludos,\nEl equipo");

        mailSender.send(mensaje);
    }
}