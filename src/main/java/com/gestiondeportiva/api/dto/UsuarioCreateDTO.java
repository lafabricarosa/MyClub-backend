package com.gestiondeportiva.api.dto;

import com.gestiondeportiva.api.entities.Posicion;
import com.gestiondeportiva.api.entities.Rol;

/**
 * DTO (Data Transfer Object) para crear nuevos usuarios en el sistema.
 * <p>
 * Utilizado en operaciones de registro y creación de usuarios. Incluye el campo
 * {@code password} en texto plano que será encriptado con BCrypt antes de
 * almacenarse en la base de datos. No incluye el ID ya que es autogenerado.
 * </p>
 *
 * <p><strong>Flujo de creación:</strong></p>
 * <ol>
 *   <li>El frontend envía este DTO con la contraseña en texto plano</li>
 *   <li>El servicio valida los datos y encripta la contraseña con BCrypt</li>
 *   <li>Se crea la entidad Usuario y se persiste en la base de datos</li>
 *   <li>Se devuelve un {@link UsuarioDTO} sin la contraseña al frontend</li>
 * </ol>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see com.gestiondeportiva.api.entities.Usuario
 * @see UsuarioDTO
 */
public class UsuarioCreateDTO {

    /** Nombre del usuario */
    private String nombre;

    /** Apellidos del usuario */
    private String apellidos;

    /** Dirección de correo electrónico (debe ser única en el sistema) */
    private String email;

    /** Contraseña en texto plano (será encriptada con BCrypt antes de persistir) */
    private String password;

    /** Rol del usuario (ADMIN, ENTRENADOR, JUGADOR) */
    private Rol rol;

    /** Posición de juego (obligatoria para jugadores, null para otros roles) */
    private Posicion posicion;

    /** Número de teléfono de contacto */
    private String telefono;

    /** ID del equipo al que se asignará el usuario */
    private Long idEquipo;


    public UsuarioCreateDTO() {
    }


    public UsuarioCreateDTO(String nombre, String apellidos, String email, String password, Rol rol, Posicion posicion,
            String telefono, Long idEquipo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.posicion = posicion;
        this.telefono = telefono;
        this.idEquipo = idEquipo;
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


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
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


    public Long getIdEquipo() {
        return idEquipo;
    }


    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    

    
}
