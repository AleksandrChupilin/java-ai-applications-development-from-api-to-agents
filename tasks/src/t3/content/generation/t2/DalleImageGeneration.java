package t3.content.generation.t2;

import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Constants;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * T3-2a: DALL-E 3 Image Generation
 * <p>
 * Generates an image from a text prompt via /v1/images/generations.
 * The response contains a URL to the generated image.
 * Experiment with Size, Style, and Quality to see the differences.
 */
public class DalleImageGeneration {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    /**
     * The size of the generated image.
     */
    public enum Size {
        SQUARE("1024x1024"),
        PORTRAIT("1024x1792"),
        LANDSCAPE("1792x1024");

        private final String value;

        Size(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * vivid — hyper-real and dramatic; natural — less hyper-real, more realistic.
     */
    public enum Style {
        NATURAL("natural"),
        VIVID("vivid");

        private final String value;

        Style(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * hd creates images with finer details and greater consistency.
     */
    public enum Quality {
        STANDARD("standard"),
        HD("hd");

        private final String value;

        Quality(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static void main(String[] args) throws Exception {
        // TODO:
        // - Define a JSON payload for /v1/images/generations with model 'dall-e-3'
        // - Set 'prompt' to 'Smiling catdog' and experiment with 'size', 'style', and 'quality'
        // - Send a POST request to Constants.OPENAI_IMAGES_GENERATIONS_ENDPOINT
        // - Extract and print the image URL from the response JSON
    }
}
// https://developers.openai.com/api/reference/resources/images/methods/generate
// Request:
// curl https://api.openai.com/v1/images/generations \
//  -H "Content-Type: application/json" \
//  -H "Authorization: Bearer $OPENAI_API_KEY" \
//  -d '{
//    "model": "dall-e-3",
//    "prompt": "smiling catdog",
//    "size": "1024x1024",
//    "style": "natural",
//    "quality": "standard"
//  }'
//
