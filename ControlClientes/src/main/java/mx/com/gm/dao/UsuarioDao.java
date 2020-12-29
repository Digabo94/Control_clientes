package mx.com.gm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.com.gm.domain.Usuario;

/*Aca en vez de usar la clase CrudRepository se usar JpaRepository ya que tiene mejoras respecto a Crud*/
public interface UsuarioDao extends JpaRepository<Usuario, Long>{
    /*Por el framework de spring, es necesario crear un metodo findByUsername para recuperar, en este caso, un usuario*/
    Usuario findByUsername(String username);
}
