package cisco.java.challenge.node;

import java.util.List;

public interface ITrie {
    void insert(String data, GNode root);
    boolean find(String data, GNode root);
    List<String> showllAllOcurrences();
}