package cl.ritmolab.ritmolab_backend.repository;

import cl.ritmolab.ritmolab_backend.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
