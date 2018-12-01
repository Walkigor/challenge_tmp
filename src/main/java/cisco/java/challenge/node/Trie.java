package cisco.java.challenge.node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Trie implements ITrie, IGNodeSrv {
    @Getter private final char rootKey = ' ';
    @Getter private final GNode root = new GNode(rootKey, false);
    private List<String> occurrences = new ArrayList<>();
    
    private GNode findChild(List<GNode> children, char name) {
        int idx =  Collections.binarySearch(children, new GNode(name));
        return idx >=0
                ? children.get(idx)
                : null;
    }    

    @Override
    public void insert(String data, GNode root) {
        try {
            if (data==null || data.length()==0)
                return;

            if (!Character.isLetter(data.charAt(0)))
                return;

            GNode child = findChild(Arrays.asList(root.getChildren()), data.charAt(0));
            if (child==null) {
                GNode node = new GNode(data.charAt(0), data.length()==1);            
                root.getChildrenSet().add(node);
                if (data.length()>1)
                    insert(data.substring(1, data.length()), node);
            } else {
                if (data.length()==1)
                    child.setWord(child.getWord()+1);
                else
                    insert(data.substring(1, data.length()), child);            
            }
        } catch (Exception ex) {
            log.error("{}", ex);
            //ex.printStackTrace();
        }
    }

    @Override
    public boolean find(String data, GNode root) {
        try {
            if (data==null || data.length()==0)
                return true;

            GNode node = findChild(Arrays.asList(root.getChildren()), data.charAt(0));
            if (node==null)
                return false;
            else
                return data.length()==1
                        ? node.getWord()!=0
                        : find(data.substring(1, data.length()), node);
        } catch (Exception ex) {
            log.error("{}", ex);
            return false;
        }
    }
    
    @Override
    public List<String> showllAllOcurrences() {
        occurrences = new ArrayList<>();
        findAllDFS(root, "");
        
        return occurrences;
    }

    private void findAllDFS(GNode node, String old) { //Depth First Search        
        if (node != null) {
            if (node.getName()!=rootKey)
                old += node.getName();
            if (node.getWord()>0)
                occurrences.add(
                        "\n".concat(
                                String.join(": ", old, String.valueOf(node.getWord()))
                        )
                );              
            
            for (GNode item : node.getChildren())
                findAllDFS(item, old);
        }
    }
    
    /* ************ */
    private ArrayList<GNode> findAllNodes(GNode node, char path) {  
        ArrayList<GNode> list = new ArrayList<>();
        if (node != null) {
            if (node.getName()!=rootKey)
                path += node.getName();
            if (node.getWord()>0)
                list.add(node);
            
            for (GNode item : node.getChildren())
                list.addAll(findAllNodes(item, path));
        }
        return list;
    }

    @Override
    public ArrayList<GNode> walkGraph() {
        return findAllNodes(root, rootKey);
    }

    @Override
    public ArrayList<GNode> paths(char parentPath) {
        GNode lvl1ChildNode = Arrays.stream(root.getChildren()).filter(it -> it.getName()==parentPath).findFirst().orElse(null);
        if (lvl1ChildNode==null)
            return new ArrayList<>();        
        return findAllNodes(lvl1ChildNode, parentPath);
    }
}