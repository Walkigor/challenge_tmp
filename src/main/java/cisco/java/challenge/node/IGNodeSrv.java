package cisco.java.challenge.node;

import java.util.ArrayList;

//added according to recommendations
public interface IGNodeSrv {
    public ArrayList<GNode> walkGraph();            //whole graph
    public ArrayList<GNode> paths(char parentPath); //inside embedded path
}