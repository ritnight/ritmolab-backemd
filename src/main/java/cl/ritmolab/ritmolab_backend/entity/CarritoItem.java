package cl.ritmolab.ritmolab_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "carrito_items")
public class CarritoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // muchos items son de un carrito
    @ManyToOne
    @JoinColumn(name = "carrito_id")
    @JsonBackReference
    private Carrito carrito;

    // muchos items pueden ser del mismo producto
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;

    public CarritoItem() {}

    public CarritoItem(Long id, Carrito carrito, Producto producto, Integer cantidad) {
        this.id = id;
        this.carrito = carrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Carrito getCarrito() { return carrito; }
    public void setCarrito(Carrito carrito) { this.carrito = carrito; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
