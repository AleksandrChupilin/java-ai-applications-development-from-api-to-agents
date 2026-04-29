package t11.mcp.auth.agent.clients;

import commons.exceptions.TaskNotImplementedException;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * MCP client that authenticates via OAuth 2.0 Authorization Code + PKCE flow (Keycloak).
 *
 * <p>On {@link #connect()}:
 * <ol>
 *   <li>Runs the PKCE browser flow (opens Keycloak login once)
 *   <li>Connects to the MCP server with the resulting Bearer token
 * </ol>
 *
 * <p>On tool calls: proactively checks token expiry and transparently refreshes + reconnects
 * before each call to avoid broken streams mid-session.
 */
public class OAuthMcpClient extends BaseMcpClient {

    // ==================== KEYCLOAK CONFIGURATION ====================

    private static final String KEYCLOAK_URL = envOr("KEYCLOAK_URL", "http://localhost:8089");
    private static final String KEYCLOAK_REALM = envOr("KEYCLOAK_REALM", "mcp-realm");
    private static final String CLIENT_ID = envOr("MCP_CLIENT_ID", "mcp-client");

    private static final int REDIRECT_PORT = 9999;
    private static final String REDIRECT_URI = "http://localhost:" + REDIRECT_PORT + "/callback";
    private static final String BASE = KEYCLOAK_URL + "/realms/" + KEYCLOAK_REALM + "/protocol/openid-connect";
    private static final String AUTH_ENDPOINT = BASE + "/auth";
    private static final String TOKEN_ENDPOINT = BASE + "/token";

    // ==================== TOKEN STATE ====================

    private String accessToken;
    private String storedRefreshToken;
    private long expiresAt;

    private final HttpClient tokenHttpClient = HttpClient.newHttpClient();

    public OAuthMcpClient(String mcpServerUrl) {
        super(mcpServerUrl);
    }

    // ==================== LIFECYCLE ====================

    @Override
    protected void beforeConnect() {
        runPkceFlow();
    }

    @Override
    protected Consumer<HttpRequest.Builder> requestCustomizer() {
        //TODO:
        // - Return a lambda that adds "Authorization: Bearer <accessToken>" to each request builder
        //   (the lambda captures `this` so it reads the current `accessToken` field on every request,
        //   meaning a refreshed token is picked up automatically without rebuilding the transport)
        throw new TaskNotImplementedException();
    }

    @Override
    public String callTool(String toolName, Map<String, Object> arguments) {
        //TODO:
        // - If `isTokenExpired()` — print a token-expired message and call `refreshAndReconnect()`
        // - Delegate to `super.callTool(toolName, arguments)` and return the result
        throw new TaskNotImplementedException();
    }

    // ==================== PKCE FLOW ====================

    private void runPkceFlow() {
        //TODO:
        // Step 1 — Generate `code_verifier` (64 random bytes via `SecureRandom`, Base64URL-encoded without padding)
        //           and `code_challenge` (SHA-256 of verifier via `sha256Base64Url()`)
        // Step 2 — Generate a random `state` string (16 bytes, Base64URL-encoded) for CSRF protection
        // Step 3 — Start a local callback server in a virtual thread via `Thread.ofVirtual().start()` using
        //           `runCallbackServer()` with a `CountDownLatch` and `AtomicReference<String>` for code and state
        // Step 4 — Build the authorization URL via `buildAuthUrl()` and open it in the browser via
        //           `Desktop.getDesktop().browse()`; print the URL in case the browser doesn't open
        // Step 5 — Await the latch (120 s timeout); throw `RuntimeException` if timed out
        // Step 6 — Validate the returned `state` matches to prevent CSRF; throw if mismatch or code is null
        // Step 7 — Exchange the authorization code for tokens via `exchangeCodeForTokens(code, codeVerifier)`
        throw new TaskNotImplementedException();
    }

    // ==================== LOCAL CALLBACK SERVER ====================

    private void runCallbackServer(CountDownLatch latch,
                                   AtomicReference<String> codeRef,
                                   AtomicReference<String> stateRef) {
        try (ServerSocket serverSocket = new ServerSocket(REDIRECT_PORT)) {
            serverSocket.setSoTimeout(120_000);
            try (Socket socket = serverSocket.accept()) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                String requestLine = reader.readLine();

                if (requestLine != null && requestLine.contains("/callback")) {
                    String path = requestLine.split(" ")[1];
                    int queryStart = path.indexOf('?');
                    if (queryStart >= 0) {
                        Map<String, String> params = parseQueryParams(path.substring(queryStart + 1));
                        codeRef.set(params.get("code"));
                        stateRef.set(params.get("state"));
                    }
                    sendBrowserResponse(socket.getOutputStream(), codeRef.get() != null);
                }
            }
            latch.countDown();
        } catch (Exception e) {
            latch.countDown();
        }
    }

    private void sendBrowserResponse(OutputStream out, boolean success) throws IOException {
        String body = success
                ? "<html><body style='font-family:monospace;background:#0a0c10;color:#34d399;"
                        + "display:flex;align-items:center;justify-content:center;"
                        + "height:100vh;margin:0;font-size:18px;'>"
                        + "<div>&#10003; Authentication successful. You can close this tab.</div>"
                        + "</body></html>"
                : "<html><body style='font-family:monospace;background:#0a0c10;color:#f87171;"
                        + "display:flex;align-items:center;justify-content:center;"
                        + "height:100vh;margin:0;font-size:18px;'>"
                        + "<div>&#10007; Authentication failed. Check terminal for details.</div>"
                        + "</body></html>";
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n"
                + "Content-Length: " + body.length() + "\r\n\r\n" + body;
        out.write(response.getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    // ==================== TOKEN EXCHANGE ====================

    @SuppressWarnings("unchecked")
    private void exchangeCodeForTokens(String code, String codeVerifier) {
        //TODO:
        // - Build an `authorization_code` grant form body with `client_id`, `redirect_uri`, `code`,
        //   `code_verifier` (URL-encode each value via `encode()`)
        // - POST to `TOKEN_ENDPOINT` with `Content-Type: application/x-www-form-urlencoded` using `tokenHttpClient`
        // - If response status is not 200 — throw `RuntimeException` with status and body details
        // - Parse the JSON response with `objectMapper`, call `storeTokens()`, and print the expiry time
        throw new TaskNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private void performTokenRefresh() {
        //TODO:
        // - If `storedRefreshToken` is null — throw `RuntimeException` (no refresh token available)
        // - Build a `refresh_token` grant form body with `client_id` and `refresh_token` (encode values via `encode()`)
        // - POST to `TOKEN_ENDPOINT` using `tokenHttpClient`, parse the JSON response, and call `storeTokens()`
        throw new TaskNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private void storeTokens(Map<String, Object> tokens) {
        //TODO:
        // - Extract `access_token` from the map and assign to `this.accessToken`
        // - If a new `refresh_token` is present in the map, update `this.storedRefreshToken`
        // - Calculate `expiresAt`: `System.currentTimeMillis() + (expiresIn - 30) * 1000L` using
        //   `expires_in` from the map (default 300 if missing), applying a 30-second buffer
        throw new TaskNotImplementedException();
    }

    // ==================== TOKEN REFRESH + RECONNECT ====================

    private boolean isTokenExpired() {
        return expiresAt == 0 || System.currentTimeMillis() >= expiresAt;
    }

    private void refreshAndReconnect() {
        //TODO:
        // - Call `performTokenRefresh()` to obtain a new access token
        // - Close the existing `mcpClient` gracefully if it is not null (wrap in try/catch)
        // - Call `connectTransport()` to rebuild the transport with the refreshed token
        //   (`requestCustomizer()` reads the updated `accessToken` field automatically)
        throw new TaskNotImplementedException();
    }

    // ==================== HELPERS ====================

    private String buildAuthUrl(String codeChallenge, String state) {
        return AUTH_ENDPOINT
                + "?response_type=code"
                + "&client_id=" + encode(CLIENT_ID)
                + "&redirect_uri=" + encode(REDIRECT_URI)
                + "&scope=" + encode("openid profile")
                + "&state=" + encode(state)
                + "&code_challenge=" + encode(codeChallenge)
                + "&code_challenge_method=S256";
    }

    private static String sha256Base64Url(String input) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        for (String pair : query.split("&")) {
            int eq = pair.indexOf('=');
            if (eq > 0) {
                params.put(pair.substring(0, eq), pair.substring(eq + 1));
            }
        }
        return params;
    }

    private static String envOr(String name, String defaultValue) {
        String val = System.getenv(name);
        return (val != null && !val.isBlank()) ? val : defaultValue;
    }
}
