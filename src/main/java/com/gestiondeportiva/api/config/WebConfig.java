package com.gestiondeportiva.api.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración de Spring MVC para manejo de recursos estáticos.
 * <p>
 * Configura el servidor para servir archivos estáticos (como fotos de perfil) desde
 * el sistema de archivos local. Esto permite que las imágenes subidas por los usuarios
 * sean accesibles a través de URLs HTTP.
 * </p>
 *
 * <p><strong>Configuración actual:</strong></p>
 * <ul>
 *   <li>Directorio de almacenamiento: Configurable mediante propiedad file.upload-dir</li>
 *   <li>URL de acceso: /uploads/fotos-perfil/**</li>
 *   <li>Ejemplo: /uploads/fotos-perfil/usuario123.jpg mapea a {upload-dir}/usuario123.jpg</li>
 * </ul>
 *
 * <p><strong>Nota:</strong></p>
 * <p>
 * Esta configuración es principalmente útil para entornos de desarrollo local.
 * En producción, se recomienda usar servicios de almacenamiento en la nube como
 * Cloudinary, AWS S3 o similar para mejor rendimiento y escalabilidad.
 * </p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * Configura los manejadores de recursos estáticos.
     * <p>
     * Registra un handler que mapea URLs con el patrón /uploads/fotos-perfil/**
     * al directorio físico especificado en la propiedad file.upload-dir.
     * </p>
     *
     * <p><strong>Funcionamiento:</strong></p>
     * <ol>
     *   <li>Lee la propiedad file.upload-dir del archivo application.properties</li>
     *   <li>Normaliza la ruta a una ruta absoluta del sistema</li>
     *   <li>Configura Spring para servir archivos de ese directorio en /uploads/fotos-perfil/**</li>
     * </ol>
     *
     * @param registry registro de manejadores de recursos de Spring MVC
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Obtener la ruta absoluta del directorio de uploads
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        String uploadLocation = "file:" + uploadPath.toString() + "/";

        // Configurar el handler para servir archivos estáticos
        registry.addResourceHandler("/uploads/fotos-perfil/**")
                .addResourceLocations(uploadLocation);
    }
}
