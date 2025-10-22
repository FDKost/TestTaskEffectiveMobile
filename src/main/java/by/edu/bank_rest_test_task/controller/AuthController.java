package by.edu.bank_rest_test_task.controller;

import by.edu.bank_rest_test_task.dto.auth.AuthRequest;
import by.edu.bank_rest_test_task.dto.owner.OwnerCreateEditDTO;
import by.edu.bank_rest_test_task.dto.owner.OwnerReadDTO;
import by.edu.bank_rest_test_task.exception.AuthIOException;
import by.edu.bank_rest_test_task.security.JwtUtil;
import by.edu.bank_rest_test_task.service.owner.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "auth", description = "Регистрация/логин")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final OwnerService ownerService;

    @Operation(summary = "Регистрация пользователя",
            description = "Возвращает пользователя если успешно")
    @PostMapping("/register")
    public String register(@RequestBody AuthRequest authRequest) {
        if (ownerService.findByUsername(authRequest.getUsername()).isPresent()) {
            throw new AuthIOException("Username is already taken");
        }
        OwnerCreateEditDTO dto = new OwnerCreateEditDTO(authRequest.getUsername(),
                authRequest.getPassword(), "USER");
        OwnerReadDTO resultDto = ownerService.create(dto);
        return resultDto.toString();
    }

    @Operation(summary = "Вход в систему",
            description = "Возвращает JWT токен пользователя")
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                        authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authRequest.getUsername());
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
