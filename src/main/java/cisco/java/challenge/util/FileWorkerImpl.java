package cisco.java.challenge.util;

import cisco.java.challenge.node.Trie;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class FileWorkerImpl implements FileWorker<Trie> {

    @Bean
    FileWorker<Trie> fileWorker() {
        return new FileWorkerImpl();
    }
    
    @Override
    public Trie mapContent(String fullFilePath) {
        Trie trie = new Trie();
        
        try (Stream<String> stream = Files.lines(Paths.get(fullFilePath), StandardCharsets.ISO_8859_1)) {
            stream.parallel()
                    .flatMap(line -> 
                            Arrays.stream(line.split("\\s+|,\\s+|\\.\\s+|:|;"))
                    )
                    .parallel()
                    //.map(item -> item.replaceAll("[^A-Za-z0-9 ]", ""))
                    //.filter(it -> !it.isEmpty())
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .forEach(word -> trie.insert(word, trie.getRoot()));
            
        } catch (IOException ex) {
            log.error("{}: {}", ex.getStackTrace()[0].getMethodName(), ex.getMessage());
            ex.printStackTrace();
	}
        return trie;
    }
}