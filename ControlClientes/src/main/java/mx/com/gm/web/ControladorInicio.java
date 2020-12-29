package mx.com.gm.web;

//Se agrega la siguiente anotacion para que Spring pueda reconocer esta clase de java
import java.util.*;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import mx.com.gm.domain.Persona;
import mx.com.gm.servicio.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
//Vamos a usar un controlador Spring MVC

@Controller
//La siguiente anotacion es para un login
@Slf4j
public class ControladorInicio {

    //Autowired se usa para inyectar nuestra interfaz PersonaDao en esta clase controlador, y la interfaz al extender
    //de la clase CrudRepository, esta clase tendra los permisos de usar los metodos para obtener y modificar
    //la base de datos
    @Autowired
    private PersonaService personaService;

    @GetMapping("/")
    /*Con la anotacion @AuthenticalPrincipal recuperamos el usuario que hizo login en nuestra aplicacion
    y lo inyecta dentro de User*/
    public String inicio(Model model, @AuthenticationPrincipal User user) {
        
        List<Persona> personas = personaService.listarPersona();
        log.info("Ejecutando el controlador Spring MVC");
        log.info("Usuario que hizo login:" + user);
        model.addAttribute("personas", personas);
        return "index";
    }

    //Aca en el parametro del metodo estamos inyectando directamente un objeto Persona
    @GetMapping("/agregar")
    public String agregar(Persona persona) {

        return "modificar";
    }

    @PostMapping("/guardar")
    //Con solo definir como argumento el objeto persona en el parametro del metodo Spring entiende
    //que es el objeto enviado por el formulario
    //Tambien la anotacion de @Valid permite que primeramente valide el objeto
    //y tambien pasamos el parametro de errors para manejar el error en una variable
    public String guardar(@Valid Persona persona, Errors errores) {

        //Se valida si la variable errores contiene errores, si es true (si contiene errores) se redirecciona a
        //modificar
        if (errores.hasErrors()) {
            return "modificar";
        }
        personaService.guardar(persona);
        //Con esta sintaxis de return, estamos redireccionando a la pagina de inicio cuando el usuario de click al boton
        // de Guardar
        return "redirect:/";
    }

    //Aqui se esta mapeando la opcion de editar un registro de la tabla, esto se agrega de la siguiente manera
    //la cual es: la ruta (editar) y el atributo a editar (idPersona). EL atributo hay que escribirlo exactamente
    //como lo tenemos nombrado en el codigo para que se asocien
    @GetMapping("/editar/{idPersona}")
    //Aqui estamos inyectando el objeto de persona, y el valor del atributo de idPersona lo asociara a ese objeto
    //y se agrega el objeto Model para asi poder hacer uso de este objeto en posteriores usos
    public String editar(Persona persona, Model model) {
        persona = personaService.encontrarPersona(persona);
        model.addAttribute("persona", persona);
        return "modificar";
    }

    //Ya no requiere agregar al path el idPersona ya que esta inherente en borra gracias al query param 
    @GetMapping("/borrar")
    public String borrar(Persona persona) {
        personaService.eliminar(persona);
        return "redirect:/";
    }
}
