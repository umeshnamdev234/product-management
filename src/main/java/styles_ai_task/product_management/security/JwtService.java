package styles_ai_task.product_management.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Service responsible for JWT (JSON Web Token) operations.
 *
 * This class handles:
 * - JWT token generation
 * - JWT token validation
 * - Extracting claims from JWT tokens
 * - Extracting username and role information
 *
 * JWT tokens are used to authenticate users in a stateless
 * Spring Security architecture.
 *
 * Token Structure:
 * Header:
 * {
 * "alg": "HS256",
 * "typ": "JWT"
 * }
 *
 * Payload:
 * {
 * "sub": "admin",
 * "role": "EDIT",
 * "iat": 1717740000,
 * "exp": 1717826400
 * }
 *
 * Signature:
 * Generated using the secret key and HMAC SHA algorithm.
 */
@Service
public class JwtService {

	/**
	 * Secret key used for signing and validating JWT tokens.
	 *
	 * In production environments this should never be hardcoded.
	 * It should be stored securely in:
	 * - application.properties
	 * - environment variables
	 * - AWS Secrets Manager
	 * - Vault
	 */

	/**
	 * Cryptographic signing key generated from the secret.
	 *
	 * This key is used to:
	 * - Sign JWT tokens during generation
	 * - Verify JWT signatures during validation
	 */

	@Value("${jwt.secret}")
	private String secret;

	private SecretKey key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	/**
	 * Generates a JWT token for an authenticated user.
	 *
	 * The token contains:
	 * - Username (stored as subject)
	 * - User role (stored as custom claim)
	 * - Issue timestamp
	 * - Expiration timestamp
	 *
	 * Token validity:
	 * 24 hours
	 *
	 * @param username authenticated username
	 * @param role     user role or authority
	 * @return signed JWT token
	 */
	public String generateToken(
			String username,
			String role) {

		return Jwts.builder()

				// Subject represents the user identity
				.subject(username)

				// Custom claim storing user role
				.claim("role", role)

				// Token creation timestamp
				.issuedAt(new Date())

				// Token expiration (24 hours)
				.expiration(
						new Date(
								System.currentTimeMillis()
										+ 86400000))

				// Sign token using secret key
				.signWith(key)

				// Generate final compact JWT string
				.compact();
	}

	/**
	 * Extracts all claims from a JWT token.
	 *
	 * During parsing:
	 * - Signature is verified
	 * - Expiration is checked
	 * - Claims are extracted
	 *
	 * If token is invalid, expired, or tampered with,
	 * an exception will be thrown.
	 *
	 * @param token JWT token
	 * @return Claims object containing token data
	 */
	public Claims extractClaims(
			String token) {

		return Jwts.parser()

				// Verify token signature using secret key
				.verifyWith(key)

				.build()

				// Parse signed JWT token
				.parseSignedClaims(token)

				// Extract payload claims
				.getPayload();
	}

	/**
	 * Extracts username from JWT token.
	 *
	 * Username is stored in the token subject.
	 *
	 * Example:
	 * Subject = "admin"
	 *
	 * @param token JWT token
	 * @return username stored in token
	 */
	public String extractUsername(
			String token) {
		return extractClaims(token).getSubject();
	}

	/**
	 * Extracts role from JWT token.
	 *
	 * Role is stored as a custom claim named "role".
	 *
	 * Example:
	 * {
	 * "role": "EDIT"
	 * }
	 *
	 * @param token JWT token
	 * @return user role stored in token
	 */
	public String extractRole(
			String token) {
		return extractClaims(token)
				.get("role", String.class);
	}
}