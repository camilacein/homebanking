package com.mindhub.homebanking.configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity //Se inicia una clase de configuracion de seguridad con estas anotaciones
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception { //Configura la cadena de filtros de seguridad.
        // Se realiza utilizando un objeto httpsecurity que se pasa como parametro al metodo
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/index.html", "/index.js", "/tailwind.config.js", "/images/**","style.css").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .requestMatchers("/api/clients/current", "/assets/**", "/api/loans" ).hasAuthority("CLIENT")
                .requestMatchers(HttpMethod.POST,"/api/clients/current/accounts", "/api/clients/current/cards", "/api/transactions", "/api/loans").hasAuthority("CLIENT")
                .requestMatchers("/h2-console/**").hasAuthority("ADMIN")
                .anyRequest().denyAll()); //Se configuran las reglas de autorización para diferentes rutas. Algunas rutas son
        // permitidas sin autenticación (permitAll), algunas requieren ciertos roles (hasAuthority), y otras se niegan (denyAll).
        // Por ejemplo, se permite el acceso a archivos estáticos como CSS e imágenes, se permite la creación de nuevos clientes
        // a través de una solicitud POST, se requiere el rol "CLIENT" para algunas rutas y el rol "ADMIN" para acceder a la
        // consola H2.
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()); //Se deshabilita la protección contra CSRF (Cross-Site Request Forgery).
        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(
                frameOptionsConfig -> frameOptionsConfig.disable()));//Se deshabilitan las opciones del marco para protección
        // contra ataques de clicjacking.
        http.formLogin(formLogin -> formLogin.loginPage("/index.html") //Se configura el formulario de inicio de sesión, especificando la página
                // de inicio de sesión, la URL de procesamiento de inicio de sesión, los parámetros de nombre de usuario y contraseña,
                // manejadores de éxito y fracaso, y se permite el acceso a la página de inicio de sesión sin autenticación.
                .loginProcessingUrl("/api/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> clearAuthenticationAttributes(request))
                .failureHandler((request, response, exception) -> response.sendError(403))
                .permitAll()
        );
        http.exceptionHandling( exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException)
                -> response.sendError(401)) ); //Se configura el manejo de excepciones, especificando que se envíe un error 401 en
        // caso de fallo de autenticación.
        http.logout(httpSecurityLogoutConfigurer -> //Se configura la funcionalidad de cierre de sesión, especificando la URL de cierre de sesión,
                // el manejador de éxito y la eliminación de cookies.
                httpSecurityLogoutConfigurer

                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                        .deleteCookies("JSESSIONID"));
        http.rememberMe(Customizer.withDefaults()); //Se configura la funcionalidad "Recuérdame" con la configuración predeterminada.

        return http.build();



    }
    private void clearAuthenticationAttributes(HttpServletRequest request) { //Se define un método privado (clearAuthenticationAttributes) que
        // elimina los atributos de autenticación de la sesión.

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }// Configura la seguridad de una aplicación Spring Boot utilizando Spring Security.
    // Define reglas de autorización para diferentes rutas, deshabilita la protección CSRF,
    // maneja la autenticación a través de un formulario personalizado, configura el manejo de excepciones y la
    // funcionalidad de cierre de sesión, y proporciona la configuración predeterminada para la funcionalidad "Recuérdame".
}
