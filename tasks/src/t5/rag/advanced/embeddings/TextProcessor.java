package t5.rag.advanced.embeddings;

import commons.exceptions.TaskNotImplementedException;
import t5.rag.advanced.utils.TextUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextProcessor {

    private final EmbeddingsClient embeddingsClient;
    private final String jdbcUrl;
    private final String dbUser;
    private final String dbPassword;

    public TextProcessor(EmbeddingsClient embeddingsClient, String host, int port, String database, String user, String password) {
        this.embeddingsClient = embeddingsClient;
        this.jdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/" + database;
        this.dbUser = user;
        this.dbPassword = password;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
    }

    /**
     * Load file content, chunk it, generate embeddings, and store all chunks in the DB.
     * Truncates the vectors table before loading by default.
     */
    public void processTextFile(String fileName, int chunkSize, int overlap, int dimensions) {
        processTextFile(fileName, chunkSize, overlap, dimensions, true);
    }

    public void processTextFile(String fileName, int chunkSize, int overlap, int dimensions, boolean truncateTable) {
        //TODO:
        // - validate: chunkSize >= 10, overlap >= 0, overlap < chunkSize (throw IllegalArgumentException on failure)
        // - if truncateTable is true, call truncateTable()
        // - read file content via Files.readString(Path.of(fileName))
        // - split into chunks via TextUtils.chunkText(content, chunkSize, overlap)
        // - generate embeddings via embeddingsClient.getEmbeddings(chunks, dimensions)
        // - iterate chunks, persist each via saveChunk(embeddings.get(i), chunks.get(i), fileName)
        //   hint 1: embeddings are saved as a string representation of the float list "[v1,v2,...]"
        //   hint 2: the string is cast to vector type in SQL via `?::vector`
        // - wrap checked exceptions in RuntimeException
    }

    private void truncateTable() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE vectors");
            System.out.println("Table has been successfully truncated.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveChunk(List<Float> embedding, String chunk, String documentName) {
        String vectorString = "[" + embedding.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "]";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO vectors (document_name, text, embedding) VALUES (?, ?, ?::vector)")) {
            ps.setString(1, documentName);
            ps.setString(2, chunk);
            ps.setString(3, vectorString);
            ps.executeUpdate();
            System.out.println("Stored chunk from document: " + documentName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Perform similarity search against stored vectors.
     *
     * @param searchMode     Euclidean or Cosine distance
     * @param userRequest    Query text to embed and search with
     * @param topK           Maximum number of results to return
     * @param scoreThreshold Minimum similarity score [0.0, 1.0]
     * @param dimensions     Must match the dimensions used during indexing
     */
    public List<String> search(SearchMode searchMode, String userRequest, int topK, double scoreThreshold, int dimensions) {
        //TODO:
        // - validate: topK >= 1, scoreThreshold in [0.0, 1.0] (throw IllegalArgumentException on failure)
        // - generate query embedding via embeddingsClient.getEmbeddings(userRequest, dimensions), get index 0
        // - convert embedding List<Float> to vector string "[v1,v2,...]"
        // - compute maxDistance:
        //   hint 1: COSINE_DISTANCE    → maxDistance = 1.0 - scoreThreshold
        //   hint 2: EUCLIDEAN_DISTANCE → maxDistance = (1 / scoreThreshold) - 1  (scoreThreshold=0 → Double.MAX_VALUE)
        // - prepare statement via buildSearchQuery(searchMode), bind: vectorString (params 1 and 2), maxDistance, topK
        //   hint 3: Euclidean uses `<->`, Cosine uses `<=>` operator (handled inside buildSearchQuery)
        //   hint 4: extract "text" and "distance" columns from each ResultSet row
        //   hint 5: similarity = COSINE → 1.0 - distance; EUCLIDEAN → 1.0 / (1.0 + distance)
        // - return list of retrieved text chunks
        //
        // SQL pattern (see buildSearchQuery):
        // SELECT text, embedding <-> '[...]'::vector AS distance
        // FROM vectors
        // WHERE embedding <-> '[...]'::vector <= {maxDistance}
        // ORDER BY distance
        // LIMIT {topK}
        throw new TaskNotImplementedException();
    }

    private String buildSearchQuery(SearchMode searchMode) {
        String operator = searchMode == SearchMode.EUCLIDEAN_DISTANCE ? "<->" : "<=>";
        return "SELECT text, embedding " + operator + " ?::vector AS distance " +
               "FROM vectors " +
               "WHERE embedding " + operator + " ?::vector <= ? " +
               "ORDER BY distance " +
               "LIMIT ?";
    }
}
