package cl.ritmolab.ritmolab_backend.service;

import cl.ritmolab.ritmolab_backend.entity.*;
import cl.ritmolab.ritmolab_backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final PedidoItemRepository pedidoItemRepo;
    private final CarritoRepository carritoRepo;
    private final UsuarioRepository usuarioRepo;

    public PedidoService(PedidoRepository pedidoRepo,
                         PedidoItemRepository pedidoItemRepo,
                         CarritoRepository carritoRepo,
                         UsuarioRepository usuarioRepo) {
        this.pedidoRepo = pedidoRepo;
        this.pedidoItemRepo = pedidoItemRepo;
        this.carritoRepo = carritoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @Transactional
    public Pedido crearPedidoDesdeCarrito(Long usuarioId) {
        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        Carrito carrito = carritoRepo.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new RuntimeException("Carrito vacío"));

        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        Pedido pedido = new Pedido(usuario);
        pedidoRepo.save(pedido);

        for (CarritoItem item : carrito.getItems()) {
            PedidoItem pi = new PedidoItem(
                    pedido,
                    item.getProducto(),
                    item.getCantidad(),
                    item.getProducto().getPrecio()
            );
            pedidoItemRepo.save(pi);
            pedido.getItems().add(pi);
        }

        // vaciar carrito
        carrito.getItems().clear();
        carritoRepo.save(carrito);

        return pedido;
    }

    public List<Pedido> getPedidosUsuario(Long usuarioId) {
        return pedidoRepo.findByUsuarioId(usuarioId);
    }

    public Pedido cambiarEstado(Long pedidoId, EstadoPedido estado) {
        Pedido pedido = pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no existe"));
        pedido.setEstado(estado);
        return pedidoRepo.save(pedido);
    }
}
