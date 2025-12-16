package cl.ritmolab.ritmolab_backend.controller;

import cl.ritmolab.ritmolab_backend.entity.EstadoPedido;
import cl.ritmolab.ritmolab_backend.entity.Pedido;
import cl.ritmolab.ritmolab_backend.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = {
        "http://3.227.171.106",
        "http://localhost:5173"
})
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // CLIENTE: pagar carrito
    @PostMapping("/usuario/{usuarioId}")
    public Pedido crearPedido(@PathVariable Long usuarioId) {
        return pedidoService.crearPedidoDesdeCarrito(usuarioId);
    }

    // CLIENTE: ver sus pedidos
    @GetMapping("/usuario/{usuarioId}")
    public List<Pedido> pedidosUsuario(@PathVariable Long usuarioId) {
        return pedidoService.getPedidosUsuario(usuarioId);
    }

    // ADMIN: cambiar estado
    @PutMapping("/{pedidoId}/estado")
    public Pedido cambiarEstado(@PathVariable Long pedidoId,
                                @RequestParam EstadoPedido estado) {
        return pedidoService.cambiarEstado(pedidoId, estado);
    }
}
