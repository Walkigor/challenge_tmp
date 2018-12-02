package cisco.java.challenge.node;

import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
public class GNode implements IGNode, Comparable<GNode> {
    @Getter @Setter protected char name;
    @Setter protected SortedSet<GNode> children;
    @Getter @Setter protected int word;
    
    public GNode(char name) {
        this.name = name;
    }
    
    public GNode(char name, boolean word) {
        this.name = name;
        this.word = word ? 1 : 0;
        this.children = new ConcurrentSkipListSet<>(); //new TreeSet<>();
    }

    public SortedSet<GNode> getChildrenSet() {
        return this.children;
    }
    
    @Override
    public GNode[] getChildren() {
        return this.children.toArray(new GNode[0]);
    }
    
    @Override
    public int compareTo(GNode node) {
        return this.name - node.getName();
    }
}