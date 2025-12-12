package com.gestiondeportiva.api.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Entidad JPA que representa a un usuario del sistema de gestión deportiva.
 * <p>
 * Un usuario puede tener uno de los siguientes roles:
 * <ul>
 *   <li>{@code ADMIN}: Acceso completo al sistema</li>
 *   <li>{@code ENTRENADOR}: Gestiona un equipo y sus jugadores</li>
 *   <li>{@code JUGADOR}: Miembro de un equipo con acceso limitado</li>
 * </ul>
 *
 * <p>Los jugadores tienen asociada una posición en el campo (portero, defensa,
 * centrocampista, delantero) y pertenecen a un equipo. Los usuarios están relacionados
 * con convocatorias, cuotas, disponibilidades y estadísticas.</p>
 *
 * <p>La contraseña del usuario se almacena cifrada con BCrypt y nunca se expone en
 * las respuestas JSON gracias a la anotación {@code @JsonProperty(access = WRITE_ONLY)}.</p>
 *
 * @author Sistema de Gestión Deportiva MyClub
 * @version 1.0
 * @see Rol
 * @see Posicion
 * @see Equipo
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    /** Identificador único del usuario */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del usuario (obligatorio) */
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    /** Apellidos del usuario (obligatorio) */
    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    private String apellidos;

    /**
     * Email del usuario (obligatorio, único).
     * Se utiliza como username en la autenticación JWT.
     */
    @Column(nullable = false, unique = true)
    @Email(message = "El email debe tener un formato válido")
    @NotBlank(message = "El email no puede estar vacío")
    private String email;

    /**
     * Contraseña cifrada del usuario (obligatoria).
     * Se cifra con BCrypt y nunca se expone en respuestas JSON.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "La contraseña no puede ser nula")
    private String password;

    /** Rol del usuario en el sistema (ADMIN, ENTRENADOR, JUGADOR) */
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Debe indicar un rol para el usuario")
    private Rol rol;

    /** Posición del jugador en el campo (solo para rol JUGADOR) */
    @Enumerated(EnumType.STRING)
    private Posicion posicion;

    /** Número de teléfono del usuario (9-15 dígitos) */
    @Pattern(regexp = "\\d{9,15}", message = "El teléfono debe tener entre 9 y 15 dígitos")
    private String telefono;

    /** URL de la foto de perfil almacenada en Cloudinary */
    @Column(name = "foto_url")
    private String fotoUrl;

    /** Equipo al que pertenece el usuario (null para ADMIN) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_equipo")
    @JsonIgnoreProperties("jugadores")
    private Equipo equipo;

    /** Convocatorias en las que participa el jugador */
    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("jugador")
    private Set<Convocatoria> convocatorias;

    /** Cuotas de pago asociadas al jugador */
    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("jugador")
    private Set<Cuota> cuotas;

    /** Disponibilidades del jugador para eventos */
    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("jugador")
    private Set<Disponibilidad> disponibilidades;

    /** Estadísticas del jugador en eventos */
    @OneToMany(mappedBy = "jugador", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("jugador")
    private Set<Estadistica> estadisticas;

    /**
     * Constructor por defecto que inicializa las colecciones vacías.
     */
    public Usuario() {
        this.convocatorias = new HashSet<>();
        this.cuotas = new HashSet<>();
        this.disponibilidades = new HashSet<>();
        this.estadisticas = new HashSet<>();
    }

    /**
     * Constructor con todos los campos.
     *
     * @param nombre Nombre del usuario
     * @param apellidos Apellidos del usuario
     * @param email Email del usuario (único en el sistema)
     * @param password Contraseña (se debe cifrar antes de persistir)
     * @param rol Rol del usuario en el sistema
     * @param posicion Posición del jugador en el campo
     * @param telefono Número de teléfono
     * @param fotoUrl URL de la foto de perfil
     * @param equipo Equipo al que pertenece
     */
    public Usuario(String nombre, String apellidos, String email, String password, Rol rol, Posicion posicion,
            String telefono, String fotoUrl, Equipo equipo) {
        this();
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.posicion = posicion;
        this.telefono = telefono;
        this.fotoUrl = fotoUrl;
        this.equipo = equipo;
    }

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

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public Set<Convocatoria> getConvocatorias() {
        return convocatorias;
    }

    public void setConvocatorias(Set<Convocatoria> convocatorias) {
        this.convocatorias = convocatorias;
    }

    public Set<Cuota> getCuotas() {
        return cuotas;
    }

    public void setCuotas(Set<Cuota> cuotas) {
        this.cuotas = cuotas;
    }

    public Set<Disponibilidad> getDisponibilidades() {
        return disponibilidades;
    }

    public void setDisponibilidades(Set<Disponibilidad> disponibilidades) {
        this.disponibilidades = disponibilidades;
    }

    public Set<Estadistica> getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(Set<Estadistica> estadisticas) {
        this.estadisticas = estadisticas;
    }

    /*public Estadistica asignarEstadistica(Estadistica estadistica) {
        estadisticas.add(estadistica);
        estadistica.setJugador(this);
        return estadistica;
    }

    public void eliminarEstadistica(Estadistica estadistica) {
        this.estadisticas.remove(estadistica);
        estadistica.setJugador(null);
    }

    public Disponibilidad asignarDisponibilidad(Disponibilidad disponibilidad) {
        disponibilidades.add(disponibilidad);
        disponibilidad.setJugador(this);
        return disponibilidad;
    }

    public void eliminarDisponibilidad(Disponibilidad disponibilidad) {
        this.disponibilidades.remove(disponibilidad);
        disponibilidad.setJugador(null);
    }

    public Cuota asignarCuota(Cuota cuota) {
        cuotas.add(cuota);
        cuota.setJugador(this);
        return cuota;
    }

    public void eliminarCuota(Cuota cuota) {
        this.cuotas.remove(cuota);
        cuota.setJugador(null);
    }

    public Equipo asignarEquipo(Equipo equipo) {
        this.setEquipo(equipo);
        equipo.getJugadores().add(this);
        return equipo;
    }

    public void eliminarEquipo(Equipo equipo) {
        equipo.setEntrenador(null);
        this.equipo = null;
    }

    public Convocatoria asignarConvocatoria(Convocatoria convocatoria) {
        convocatorias.add(convocatoria);
        convocatoria.setJugador(this);
        return convocatoria;
    }

    public void eliminarConvocatoria(Convocatoria convocatoria) {
        this.convocatorias.remove(convocatoria);
        convocatoria.setJugador(null);
    }*/

    @Override
    public String toString() {
        return "Usuario{id=" + id +
                ", nombre='" + nombre + '\'' +
                ", rol=" + rol +
                ", equipo=" + (equipo != null ? equipo.getNombre() : "N/A") + 
                ", convocatorias=" + (convocatorias != null ? convocatorias.size() : 0) +
                ", cuotas=" + (cuotas != null ? cuotas.size() : 0) +"}";
                
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

}
