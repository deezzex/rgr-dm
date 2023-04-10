package com.deezzex.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Edge implements Comparable<Edge> {
    private final Integer from;
    private final Integer to;
    private final Double cost;

    @Override
    public int compareTo(Edge o) {
        return Double.compare(this.cost, o.cost);
    }
}