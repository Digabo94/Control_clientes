package mx.com.gm.servicio;

import lombok.extern.slf4j.Slf4j;
import mx.com.gm.dao.UsuarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import mx.com.gm.domain.Usuario;
import mx.com.gm.domain.Rol;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

/*Para que esta clase la pueda utilizar Spring Security, es necesario a la anotacion service poner userDetailsService para que
la reconozca*/
@Service("userDetailsService")
@Slf4j
/*Es necesario que tambien implemente UserDetailsService para que Spring Security reconozca esta clase*/
public class UsuarioService implements UserDetailsService{

    @Autowired
    private UsuarioDao usuarioDao;
    
    @Override
    /*Se agrega la transaccion para que Spring pueda realizar la sesion de CRUD*/
    @Transactional(readOnly=true)
    /*Se implementa el metodo de la interfaz, el cual te recupera un usuario con el nombre del username dado*/
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);
        
        if(usuario == null){
            /*Si no encuentra nada en la tabla de usuario, entonces arrojara una excepcion como es la siguiente*/
            throw new UsernameNotFoundException(username);
        }
        /*Se hara un tipo de lista GrantedAuthority, ya que ese es el tipo de lista que necesita Spring para manejar los roles*/
        ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        
        for(Rol rol : usuario.getRoles()){
            /*se agregara al arraylist un elemente GrantedAuthority, por ello en el metodo add
            se creara un objeto que implemente GrantedAuthority, en este caso, SimpleGrantedAuthority,
            el cual obtendra como parametro un objeto rol*/
            roles.add(new SimpleGrantedAuthority(rol.getNombre()));
        }
        /*Se regresara un objeto usuario con el  nombre del usuario, el password y los roles del usuario*/
        return new User(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
