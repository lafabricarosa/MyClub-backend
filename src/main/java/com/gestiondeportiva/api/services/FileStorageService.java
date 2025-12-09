package com.gestiondeportiva.api.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo crear el directorio para almacenar archivos", ex);
        }
    }

    /**
     * Guarda un archivo y retorna el nombre del archivo guardado
     */
    public String guardarArchivo(MultipartFile file) {
        // Validar que el archivo no esté vacío
        if (file.isEmpty()) {
            throw new IllegalArgumentException("El archivo está vacío");
        }

        // Validar tipo de archivo (solo imágenes)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Solo se permiten archivos de imagen");
        }

        // Obtener extensión del archivo
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("El nombre del archivo no es válido");
        }

        String cleanFilename = StringUtils.cleanPath(originalFilename);
        String extension = "";
        int dotIndex = cleanFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = cleanFilename.substring(dotIndex);
        }

        // Generar nombre único para el archivo
        String nuevoNombre = UUID.randomUUID().toString() + extension;

        try {
            // Copiar archivo al directorio de destino
            Path targetLocation = this.fileStorageLocation.resolve(nuevoNombre);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return nuevoNombre;
        } catch (IOException ex) {
            throw new RuntimeException("Error al guardar el archivo: " + nuevoNombre, ex);
        }
    }

    /**
     * Elimina un archivo por su nombre
     */
    public void eliminarArchivo(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            // Log pero no lanzar excepción, es opcional eliminar la foto anterior
            System.err.println("No se pudo eliminar el archivo: " + fileName);
        }
    }

    /**
     * Obtiene la ruta completa del archivo
     */
    public Path obtenerRutaArchivo(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize();
    }
}
