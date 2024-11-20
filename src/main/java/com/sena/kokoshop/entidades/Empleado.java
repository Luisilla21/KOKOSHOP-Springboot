package com.sena.kokoshop.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class Empleado { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false,length = 20)
    private String nombre;

    @Column(name = "salario", nullable = false,length = 10)
    private Double salario;

    @Column(name = "horaEntrada", nullable = false,length = 5)
    private String horaEntrada;

    @Column(name = "horaSalida", nullable = false,length = 5)
    private String horaSalida;

    @Column(name = "horasTrabajadas", nullable = false,length = 10)
    private String horasTrabajadas;
    
    public Empleado(){
        
    }

    public Empleado(Long id, String nombre, Double salario, String horaEntrada, String horaSalida,
            String horasTrabajadas) {
        this.id = id;
        this.nombre = nombre;
        this.salario = salario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasTrabajadas = horasTrabajadas;
    }

    public Empleado(String nombre, Double salario, String horaEntrada, String horaSalida,
    String horasTrabajadas) {

       this.nombre = nombre;
       this.salario = salario;
       this.horaEntrada = horaEntrada;
       this.horaSalida = horaSalida;
       this.horasTrabajadas = horasTrabajadas;
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(String horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }
}
