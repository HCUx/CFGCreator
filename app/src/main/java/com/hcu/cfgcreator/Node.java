package com.hcu.cfgcreator;

import java.util.List;

/**
 * Created by stava on 29.12.2017.
 */

public class Node {
    List<Node> nodelist;
    String value;

    public List<Node> getNodelist() {
        return nodelist;
    }

    public void addNodeListItem(Node node) {
        this.nodelist.add(node);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
