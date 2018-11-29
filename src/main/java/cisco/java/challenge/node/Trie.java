package cisco.java.challenge.node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public class Trie implements ITrie, IGNodeSrv {
    @Getter private final char rootKey = ' ';
    @Getter private final GNode root = new GNode(rootKey, false);
    private List<String> occurrences = new ArrayList<>();
    
    private int getIndex(char x) {
        return ((int) x) - ((int) 'a');
    }

    @Override
    public void insert(String data, GNode root) {
        if (data==null || data.length()==0)
            return;
        
        if (!Character.isLetter(data.charAt(0)))
            return;
        
        GNode child = root.getChildren()[getIndex(data.charAt(0))];
        if (child==null) {
            GNode node = new GNode(data.charAt(0), data.length()==1);            
            root.getChildren()[getIndex(data.charAt(0))] = node;
            if (data.length() > 1) {
                insert(data.substring(1, data.length()), node);
            }
        } else {
            if (data.length()==1) {
                child.setIsLeaf(child.getIsLeaf()+1); //setLeaf(true);
            } else {
                insert(data.substring(1, data.length()), child);
            }
        }
    }

    @Override
    public boolean find(String data, GNode root) {
        if (data==null || data.length()==0)
            return true;
        
        char x = data.charAt(0);

        GNode node = root.getChildren()[getIndex(x)];
        if (node==null)
            return false;
        else
            return data.length()==1
                    ? node.getIsLeaf()!=0 //isLeaf()
                    : find(data.substring(1, data.length()), node);
    }

    @Override
    public boolean delete(String data, GNode root) {
        if (data==null || data.length()==0)
            return false;

        char x = data.charAt(0);

        GNode node = root.getChildren()[getIndex(x)];
        if (node==null)
            return false;
        else {
            if (data.length()==1) {
                node.setIsLeaf(0); //setLeaf(false);
                boolean allNull=true;
                Arrays.setAll(node.getChildren(), i -> null);
   
                return allNull;
            }
            else {
                boolean delete = delete(data.substring(1, data.length()), node);
                if (delete) {
                    node.children[getIndex(x)] = null;
                    if(node.getIsLeaf()!=0) //isLeaf())
                        return false;                    
                    boolean allNull = true;
                    Arrays.setAll(node.getChildren(), i -> null);
                      
                    return allNull; 
                }
            }
        }
        return false;
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
            if (node.getIsLeaf()>0) //isLeaf())
                occurrences.add(
                        "\n".concat(
                                String.join(": ", old, String.valueOf(node.getIsLeaf()))
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
            if (node.getIsLeaf()>0)
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