package com.taehk23.simplechat.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.taehk23.simplechat.entity.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FileMessageRepository {

    private final Path filePath;
    private final ObjectMapper objectMapper;

    public FileMessageRepository() {
        this.filePath = Paths.get(System.getProperty("user.dir"), "data", "messages");
        this.objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .enable(SerializationFeature.INDENT_OUTPUT);

        if(Files.notExists(filePath)) {
            try {
                Files.createDirectories(filePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized Message save(Message message) {
        if(message.getId() == null) {
            message.setId(generateId());
        }

        Path path = getFilePath(message.getId());
        try {
            objectMapper.writeValue(path.toFile(), message);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    public synchronized Optional<Message> findById(Long id) {
        Path path = getFilePath(id);
        if(Files.notExists(path)) {
            return Optional.empty();
        }
        return Optional.of(read(path));
    }

    public synchronized List<Message> findLastMessages(int size) {
        try (var paths = Files.list(filePath)){
            return paths
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(this::read)
                    .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
                    .limit(size)
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void deleteById(Long id) {
        Path path = getFilePath(id);

        if(Files.notExists(path)) {
            throw new IllegalArgumentException("File not found");
        }

        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getFilePath(Long id) { return filePath.resolve(id + ".json"); }

    private Long generateId() {
        try (Stream<Path> paths = Files.list(filePath)) {
            return paths
                    .filter(path -> path.toString().endsWith(".json"))
                    .map(path -> path.getFileName().toString().replace(".json", ""))
                    .map(Long::parseLong)
                    .max(Long::compareTo)
                    .orElse(0L) + 1;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Message read(Path path) {
        try {
            return objectMapper.readValue(path.toFile(), Message.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
