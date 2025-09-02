package com.shop.CartIn.user;

import com.shop.CartIn.config.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController
{
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<String> getByMe(@AuthenticationPrincipal CustomUserDetails userDetails) {
        String username =  userDetails.getUsername();
        String authorities = userDetails.getAuthorities()
                .stream().
                map(auth -> auth.getAuthority())
                .reduce((a, b) -> a + ", " + b)
                .orElse("NO_ROLE");

        return ResponseEntity.ok(username + " " + authorities);
    }

}
