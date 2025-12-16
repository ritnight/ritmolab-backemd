package cl.ritmolab.ritmolab_backend.repository;

import cl.ritmolab.ritmolab_backend.entity.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
}
