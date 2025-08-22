package utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class StorageManager {
    
    private static final String ROOT_DIR = "data";
    private static final String NOTEBOOKS_DIR = ROOT_DIR + "/notebooks";
    private static final String DOCUMENTS_DIR = ROOT_DIR + "/documents";
    private static final String FOLDERS_DIR = ROOT_DIR + "/folders";
    private static final String INDEX_FILE = ROOT_DIR + "/index.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // Ensure folder structire exists 
    public static void initialize() throws IOException {
        Files.createDirectories(Path.of(NOTEBOOKS_DIR));
        Files.createDirectories(Path.of(DOCUMENTS_DIR));
        Files.createDirectories(Path.of(FOLDERS_DIR));

        File indexFile = new File(INDEX_FILE);

        if (!indexFile.exists()){
            JsonObject root = new JsonObject();

            root.add("notebooks", new JsonArray());
            root.add("documents", new JsonArray());
            root.add("folders", new JsonArray());
            root.add("favorites", new JsonArray());
            root.add("trash", new JsonArray());

            try (FileWriter writer = new FileWriter(indexFile)) {
                gson.toJson(root, writer);
            }
        }
    } 

    // Generate unique ID
    public static String generateID(String prefix) {
        return prefix + "_" + UUID.randomUUID();
    }

    // Update index.json
    private static void updateIndex(String type, String id, String title, String filePath) throws IOException {
        File indexFile = new File(INDEX_FILE);
        JsonObject indexjson;

        if (indexFile.exists()) {
            try (FileReader render = new FileReader(indexFile)){
                indexjson = gson.fromJson(render, JsonObject.class);
            }
        } else {
            indexjson = new JsonObject();
            indexjson.add("notebooks", new JsonArray());
            indexjson.add("documents", new JsonArray());
            indexjson.add("folders", new JsonArray());
            indexjson.add("favorites", new JsonArray());
            indexjson.add("trash", new JsonArray());
        }

        JsonObject entry = new JsonObject();
        entry.addProperty("id", id);
        entry.addProperty("title", title);
        entry.addProperty("path", filePath);
        entry.addProperty("trashed", false);

        indexjson.getAsJsonArray(type).add(entry);

        try (FileWriter writer = new FileWriter(indexFile)) {
            gson.toJson(indexjson, writer);
        }
    }

    // Save Notebook
    public static void saveNotebook(String title, Map<String, Object> content) throws IOException {
        String id = generateID("nb");
        String filePath = NOTEBOOKS_DIR + "/" + id + ".json";

        JsonObject notebookjson = new JsonObject();
        notebookjson.addProperty("id", id);
        notebookjson.addProperty("title", title);
        notebookjson.addProperty(("createdAt"), new Date().toString());
        if (content == null) {
            notebookjson.add("pages", new JsonArray());
        } else {
            notebookjson.add("pages", gson.toJsonTree(content.getOrDefault("pages", List.of())));
        }

        try (FileWriter writer = new FileWriter(filePath)){
            gson.toJson(notebookjson, writer);
        }

        updateIndex("notebooks", id, title, filePath);
    }

    // Save Folder
    public static void saveFolder(String title, List<String> items) throws IOException {
        String id = generateID("folder");
        String filePath = FOLDERS_DIR + "/" + id + ".json";

        JsonObject folderjosn = new JsonObject();
        folderjosn.addProperty("id", id);
        folderjosn.addProperty("title", title);
        folderjosn.add("item", gson.toJsonTree(items));

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(folderjosn, writer);
        }

        updateIndex("folders", id, title, filePath);
    }

    // Save Document
    public static void saveDocument(String title, String content) throws IOException {
        String id = generateID("doc");
        String filePath = DOCUMENTS_DIR + "/" + id + ".json";

        JsonObject docjson = new JsonObject();
        docjson.addProperty("id", id);
        docjson.addProperty("title", title);
        docjson.addProperty("createdAt", new Date().toString());
        docjson.addProperty("content", content);

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(docjson, writer);
        }

        updateIndex("documents", id, title, filePath);
    }

    // Save Favorite
    public static void addFavorite(String title, String id) throws IOException {
        File indexFile = new File(INDEX_FILE);

        if (!indexFile.exists()) return ;

        JsonObject indexjson;
        
        try (FileReader reader = new FileReader((indexFile))) {
            indexjson = gson.fromJson(reader, JsonObject.class);
        }

        JsonArray favArray = indexjson.getAsJsonArray("favorites");
        if (favArray == null){
            favArray = new JsonArray();
            indexjson.add("favorites", favArray);
        }

        boolean exists = favArray.asList().stream().anyMatch(e -> e.getAsString().equals(id));
        if (!exists) {
            JsonObject favEntry = new JsonObject();
            favEntry.addProperty("id", id);
            favEntry.addProperty("title", title);
            favArray.add(favEntry);
        }

        try (FileWriter writer = new FileWriter(indexFile)) {
            gson.toJson(indexjson, writer);
        }
    }

    // Remove favorites
    public static void removefavorites(String id) throws IOException {
        File indexFile = new File(INDEX_FILE);
        if (!indexFile.exists()) return;

        JsonObject indexjson;
        try (FileReader reader = new FileReader(indexFile)) {
            indexjson = gson.fromJson(reader, JsonObject.class);
        }

        JsonArray favArray = indexjson.getAsJsonArray("favorites");
        if (favArray != null) {
            for (int i = 0; i < favArray.size(); i++) {
                if (favArray.get(i).getAsString().equals(id)) {
                    favArray.remove(i);
                    break;
                }
            }
        }

        try (FileWriter writer = new FileWriter(indexFile)) {
            gson.toJson(indexjson, writer);
        }
    }

    // Move item to trash
    public static void moveToTrash(String type, String id) throws IOException {
        File indexFile = new File(INDEX_FILE);
        if (!indexFile.exists()) return;

        JsonObject indexjson;
        try (FileReader reader = new FileReader(indexFile)) {
            indexjson = gson.fromJson(reader, JsonObject.class);
        }

        JsonArray array = indexjson.getAsJsonArray(type);
        JsonArray trasharray = indexjson.getAsJsonArray("trash");

        if (trasharray == null){
            trasharray = new JsonArray();
            indexjson.add("trash", trasharray);
        }

        for (int i=0; i<array.size(); i++){
            JsonObject item = array.get(i).getAsJsonObject();
            if (item.get("id").getAsString().equals(id)) {
                item.addProperty("trashed", true);
                trasharray.add(id);
                break;
            }
        }

        try (FileWriter writer = new FileWriter(indexFile)) {
            gson.toJson(indexjson, writer);
        }
    }

    // Load index.json into memory
    public static Map<String, List<Map<String, String>>> loadIndex() throws IOException {
        File indexFile = new File(INDEX_FILE);
        if (!indexFile.exists()) return Map.of();

        JsonObject indexJson;
        try (FileReader reader = new FileReader(indexFile)) {
            indexJson = gson.fromJson(reader, JsonObject.class);
        }

        Map<String, List<Map<String, String>>> index = new HashMap<>();

        for (String type : List.of("notebooks", "documents", "folders")) {
            List<Map<String, String>> items = new ArrayList<>();
            JsonArray array = indexJson.has(type) && indexJson.get(type).isJsonArray()
                                ? indexJson.getAsJsonArray(type)
                                :new JsonArray();

            array.forEach(node -> {
                JsonObject obj = node.getAsJsonObject();
                Map<String, String> item = new HashMap<>();
                item.put("id", obj.get("id").getAsString());
                item.put("title", obj.get("title").getAsString());
                item.put("path", obj.get("path").getAsString());
                items.add(item);
            });

            index.put(type, items);
        }

        return index;
    }
}
