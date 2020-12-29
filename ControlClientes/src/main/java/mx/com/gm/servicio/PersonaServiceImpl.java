package mx.com.gm.servicio;

import mx.com.gm.dao.PersonaDao;
import java.util.List;
import mx.com.gm.domain.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//Esta notacion es para poder inyectar esta clase como una implementacion de la interfaz PersonaService dentro del 
//controlador de Spring
@Service
public class PersonaServiceImpl implements PersonaService{
    
    @Autowired
    private PersonaDao personaDao;
    
    @Override
    //Con esta notacion le estamos diciendo que el metodo de listar persona es solo para leer la base de datos
    @Transactional(readOnly = true)
    public List<Persona> listarPersona() {
        return (List<Persona>)personaDao.findAll();
    }

    
    @Override
    //No se le indica readOnly porque aqui si modificara la base de datos
    @Transactional
    public void guardar(Persona persona) {
        //Con este metodo guardamos un objeto persona en la base de datos 
        personaDao.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Persona persona) {
        //Eliminar de la base de datos un objeto persona
        personaDao.delete(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona encontrarPersona(Persona persona) {
        //Este metodo recupera un objeto de tipo persona dandole la llave primaria de la clase, en este caso el ID
        //y con el metodo orElse, en caso de que no encuentre el objeto, devolvera nulo
     return personaDao.findById(persona.getIdPersona()).orElse(null);
    }
    
}
