package com.prueba.usuarios.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotEmpty(message = " no puede estar vacio")
    @Size(min = 4, max = 20, message = " El largo debe ser entre 4 y 12 caracteres")
    @Column(length = 20)
    private String name;

    @NotEmpty(message = " no puede estar vacio")
    @Size(min = 4, max = 60, message = " El largo debe ser entre 4 y 60 caracteres")
    @Column(length = 60)
    private String password;

    @NotEmpty(message = " no puede estar vacio")
    @Email(message = " debe tener un formato valido")
    @Column(nullable = false, unique = true)
    private String email;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "usuarios_phones", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "phone_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "phone_id"})})
    private Set<Telefono> phones;

    @Column(name = "isActive")
    private Boolean isActive;

    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date fecha_creacion;

    @Column(name = "modified")
    @Temporal(TemporalType.DATE)
    private Date fecha_ult_actualizacion;

    @Column(name = "last_login")
    @Temporal(TemporalType.DATE)
    private Date last_login;

    @Column(name = "token")
    private String token;

}
