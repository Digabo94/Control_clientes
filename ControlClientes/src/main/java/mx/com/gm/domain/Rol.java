package mx.com.gm.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name="rol")
public class Rol implements Serializable{
    
    private final static Long serialVersioUID = 1L;
    
    @Id
    /*Al ser una llave primaria auto incrementable en la base de datos, aca podemos usar una llave primaria auto incrementable*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;
    
    @NotEmpty
    private String nombre;
}
