package br.com.htg.backend;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Classe que irá iniciar a funcionamento e a configuração do jersey do servidor
 * backend e irá indicar onde estarão as classes controladoras do servidor.
 *
 * @author Helbert Gomes
 */
@ApplicationPath("rest")
public class MyApplication extends ResourceConfig {

    /**
     * Construtor, que irá configurar o caminho dos controladores.
     */
    public MyApplication() {
        packages("br.com.htg.backend.controllers");
    }
}
