package cl.ritmolab.ritmolab_backend.controller;

import cl.ritmolab.ritmolab_backend.entity.Categoria;
import cl.ritmolab.ritmolab_backend.entity.Producto;
import cl.ritmolab.ritmolab_backend.repository.CategoriaRepository;
import cl.ritmolab.ritmolab_backend.repository.ProductoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = {
        "http://3.227.171.106",
        "http://localhost:5173"
})
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoController(ProductoRepository productoRepository,
                              CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // GET /api/productos
    @GetMapping
    public List<Producto> listar() {
        return productoRepository.findAll();
    }

    // GET /api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        return productoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/productos  (SOLO ADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crear(@RequestBody Producto producto) {

        // Validar categoría
        if (producto.getCategoria() == null || producto.getCategoria().getId() == null) {
            return ResponseEntity.badRequest().body("Debes enviar categoria: {\"id\": <id> }");
        }

        Long categoriaId = producto.getCategoria().getId();
        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);

        if (categoria == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("La categoría con id " + categoriaId + " no existe");
        }

        producto.setCategoria(categoria);

        // imagenAlt por defecto si no viene
        if (producto.getImagenAlt() == null || producto.getImagenAlt().isBlank()) {
            producto.setImagenAlt("Imagen de " + producto.getTitulo());
        }

        Producto guardado = productoRepository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // PUT /api/productos/{id} (SOLO ADMIN)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Producto cambios) {

        return productoRepository.findById(id).map(producto -> {

            producto.setTitulo(cambios.getTitulo());
            producto.setArtista(cambios.getArtista());
            producto.setDescripcion(cambios.getDescripcion());
            producto.setPrecio(cambios.getPrecio());
            producto.setStock(cambios.getStock());
            producto.setImagenUrl(cambios.getImagenUrl());
            producto.setImagenAlt(cambios.getImagenAlt());
            producto.setTipo(cambios.getTipo());

            // Actualizar categoría si viene
            if (cambios.getCategoria() != null && cambios.getCategoria().getId() != null) {
                Long categoriaId = cambios.getCategoria().getId();
                Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);

                if (categoria == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("La categoría con id " + categoriaId + " no existe");
                }
                producto.setCategoria(categoria);
            }

            // imagenAlt por defecto si queda vacío
            if (producto.getImagenAlt() == null || producto.getImagenAlt().isBlank()) {
                producto.setImagenAlt("Imagen de " + producto.getTitulo());
            }

            Producto actualizado = productoRepository.save(producto);
            return ResponseEntity.ok(actualizado);

        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/productos/{id} (SOLO ADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!productoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
