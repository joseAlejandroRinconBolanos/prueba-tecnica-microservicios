package com.microservicios.servicio_inventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.microservicios.servicio_inventario.Client")
public class ServicioInventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioInventarioApplication.class, args);
	}

}
