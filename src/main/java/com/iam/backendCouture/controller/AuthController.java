package com.iam.backendCouture.controller;
import com.iam.backendCouture.entities.ERole;
import com.iam.backendCouture.entities.Role;
import com.iam.backendCouture.entities.User;
import com.iam.backendCouture.payload.request.LoginRequest;
import com.iam.backendCouture.payload.request.SignupRequest;
import com.iam.backendCouture.payload.response.MessageResponse;
import com.iam.backendCouture.payload.response.UserInfoResponse;
import com.iam.backendCouture.repository.RoleRepository;
import com.iam.backendCouture.repository.UserRepository;
import com.iam.backendCouture.security.jwt.JwtUtils;
import com.iam.backendCouture.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
@PostMapping("/signin")
public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
			loginRequest.getUsername(), loginRequest.getPassword()));
	String jwt = jwtUtils.generateJwtToken(authentication);
	UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	List<String> roles = userDetails.getAuthorities()
			.stream()
			.map(item -> item.getAuthority())
			.collect(Collectors.toList());

	// Set the JWT token in the response body instead of the HTTP header
	return ResponseEntity.ok(new UserInfoResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
}
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Utilisateur existe deja !"));
		}
		if
		(userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new
					MessageResponse("Error: Email existe deja!"));
		}
		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));
		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();
		if (strRoles == null) {
			Role userRole =
					roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new
									RuntimeException("Error:Role n existe pas."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() ->
								new RuntimeException("Error: Role n existe pas."));
						roles.add(adminRole);
						break;
					case "mod":
						Role modRole =
								roleRepository.findByName(ERole.ROLE_MODERATOR)
										.orElseThrow(() -> new
												RuntimeException("Error: Role n existe pas."));
						roles.add(modRole);
						break;
					default:
						Role userRole =
								roleRepository.findByName(ERole.ROLE_USER)
										.orElseThrow(() -> new
												RuntimeException("Error: Role n existe pas."));
						roles.add(userRole);
				}
			});
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("L utilisateur s'est enregistré avec succès !"));
	}
	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(new MessageResponse("Vous avez été déconnecté !"));
	}
}