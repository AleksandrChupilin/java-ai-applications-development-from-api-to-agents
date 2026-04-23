package t3.content.generation.t1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Constants;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/**
 * T3-1: Image Analysis (Vision)
 * <p>
 * Sends two images to gpt model via /v1/chat/completions:
 *   - a remote image by URL
 *   - a local logo.png encoded as a base64 data URL
 * and asks the model to write a poem based on both images.
 */
public class ImageAnalysis {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        //TODO:
        //  https://developers.openai.com/api/docs/guides/images-vision?format=url&lang=curl
        //  https://developers.openai.com/api/docs/guides/images-vision?format=base64-encoded
        // - Encode local 'logo.png' to a base64 data URL
        // - Define a JSON payload for /v1/chat/completions with model 'gpt-5.4' (or some other OpenAI LLM)
        // - Include a user message with 3 content parts: prompt text, remote image URL, and local base64 image
        // - Send a POST request to Constants.OPENAI_CHAT_COMPLETIONS_ENDPOINT with Bearer token
        // - Extract and print the poem from the response JSON
    }
}
