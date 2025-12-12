package com.gestiondeportiva.api.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 * Servicio para la gestión de imágenes en Cloudinary.
 * <p>
 * Proporciona funcionalidades para subir, transformar y eliminar imágenes
 * en el servicio cloud de Cloudinary. Las imágenes se optimizan automáticamente
 * (tamaño, calidad, formato) antes de almacenarlas.
 * </p>
 *
 * <p><strong>Configuración:</strong></p>
 * <ul>
 *   <li>Tamaño máximo de imagen: 500x500px (manteniendo aspecto)</li>
 *   <li>Calidad: automática (optimizada por Cloudinary)</li>
 *   <li>Formato: automático (WebP cuando el navegador lo soporte)</li>
 *   <li>Conexión: HTTPS segura</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 */
@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    /**
     * Constructor que inicializa el cliente de Cloudinary con las credenciales.
     *
     * @param cloudName nombre del cloud de Cloudinary
     * @param apiKey clave API de Cloudinary
     * @param apiSecret secreto API de Cloudinary
     */
    public CloudinaryService(
            @Value("${cloudinary.cloud-name}") String cloudName,
            @Value("${cloudinary.api-key}") String apiKey,
            @Value("${cloudinary.api-secret}") String apiSecret) {

        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true));
    }

    /**
     * Sube una imagen a Cloudinary y retorna la URL segura de la imagen.
     * <p>
     * Valida que el archivo sea una imagen y no esté vacío. Aplica transformaciones
     * automáticas para optimizar el tamaño, calidad y formato.
     * </p>
     *
     * @param file archivo MultipartFile con la imagen
     * @param folder carpeta destino en Cloudinary
     * @return URL segura (HTTPS) de la imagen subida
     * @throws IllegalArgumentException si el archivo está vacío o no es una imagen
     * @throws RuntimeException si hay un error durante la subida
     */
    public String subirImagen(MultipartFile file, String folder) {
        // Validar que el archivo no esté vacío
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // Validar tipo de archivo (solo imágenes)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten archivos de imagen");
        }

        try {
            // Configurar opciones de subida
            Map<String, Object> uploadParams = ObjectUtils.asMap(
                    "folder", folder,
                    "resource_type", "image",
                    "transformation", new com.cloudinary.Transformation()
                            .width(500)
                            .height(500)
                            .crop("limit")
                            .quality("auto")
                            .fetchFormat("auto"));

            // Subir el archivo a Cloudinary
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), uploadParams);

            // Retornar la URL segura de la imagen
            return (String) uploadResult.get("secure_url");

        } catch (IOException ex) {
            throw new RuntimeException("Error al subir la imagen a Cloudinary", ex);
        }
    }

    /**
     * Elimina una imagen de Cloudinary usando su public_id
     */
    public void eliminarImagen(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }

        try {
            // Extraer el public_id de la URL
            // Ejemplo URL: https://res.cloudinary.com/cloud-name/image/upload/v123456/folder/public_id.jpg
            String publicId = extractPublicIdFromUrl(imageUrl);

            if (publicId != null && !publicId.isEmpty()) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (Exception ex) {
            // Log pero no lanzar excepción, es opcional eliminar la foto anterior
            System.err.println("No se pudo eliminar la imagen de Cloudinary: " + imageUrl);
            ex.printStackTrace();
        }
    }

    /**
     * Extrae el public_id de una URL de Cloudinary
     */
    private String extractPublicIdFromUrl(String imageUrl) {
        if (imageUrl == null || !imageUrl.contains("cloudinary.com")) {
            return null;
        }

        try {
            // La URL tiene el formato: .../upload/v123456/folder/public_id.ext
            String[] parts = imageUrl.split("/upload/");
            if (parts.length < 2) {
                return null;
            }

            String afterUpload = parts[1];
            // Remover la versión (v123456)
            String[] versionParts = afterUpload.split("/", 2);
            if (versionParts.length < 2) {
                return null;
            }

            String pathWithExtension = versionParts[1];
            // Remover la extensión
            int lastDot = pathWithExtension.lastIndexOf('.');
            if (lastDot > 0) {
                return pathWithExtension.substring(0, lastDot);
            }

            return pathWithExtension;
        } catch (Exception ex) {
            System.err.println("Error al extraer public_id de la URL: " + imageUrl);
            return null;
        }
    }
}
