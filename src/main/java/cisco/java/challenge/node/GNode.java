package cisco.java.challenge.node;

import java.util.Arrays;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode
public class GNode implements IGNode {
    protected char name;
    protected GNode[] children;
    protected int isLeaf; //boolean isLeaf;
        
    public GNode(char name, boolean leaf) {
        this.name = name;
        this.isLeaf = leaf ? 1 : 0;
        this.children = new GNode[26]; //ALPHABET 
        Arrays.setAll(this.children, i -> null);
    }
}