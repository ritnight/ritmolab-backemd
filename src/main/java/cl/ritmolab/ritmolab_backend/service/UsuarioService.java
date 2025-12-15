package cl.ritmolab.ritmolab_backend.service;

import cl.ritmolab.ritmolab_backend.entity.Rol;
import cl.ritmolab.ritmolab_backend.entity.Usuario;
import cl.ritmolab.ritmolab_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow();
    }

    public Usuario create(Usuario usuario) {
        // Forzar rol cliente
        usuario.setRol(Rol.CLIENTE);

        // Hashear password antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
           throw new RuntimeException("Email ya registrado");
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario cambios) {
        Usuario u = usuarioRepository.findById(id).orElseThrow();
        u.setNombre(cambios.getNombre());
        u.setEmail(cambios.getEmail());

        if (cambios.getPassword() != null && !cambios.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(cambios.getPassword()));
        }

        return usuarioRepository.save(u);
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}
