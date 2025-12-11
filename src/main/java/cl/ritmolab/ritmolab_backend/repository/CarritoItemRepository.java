package cl.ritmolab.ritmolab_backend.repository;

import cl.ritmolab.ritmolab_backend.entity.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
}
