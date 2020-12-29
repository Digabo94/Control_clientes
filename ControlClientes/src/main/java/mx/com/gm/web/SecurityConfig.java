package mx.com.gm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*Para poder realizar la configuracion de la seguridad, es necesario agregar las siguientes
anotaciones como: Configuration, que esta anotacion permite crear una clase configuracion
y la anotacion EnableWebSecurity, la cual activa la seguridad web, aparte de extender
de la clase WebSecurityConfigurerAdapter*/
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    
    /*Una instancia de UsuarioService se inyectara aqui, ya que en la anotacion service de dicha clase
    se le denomino como userDetailsService, entonces autowired es la clase que tomara, incluyendo el metodo
    de loadUserByUsername, el cual se encuentra dentro de dicha clase*/
    @Autowired
    private UserDetailsService userDetailsService;
    
    /*Estamos definiendo al metodo como bean, ya que regresara un objeto del tipo BCryptPasswordEncoder y estara 
    disponible dentro del contenedor de Spring solo con agregar dicha anotacion*/
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    /*AuthenticationManagerBuilder es un objeto que ya se encuentra definido dentro de la fabrica de Spring Security,
    y con el simple hecho de poner la anotacion Autowired, podemos implementar dicho objeto*/
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder build) throws Exception{
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    
    /*Aca restringiremos las URL a los usuarios*/
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        /*Aca estamos limitando el acceso a los usuarios. Con **
        le estamos indicando que son todos los usuarios contenidos*/
        http.authorizeRequests()
                .antMatchers("/editar/**", "/agregar/**", "/borrar")
                /*Le indicamos que solo el admin tiene acceso a estos sitios
                editar, agregar y eliminar*/
                .hasRole("ADMIN")
                /*Aca le indicamos que cualquier rol escrito, user y admin
                pueden acceder a la direccion / donde aparecen todos los usuarios*/
                .antMatchers("/")
                .hasAnyRole("USER", "ADMIN")
                /*Aca agregaremos el login que creamos en HTML*/
                .and()
                .formLogin()
                .loginPage("/login")
                /*Aca agregaremos la pagina de error 403*/
                .and()
                .exceptionHandling()
                .accessDeniedPage("/errores/403")
                ;
    }
}
