package com.mindhub.homebanking.configurations;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration //La clase está anotada con @Configuration, indicando que es una clase de configuración de Spring.
// GlobalAuthenticationConfigurerAdapter es una clase proporcionada por Spring Security que permite personalizar la
// configuración global de autenticación.
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Autowired //inyeccion de dependencia
    ClientRepository clientRepository;
    @Override //Metodo de inicializacion. Este método se anula para proporcionar la configuración de autenticación.

    public void init(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inputName-> { //Configuracion del detalle del usuario. Se configura el userDetailsService, que se
            // encarga de cargar los detalles del usuario durante la autenticación. El método findByEmail del repositorio se
            // utiliza para obtener detalles del cliente a través del correo electrónico.

            Client client = clientRepository.findByEmail(inputName); //Creacion del objeto User. Si el cliente se encuentra en la base
            // de datos, se crea un objeto User de Spring Security con su correo electrónico, contraseña y roles. Si no se encuentra,
            // se lanza una excepción indicando que el usuario es desconocido.

            if (client != null) {

                return new User(client.getEmail(), client.getPassword(),

                        AuthorityUtils.createAuthorityList(client.getRole().toString()));

            } else {

                throw new UsernameNotFoundException("Unknown user");

            }

        });

    }
    @Bean //Configuracion del codificador de contraseñas. Se define un bean para proporcionar un codificador de contraseñas.
    // En este caso, se utiliza el DelegatingPasswordEncoder proporcionado por Spring Security.

    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

} //Este código configura Spring Security para manejar la autenticación de usuarios en una aplicación Spring Boot, utilizando
// un repositorio de clientes y un esquema de roles. Además, se utiliza un codificador de contraseñas para almacenar y
// verificar las contraseñas de los clientes de manera segura.

