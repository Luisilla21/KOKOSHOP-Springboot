package com.sena.crudempleados;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudempleadosApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CrudempleadosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		/* 
		Empleado empleado1 = new Empleado("luisa", 1.200000, "8am", "5pm", "8 horas");
		repositorio.save(empleado1);

		Empleado empleado2 = new Empleado("yuliana", 1.500000, "8am", "3pm", "6 horas");
		repositorio.save(empleado2);
		*/
	}

}
