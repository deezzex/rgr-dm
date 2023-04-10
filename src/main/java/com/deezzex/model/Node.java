package com.deezzex.model;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final Boolean isRoot;
    private final Integer number;
    private List<Node> children;

    public Node(int n, boolean isRoot) {
        number = n;
        children = null;
        this.isRoot = isRoot;
    }

    public void addChild(Node node) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(node);
    }

    public void visitFindOddDegreeNodes(List<Integer> oddNodes) {
        if (children == null) {
            oddNodes.add(number);
            return;
        }
        if (isRoot && children.size() % 2 != 0) {
            oddNodes.add(number);
        }
        if (!isRoot && children.size() % 2 == 0) {
            oddNodes.add(number);
        }
        for (Node child : children) {
            child.visitFindOddDegreeNodes(oddNodes);
        }
    }
}