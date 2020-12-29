package mx.com.gm.domain;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import mx.com.gm.domain.Rol;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {
    
    private final static Long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @NotEmpty
    private String username;
    
    @NotEmpty
    private String password;
    
    /*Esta notacion se usa, ya que se hara el mapeo de id_usuario en la tabla de rol, y un usuario puede
    tener varios roles, entonces se usa OneToMany*/
    @OneToMany
    /*Aca le indicamos cual columna es la que hara la relacion entre las tablas*/
    @JoinColumn(name="id_usuario")
    private List<Rol> roles;
}
