package t3.content.generation.t5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commons.Constants;

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
 * T3-5: Speech to Speech
 * <p>
 * Sends an audio question (question.mp3) as a base64-encoded input_audio message
 * to gpt-4o-audio-preview via /v1/chat/completions with modalities=["text","audio"].
 * The model responds with both text and audio; the audio is decoded from base64
 * and saved as an MP3 file.
 */
public class SpeechToSpeech {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    public static void main(String[] args) throws Exception {
        // TODO:
        //  https://developers.openai.com/api/docs/guides/audio#add-audio-to-your-existing-application
        // - Load 'question.mp3' and encode it to base64
        // - Define a JSON payload for /v1/chat/completions with model 'gpt-4o-audio-preview'
        // - Set 'modalities' to ["text", "audio"] and configure the 'audio' object (voice, format)
        // - Send a POST request to Constants.OPENAI_CHAT_COMPLETIONS_ENDPOINT
        // - Extract 'audio.data' from the response, decode it, and save as an .mp3 file
    }

}
