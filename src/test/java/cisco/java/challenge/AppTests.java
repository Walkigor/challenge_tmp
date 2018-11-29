package cisco.java.challenge;

import cisco.java.challenge.cmd.Application;
import cisco.java.challenge.node.GNode;
import cisco.java.challenge.node.Trie;
import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("dev")
@ContextConfiguration(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTests {
 
    private Trie trie;
    @Before
    public void setup() {
        //arrange
        trie = new Trie();
        
        //act
        trie.insert("abc", trie.getRoot());
        trie.insert("abcd", trie.getRoot());
        trie.insert("abcde", trie.getRoot());
        trie.insert("abcdef", trie.getRoot());
        
        trie.insert("ghi", trie.getRoot());
        trie.insert("ghijkl", trie.getRoot());
        
        trie.insert("mnop", trie.getRoot());
        trie.insert("mnops", trie.getRoot());
        trie.insert("mnopst", trie.getRoot());
    }
    
    @Test
    public void contextLoads() { }
    
    @Test
    public void isExistNodeAfterInsertion() {
        //accert
        assertThat(trie.find("abc", trie.getRoot())).isTrue();
    }
    
    @Test
    public void isNotExistWithItsChildrenAfterDeletion() {
        //act
        trie.delete("mnops", trie.getRoot());
        
        //accert
        assertThat(trie.find("mnops", trie.getRoot())).isFalse();
        assertThat(trie.find("mnopst", trie.getRoot())).isFalse();
    }
    
    @Test
    public void getAllGNodesList() {
        //act        
        ArrayList<GNode> listAll = trie.walkGraph();
        ArrayList<GNode> listIns = trie.paths('a');
        
        //arrrange
        assertThat(listAll.isEmpty()).isFalse();
        assertThat(listIns.isEmpty()).isFalse();       
    }
}