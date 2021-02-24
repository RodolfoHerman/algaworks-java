package br.com.rodolfo.algafood.notificador;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("notificador.email")
public class NotificadorProperties {

    /**
     * Host do servidor de e-mail
     */
    private String hostServidor;

    /**
     * Porta do servidor de e-mail
     */
    private Integer portaServidor = 28;

    public String getHostServidor() {
        return this.hostServidor;
    }

    public void setHostServidor(String hostServidor) {
        this.hostServidor = hostServidor;
    }

    public Integer getPortaServidor() {
        return this.portaServidor;
    }

    public void setPortaServidor(Integer portaServidor) {
        this.portaServidor = portaServidor;
    }
}
