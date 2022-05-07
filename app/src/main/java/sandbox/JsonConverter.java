/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package sandbox;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;

public class JsonConverter {
    public static void main(String[] args) throws IOException {
        // String jsonPath = args[0];
        String jsonPath = "src/main/resources/sample.json";
        convertToCsv(jsonPath);
    }

    public static void convertToCsv(String jsonPath) throws IOException {
        String data = Files.readString(Paths.get(jsonPath));

        JsonReader jsonReader = Json.createReader(new StringReader(data));
        JsonStructure json = jsonReader.read();

        JsonValue sender = json.getValue("/sender");
        JsonValue receiver = json.getValue("/receiver");
        JsonValue documents = json.getValue("/documents");

        JsonObject senderObj = sender.asJsonObject();
        JsonObject receiverObj = receiver.asJsonObject();
        JsonArray documentsArray = documents.asJsonArray();

        int counter = 1;

        String outputDir = "src/main/resources/";

        String senderBody = "\"" + counter + "\"," + "\"" + senderObj.getString("name") + "\"," + "\""
                + senderObj.getString("division") + "\"";
        Files.writeString(Paths.get(outputDir + "sender.csv"), senderBody);
        counter++;

        String receiverBody = "\"" + counter + "\"," + "\"" + receiverObj.getString("name") + "\"," + "\""
                + receiverObj.getString("division") + "\"";
        Files.writeString(Paths.get(outputDir + "receiver.csv"), receiverBody);

        counter++;

        String documentBody = "";
        for (JsonValue document : documentsArray) {
            JsonObject documentObj = document.asJsonObject();
            documentBody += "\"" + counter + "\"," + "\"" + documentObj.getString("file_name") + "\"," + "\""
                    + documentObj.getString("accepted_date") + "\"" + "\n";
            counter++;
        }
        Files.writeString(Paths.get(outputDir + "documents.csv"), documentBody);
    }
}