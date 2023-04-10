package com.deezzex.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class GraphVertice {
    private final List<GraphVertice> childVertices;
    @Getter
    private final Integer identifier;

    public GraphVertice(int identifier) {
        this.identifier = identifier;
        childVertices = new ArrayList<>();
    }


    public void addChild(GraphVertice vertice) {
        if (!(Objects.equals(this.getIdentifier(), vertice.getIdentifier()))) {
            childVertices.add(vertice);
        }
    }

    public void removeChild(GraphVertice node) {
        childVertices.remove(node);
    }

    public boolean hasMoreChildren() {
        return childVertices.size() > 0;
    }

    public void getNextChild(int goal, Vector<Integer> path, boolean firstTime) {

        if (this.getIdentifier() == goal && !firstTime) {
            path.add(this.getIdentifier());
        } else {
            if (childVertices.size() > 0) {
                GraphVertice tmpNode = childVertices.remove(0);
                tmpNode.removeChild(this);
                path.add(this.getIdentifier());
                tmpNode.getNextChild(goal, path, false);
            }
        }
    }
}