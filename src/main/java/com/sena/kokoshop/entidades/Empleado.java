package com.sena.kokoshop.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmp;

    @OneToOne
    @JoinColumn(name = "idUser") // Foreign Key
    private Usuario usuario;

    @Column(name = "salario", nullable = false, length = 10)
    private Double salario;

    @Column(name = "horaEntrada", nullable = false, length = 5)
    private String horaEntrada;

    @Column(name = "horaSalida", nullable = false, length = 5)
    private String horaSalida;

    @Column(name = "horasTrabajadas", nullable = false, length = 10)
    private String horasTrabajadas;

    public Empleado() {

    }

    public Empleado(Long idEmp, Usuario usuario, Double salario, String horaEntrada, String horaSalida,
            String horasTrabajadas) {
        this.idEmp = idEmp;
        this.usuario = usuario;
        this.salario = salario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasTrabajadas = horasTrabajadas;
    }

    public Empleado(Usuario usuario, Double salario, String horaEntrada, String horaSalida,
            String horasTrabajadas) {

        this.usuario = usuario;
        this.salario = salario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasTrabajadas = horasTrabajadas;
    }

    public Long getId() {
        return idEmp;
    }

    public void setId(Long idEmp) {
        this.idEmp = idEmp;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getUserId() {
        return usuario.getUsuarioID();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Empleado{");
        sb.append("idEmp=").append(idEmp);
        sb.append(", usuario=").append(usuario.getNombre());
        sb.append(", salario=").append(salario);
        sb.append(", horaEntrada=").append(horaEntrada);
        sb.append(", horaSalida=").append(horaSalida);
        sb.append(", horasTrabajadas=").append(horasTrabajadas);
        sb.append('}');
        return sb.toString();
    }

}
