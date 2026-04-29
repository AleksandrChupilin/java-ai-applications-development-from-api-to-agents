package t11.mcp.auth.mcp.server.auth;

import commons.exceptions.TaskNotImplementedException;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@Profile("oauth-auth")
public class OAuth {

    // ==================== CONFIGURATION ====================

    private static final String KEYCLOAK_URL = System.getenv("KEYCLOAK_URL") != null
            ? System.getenv("KEYCLOAK_URL") : "http://localhost:8089";
    private static final String KEYCLOAK_REALM = System.getenv("KEYCLOAK_REALM") != null
            ? System.getenv("KEYCLOAK_REALM") : "mcp-realm";
    private static final String REQUIRED_ROLE = System.getenv("MCP_REQUIRED_ROLE") != null
            ? System.getenv("MCP_REQUIRED_ROLE") : "mcp-tools-access";

    private static final String ISSUER_URI = KEYCLOAK_URL + "/realms/" + KEYCLOAK_REALM;
    private static final String JWKS_URI = ISSUER_URI + "/protocol/openid-connect/certs";

    // ==================== SECURITY FILTER CHAIN ====================

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO:
        // - Build a `NimbusJwtDecoder` from `JWKS_URI` and set issuer validator via `JwtValidators.createDefaultWithIssuer(ISSUER_URI)`
        //   (JWKS is fetched lazily — no Keycloak needed at startup)
        // - Disable CSRF and set `STATELESS` session creation policy
        // - Configure `oauth2ResourceServer` with the JWT decoder
        // - Add `RoleCheckFilter` after `BearerTokenAuthenticationFilter`
        // - Permit `ASYNC` dispatcher type (required for SSE transport) and require auth on all other requests
        // - Return `http.build()`
        throw new TaskNotImplementedException();
    }

    // ==================== ROLE CHECK FILTER ====================

    private static class RoleCheckFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain) throws ServletException, IOException {
            //TODO:
            // - Get authentication from `SecurityContextHolder`; if it's a `JwtAuthenticationToken`,
            //   extract the `realm_access.roles` list from the JWT claims
            //   (Keycloak embeds realm roles in: claims["realm_access"]["roles"])
            // - If `REQUIRED_ROLE` is not in the roles — set status 403, content type "application/json",
            //   write a JSON error listing the user's roles, and return
            // - Otherwise log the authenticated username and roles, then call `chain.doFilter(request, response)`
            throw new TaskNotImplementedException();
        }
    }
}
