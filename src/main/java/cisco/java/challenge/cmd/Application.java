package cisco.java.challenge.cmd;

import cisco.java.challenge.node.ITrie;
import cisco.java.challenge.node.Trie;
import cisco.java.challenge.util.FileWorker;
import cisco.java.challenge.util.FileWorkerImpl;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@Log4j2
@SpringBootApplication
@ComponentScan
public class Application implements CommandLineRunner {
    @Autowired FileWorker<Trie> fileWorker;
    
    @Bean
    FileWorker<Trie> fileWorker() {
        return new FileWorkerImpl();
    }    
    @Bean
    ITrie trie() {
        return new Trie();
    }
    
    public static void main(String[] args) {
        log.info("STARTING THE APPLICATION");
        SpringApplication.run(Application.class, args);
        log.info("APPLICATION FINISHED");
    }
    
    @Override
    public void run(String... args) throws Exception {
        if (args.length==0) {
            log.error("Please, specify the file path");
            return;
        }
        
        for (int i=0; i<args.length; i++) {
            log.info("***** [{}] Start loading input file {} ***** ", i, args[i].trim());
            
            LocalDateTime start_at = LocalDateTime.now();            
            Trie trie = fileWorker.mapContent(args[i].trim());
            LocalDateTime stop_at = LocalDateTime.now();
            long diff = ChronoUnit.MILLIS.between(start_at, stop_at);
            log.info("***** [{}] Input data processed in {} ms", i, diff);
            log.info("{}\n", trie.showllAllOcurrences());

            //ArrayList<GNode> listAll = trie.walkGraph();
            //ArrayList<GNode> listIns = trie.paths('e');  
        }
    }    
}