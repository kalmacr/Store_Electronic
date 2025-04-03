package com.tienda;

import com.tienda.domain.Ruta;
import com.tienda.service.RutaPermitService;
import com.tienda.service.RutaService;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class ProyectConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.getDefault());
        slr.setLocaleAttributeName("session.current.locale");
        slr.setTimeZoneAttributeName("session.current.timezone");
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registro) {
        registro.addInterceptor(localeChangeInterceptor());
    }

    //Metodo para publicar en la nube, independientemente
    @Bean
    public SpringResourceTemplateResolver templateResolver_0() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setOrder(0);
        resolver.setCheckExistence(true);
        return resolver;
    }

    /* Los siguiente métodos son para implementar el tema de seguridad dentro del proyecto */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registro/nuevo").setViewName("/registro/nuevo");
    }
    @Autowired
    private RutaPermitService rutaPermitService;

    @Autowired
    private RutaService rutaService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        String[] rutaPermit = rutaPermitService.getRutaPermitsString();
        // Obtener lista de rutas dinámicas y sus roles asociados desde rutaService
        List<Ruta> rutas = rutaService.getRutas();

        http // Configurar reglas de autorización
                .authorizeHttpRequests((request) -> {
                    // Permitir acceso público a las rutas definidas en 'rutaPermit'
                    request.requestMatchers(rutaPermit).permitAll();
                    // Configurar rutas dinámicas para requerir roles específicos
                    for (Ruta ruta : rutas) {
                        request.requestMatchers(ruta.getPatron())
                                .hasRole(ruta.getRolName());// Asegurar que el usuario tenga el rol necesario

                    }
                }
                )// Configurar página de inicio de sesión
                .formLogin((form) -> form
                .loginPage("/login")// Especificar la URL personalizada para el inicio de sesión
                .permitAll()) // Permitir acceso público a la página de inicio de sesión

                .logout((logout) -> logout // Configurar cierre de sesión 
                .permitAll());// Permitir a cualquier usuario cerrar sesión

        return http.build();
    }
    /* El siguiente método se utiliza para completar la clase no es 
    realmente funcional, la próxima semana se reemplaza con usuarios de BD */
//    @Bean
//    public UserDetailsService users() {
//        UserDetails admin = User.builder()
//                .username("juan")
//                .password("{noop}123")
//                .roles("USER", "VENDEDOR", "ADMIN")
//                .build();
//        UserDetails sales = User.builder()
//                .username("rebeca")
//                .password("{noop}456")
//                .roles("USER", "VENDEDOR")
//                .build();
//        UserDetails user = User.builder()
//                .username("pedro")
//                .password("{noop}789")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, sales, admin);
//    }
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build)
            throws Exception {
    // Configurar el servicio de detalles de usuario (UserDetailsService)
    // Esto permite a Spring Security obtener los detalles de autenticación de userDetailsService
    
        build.userDetailsService(userDetailsService)
    // Configurar el codificador de contraseñas (passwordEncoder)
    // Se utiliza BCrypt para codificar y verificar contraseñas de manera segura
                    
                .passwordEncoder(new BCryptPasswordEncoder());

    }
}
