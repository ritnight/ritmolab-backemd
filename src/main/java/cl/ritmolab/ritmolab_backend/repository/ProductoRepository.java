package cl.ritmolab.ritmolab_backend.repository;

import cl.ritmolab.ritmolab_backend.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
