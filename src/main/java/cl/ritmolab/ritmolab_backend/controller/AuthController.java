package cl.ritmolab.ritmolab_backend.controller;

import cl.ritmolab.ritmolab_backend.dto.LoginRequest;
import cl.ritmolab.ritmolab_backend.dto.LoginResponse;
import cl.ritmolab.ritmolab_backend.entity.Usuario;
import cl.ritmolab.ritmolab_backend.repository.UsuarioRepository;
import cl.ritmolab.ritmolab_backend.security.JwtService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
        "http://3.227.171.106",
        "http://localhost:5173"
})
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService,
                          UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Generar token con username/email desde auth
        String token = jwtService.generateToken((org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal());

        // Info extra para el front
        Usuario u = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();

        LoginResponse resp = new LoginResponse(
                token,
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                "ROLE_" + u.getRol().name()
        );

        return ResponseEntity.ok(resp);
    }
}
