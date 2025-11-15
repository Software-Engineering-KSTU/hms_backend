package org.example.backendjava.autth_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.example.backendjava.autth_service.entity.User;
import org.example.backendjava.autth_service.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/**
 * Утилитный сервис для работы с JWT (JSON Web Token).
 * Позволяет генерировать, проверять и извлекать данные из токенов.
 */
@Service
public class JwtUtil {

    private final TokenRepository tokenRepository;

    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    public JwtUtil(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * Извлекает имя пользователя из токена.
     *
     * @param token JWT
     * @return имя пользователя (username)
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Проверяет валидность access-токена для пользователя.
     *
     * @param token JWT
     * @param user  пользователь
     * @return true, если токен действителен и не отозван
     */
    public boolean isValid(String token, UserDetails user) {
        String username = extractUsername(token);
        boolean existsAndNotLoggedOut = tokenRepository.findByAccessToken(token)
                .map(t -> !t.isRevoked())
                .orElse(false);
        return username.equals(user.getUsername()) && isTokenExpired(token) && existsAndNotLoggedOut;
    }

    /**
     * Проверяет валидность refresh-токена для пользователя.
     *
     * @param token JWT
     * @param user  пользователь
     * @return true, если токен действителен
     */
    public boolean isValidRefreshToken(String token, User user) {
        String username = extractUsername(token);
        return username.equals(user.getUsername()) && isTokenExpired(token);
    }

    /**
     * Проверяет, истёк ли токен.
     *
     * @param token JWT
     * @return true, если токен не истёк
     */
    public boolean isTokenExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }

    /**
     * Извлекает дату окончания действия токена.
     *
     * @param token JWT
     * @return дата истечения токена
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлекает любое значение из токена через resolver.
     *
     * @param token    JWT
     * @param resolver функция для получения нужного поля из Claims
     * @param <T>      тип возвращаемого значения
     * @return значение из токена
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Генерирует access-токен для пользователя.
     *
     * @param user пользователь
     * @return JWT access-токен
     */
    public String generateAccessToken(User user) {
        return generateToken(user, accessTokenExpire);
    }

    /**
     * Генерирует refresh-токен для пользователя.
     *
     * @param user пользователь
     * @return JWT refresh-токен
     */
    public String generateRefreshToken(User user) {
        return generateToken(user, refreshTokenExpire);
    }

    /**
     * Генерирует токен с заданным временем жизни.
     *
     * @param user       пользователь
     * @param expireTime время жизни токена в миллисекундах
     * @return JWT токен
     */
    public String generateToken(User user, long expireTime) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSignKey())
                .compact();
    }

    /**
     * Возвращает секретный ключ для подписи JWT.
     *
     * @return SecretKey
     */
    public SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
