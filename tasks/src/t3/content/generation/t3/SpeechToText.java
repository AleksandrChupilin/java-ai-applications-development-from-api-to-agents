package t3.content.generation.t3;

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

/**
 * T3-3: Speech to Text (Transcription)
 * <p>
 * Transcribes audio_sample.mp3 via /v1/audio/transcriptions using multipart/form-data.
 * Try both WHISPER_1 and GPT_4O_TRANSCRIBE models and compare the results.
 */
public class SpeechToText {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        //TODO:
        //  https://developers.openai.com/api/docs/guides/speech-to-text
        // - Load 'audio_sample.mp3' and define the model (e.g., 'whisper-1')
        // - Build a multipart/form-data request body manually (including boundary and fields)
        // - Send a POST request to Constants.OPENAI_AUDIO_TRANSCRIPTIONS_ENDPOINT
        // - Extract the 'text' from the response and print it
    }
}
