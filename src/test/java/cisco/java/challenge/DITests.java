
package cisco.java.challenge;

import cisco.java.challenge.cmd.Application;
import cisco.java.challenge.node.Trie;
import cisco.java.challenge.util.FileWorker;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("dev")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
//@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = CommandLineRunner.class))
public class DITests {
    @Autowired private Trie trie;
    @Autowired private FileWorker<Trie> fileWorker;    
    
    @Test
    public void notNullBeansTest() {
        assertThat(trie).isNotNull();
	assertThat(fileWorker).isNotNull();
    }
}