package cl.ritmolab.ritmolab_backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String artista;

    @Column(length = 1000)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String imagenUrl;

    private String imagenAlt;

    private String tipo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    public Producto() {}

    // Constructor sin id (recomendado)
    public Producto(String titulo, String artista, String descripcion, BigDecimal precio,
                    Integer stock, String imagenUrl, String imagenAlt,
                    String tipo, Categoria categoria) {
        this.titulo = titulo;
        this.artista = artista;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagenUrl = imagenUrl;
        this.imagenAlt = imagenAlt;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    // Getters/Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public String getImagenUrl() { return imagenUrl; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    public String getImagenAlt() { return imagenAlt; }
    public void setImagenAlt(String imagenAlt) { this.imagenAlt = imagenAlt; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
