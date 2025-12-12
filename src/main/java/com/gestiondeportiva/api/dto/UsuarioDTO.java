package com.gestiondeportiva.api.dto;

import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;

/**
 * DTO (Data Transfer Object) para transferir datos de usuarios en respuestas API.
 * <p>
 * Representa la información de un usuario del sistema, excluyendo datos sensibles
 * como la contraseña. Utilizado principalmente en operaciones de consulta donde
 * se requiere enviar información del usuario al frontend de forma segura.
 * </p>
 *
 * <p><strong>Ventajas del uso de DTOs:</strong></p>
 * <ul>
 *   <li>Seguridad: Oculta la contraseña y otros datos sensibles de la entidad</li>
 *   <li>Eficiencia: Reduce el tamaño de las respuestas al excluir información innecesaria</li>
 *   <li>Desacoplamiento: Separa la capa de persistencia de la capa de presentación</li>
 *   <li>Desnormalización: Incluye {@code nombreEquipo} para evitar consultas adicionales</li>
 * </ul>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.entities.Usuario
 */
public class UsuarioDTO {

    /** Identificador único del usuario */
    private Long id;

    /** Nombre del usuario */
    private String nombre;

    /** Apellidos del usuario */
    private String apellidos;

    /** Dirección de correo electrónico (único en el sistema) */
    private String email;

    /** Rol del usuario en el sistema (ADMIN, ENTRENADOR, JUGADOR) */
    private Rol rol;

    /** Posición de juego (solo aplicable a jugadores) */
    private Posicion posicion;

    /** Número de teléfono de contacto */
    private String telefono;

    /** URL de la foto de perfil almacenada en Cloudinary */
    private String fotoUrl;

    /** ID del equipo al que pertenece */
    private Long idEquipo;

    /** Nombre del equipo (desnormalizado para evitar consultas adicionales) */
    private String nombreEquipo;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String apellidos, String email,
                      Rol rol, Posicion posicion, String telefono,
                      String fotoUrl, Long idEquipo, String nombreEquipo) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.rol = rol;
        this.posicion = posicion;
        this.telefono = telefono;
        this.fotoUrl = fotoUrl;
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
    }

    // --- Getters y Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }
}
