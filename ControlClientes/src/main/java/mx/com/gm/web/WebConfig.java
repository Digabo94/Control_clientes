package mx.com.gm.web;
//Esta clase tiene como fin configurar la internalizacion para que aparezca en diferentes idiomas nuestra pagina

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//Es necesario agregar la siguiente notacion para poder convertir esta clase en un Bean
@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    //Se creara un metodo, el cual creara una instancia de un objeto el cual sera agregado al contenedor de Spring gracias
    //a la anotacion de Bean
    @Bean
    public LocaleResolver localeResolver(){
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("es"));
        return slr;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }
    
    @Override
    //Este metodo es para cambiar dinamicamente nuestro lenguaje
    public void addInterceptors(InterceptorRegistry registro){
        registro.addInterceptor(localeChangeInterceptor());
    }
    
    
    /*Aca estamos momdificando las paginas que visualizara el usuario cuando no tenga
    los permisos para acceder a una URL*/
    @Override
    public void addViewControllers(ViewControllerRegistry registro){
        /*el metodo addViewController se usa para mapear paths que no
        necesariamente pasan por el controlador*/
        registro.addViewController("/").setViewName("index");
        registro.addViewController("/login");
        registro.addViewController("/errores/403").setViewName("/errores/403")
                ;
    }
}
