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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
@Profile("api-key-auth")
public class ApiKey {

    private static final String API_KEY_VALUE = "dev-secret-key";

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO:
        // - Disable CSRF and set `STATELESS` session creation policy
        // - Register `ApiKeyFilter` before `UsernamePasswordAuthenticationFilter`
        // - Permit `ASYNC` dispatcher type (required for SSE transport) and require auth on all other requests
        // - Return `http.build()`
        throw new TaskNotImplementedException();
    }

    // ==================== API KEY FILTER ====================

    private static class ApiKeyFilter extends OncePerRequestFilter {

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain) throws ServletException, IOException {
            //TODO:
            // - Read the "X-API-Key" header from `request`
            // - If the key matches `API_KEY_VALUE` — create a `UsernamePasswordAuthenticationToken`,
            //   set it on `SecurityContextHolder`, and call `chain.doFilter(request, response)`
            // - Otherwise — set response status 401, content type "application/json", and write a JSON error body
            throw new TaskNotImplementedException();
        }
    }
}
