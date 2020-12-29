package mx.com.gm.dao;

import org.springframework.data.repository.CrudRepository;
import mx.com.gm.domain.Persona;


//Extiende de la clase CrudRepository<>, la cual se encarga de crear los metodos select, update, delete, etc...
//En los parentesis en pico va, el tipo de objeto que manejara, en este caso de tipo Persona y despues
//el tipo de la llave primaria, en este caso idPersona es tipo Long
public interface PersonaDao extends CrudRepository<Persona, Long>{
    
    
}
