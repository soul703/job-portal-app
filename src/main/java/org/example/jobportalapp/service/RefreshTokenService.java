package org.example.jobportalapp.service;

import org.example.jobportalapp.entity.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenService {
    /**
     * Generates a new refresh token for the user.
     *
     * @param userId The ID of the user for whom the refresh token is generated.
     * @return The generated refresh token.
     */
    public RefreshToken createRefreshToken(UUID userId);

    /**
     * Validates the provided refresh token.
     *
     * @param token The refresh token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public RefreshToken verifyExpiration(RefreshToken token);

    /**
     * Deletes the refresh token associated with the specified user ID.
     *
     * @param  userId The ID of the user whose refresh token is to be deleted.
     * @return The number of tokens deleted (should be 1 if successful).
     */
    public int deleteByUserId(UUID userId);
    /**
     * Finds a refresh token by its token string.
     *
     * @param token The token string to search for.
     * @return The found RefreshToken, or null if not found.
     */
    public Optional<RefreshToken> findByToken(String token);
}
