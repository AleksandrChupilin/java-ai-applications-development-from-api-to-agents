package t3.content.generation.t2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Constants;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * T3-2c: GPT-Image-1 Image Edit
 * <p>
 * Edits an existing image via /v1/images/edits using gpt-image-1.
 * Request is multipart/form-data (NOT JSON) and includes the original image,
 * the model name, and an edit prompt. The response returns the edited image
 * as base64 JSON (b64_json) which is decoded and saved as a PNG file.
 */
public class GptImageEdit {

    private static final String OPENAI_IMAGES_EDITS_ENDPOINT = "https://api.openai.com/v1/images/edits";

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        //TODO:
        //  https://platform.openai.com/docs/api-reference/images/createEdit
        // - Load 'logo.png' and define the model (e.g., 'gpt-image-1') and an edit prompt
        // - Build a multipart/form-data request body manually (including boundary and fields: model, prompt, image)
        // - Send a POST request to OPENAI_IMAGES_EDITS_ENDPOINT
        // - Extract the 'b64_json' from data[0], decode base64, and save the edited image as PNG
    }
}

// https://developers.openai.com/api/reference/resources/images/methods/edit
// ---
// Request (multipart/form-data, NOT json):
// curl -X POST "https://api.openai.com/v1/images/edits" \
//     -H "Authorization: Bearer $OPENAI_API_KEY" \
//     -F "model=gpt-image-1" \
//     -F "image=@logo.png" \
//     -F "prompt=Add magical sparkles and glowing aura around the logo"
// Response:
// {
//   "created": 1699900000,
//   "data": [
//     {
//       "b64_json": "Qt0n6ArYAEABGOhEoYgVAJFdt8jM79uW2DO..."
//     }
//   ]
// }