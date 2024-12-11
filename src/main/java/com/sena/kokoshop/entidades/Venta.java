package com.sena.kokoshop.entidades;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVenta;

    @Column(nullable = false)
    private Float precioTatal;

    @Column(nullable = false)
    private Date fechaVenta;

    @Column(nullable = false)
    private String tipoVenta;

    @Column(nullable = false)
    private String estadoVenta;
}
