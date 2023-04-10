package com.deezzex.alghorithm;

import com.deezzex.model.Edge;
import com.deezzex.model.GraphVertice;
import com.deezzex.model.Node;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@NoArgsConstructor
public class SalesmanProblemSolver {
    @Getter
    private Integer lengthOfRoute = 0;

    public int[] solveByChristofides(double[][] inputMatrix) {
        long start = System.currentTimeMillis();

        int[] mst = findMSTByPrim(inputMatrix, inputMatrix[0].length);

        int[][] matchMatrix = findMatchMatrix(mst, inputMatrix, inputMatrix[0].length);

        GraphVertice[] graphVertices = buildMultiGraph(matchMatrix, mst);

        int[] route = getEulerRoute(graphVertices);

        double sum = 0.0;

        for (int i = 1; i < route.length; i++) {
            sum += inputMatrix[route[i - 1]][route[i]];
        }

        sum += inputMatrix[route[0]][route[route.length - 1]];

        lengthOfRoute = (int) sum;

        log.info("Final length of route: {}", lengthOfRoute);

        long end = System.currentTimeMillis();

        log.info("Time spent: {} ms.", (end - start));

        return route;
    }

    private int[] getEulerRoute(GraphVertice[] vertices) {
        List<Integer> path = new LinkedList<>();
        Vector<Integer> tmpPath = new Vector<>();
        int j = 0;

        vertices[0].getNextChild(vertices[0].getIdentifier(), tmpPath, true);
        path.addAll(0, tmpPath);

        while (j < path.size()) {
            if (vertices[path.get(j)].hasMoreChildren()) {
                vertices[path.get(j)].getNextChild(vertices[path.get(j)].getIdentifier(), tmpPath, true);
                if (tmpPath.size() > 0) {
                    for (int i = 0; i < path.size(); i++) {
                        if (path.get(i).intValue() == tmpPath.elementAt(0).intValue()) {
                            path.addAll(i, tmpPath);
                            break;
                        }
                    }
                    tmpPath.clear();
                }
                j = 0;
            } else {
                j++;
            }
        }

        boolean[] inPath = new boolean[vertices.length];
        int[] route = new int[vertices.length];
        j = 0;
        for (Integer integer : path) {
            if (!inPath[integer]) {
                route[j] = integer;
                j++;
                inPath[integer] = true;
            }
        }

        return route;
    }


    private GraphVertice[] buildMultiGraph(int[][] matchMatrix, int[] mst) {
        GraphVertice[] vertices = new GraphVertice[mst.length];

        for (int i = 0; i < mst.length; i++) {
            vertices[i] = new GraphVertice(i);
        }

        for (int i = 1; i < mst.length; i++) {
            vertices[i].addChild(vertices[mst[i]]);
            vertices[mst[i]].addChild(vertices[i]);
        }

        for (int[] ints : matchMatrix) {
            vertices[ints[0]].addChild(vertices[ints[1]]);
            vertices[ints[1]].addChild(vertices[ints[0]]);
            log.info("Multi graph edge {} - {}", ints[0], ints[1]);
        }

        return vertices;
    }

    public int[] findMSTByPrim(double[][] wt, int dim) {
        Vector<Integer> queue = new Vector<>();
        for (int i = 0; i < dim; i++) {
            queue.add(i);
        }

        boolean[] isInTree = new boolean[dim];
        double[] key = new double[dim];
        int[] p = new int[dim];

        for (int i = 0; i < dim; i++) {
            key[i] = Integer.MAX_VALUE;
        }

        key[0] = 0;
        int u = 0;

        double temp;
        Integer elem;
        do {
            isInTree[u] = true;
            queue.removeElement(u);
            for (int v = 0; v < dim; v++) {
                if (!isInTree[v] && wt[u][v] < key[v]) {
                    p[v] = u;
                    key[v] = wt[u][v];
                }
            }

            double mint = Double.MAX_VALUE;
            for (int i = 0; i < queue.size(); i++) {
                elem = queue.elementAt(i);
                temp = key[elem];
                if (temp < mint) {
                    u = elem;
                    mint = temp;
                }
            }
        } while (!queue.isEmpty());

        log.info("Parent vector:  {}", Arrays.toString(p));

        double sum = 0;
        for (int g = 0; g < dim; g++) {
            sum += key[g];
        }

        log.info("Length of key vector: {}", sum);

        return p;
    }


    private int[][] findMatchMatrix(int[] mst, double[][] inputMatrix, int size) {
        Node[] nodes = new Node[mst.length];

        nodes[0] = new Node(0, true);
        for (int i = 1; i < mst.length; i++) {
            nodes[i] = new Node(i, false);
        }

        for (int i = 0; i < mst.length; i++) {
            if (mst[i] != i)
                nodes[mst[i]].addChild(nodes[i]);
        }

        List<Integer> oddDegreeNodes = findOddDegreeNodes(nodes[0]);
        int nOdd = oddDegreeNodes.size();

        log.info("Odd nodes: {}", Arrays.toString(oddDegreeNodes.toArray()));

        Edge[][] edges = new Edge[nOdd][nOdd];
        for (int i = 0; i < nOdd; i++) {
            for (int j = 0; j < nOdd; j++) {
                if (oddDegreeNodes.get(i).intValue() != oddDegreeNodes.get(j).intValue()) {
                    edges[i][j] = new Edge(oddDegreeNodes.get(i), oddDegreeNodes.get(j), inputMatrix[oddDegreeNodes.get(i)][oddDegreeNodes.get(j)]);
                } else {
                    edges[i][j] = new Edge(oddDegreeNodes.get(i), oddDegreeNodes.get(j), Double.MAX_VALUE);
                }

            }
            Arrays.sort(edges[i]);
        }

        boolean[] matched = new boolean[size];
        int[][] match = new int[(nOdd / 2)][2];

        int k = 0;
        for (int i = 0; i < nOdd; i++) {
            for (int j = 0; j < nOdd; j++) {
                if (matched[edges[i][j].getFrom()] || matched[edges[i][j].getTo()]) {
                    continue;
                } else {
                    matched[edges[i][j].getFrom()] = true;
                    matched[edges[i][j].getTo()] = true;
                    match[k][0] = edges[i][j].getFrom();
                    match[k][1] = edges[i][j].getTo();
                    k++;
                }
            }
        }

        for (int i = 0; i < nOdd / 2; i++) {
            log.info("Matching: {} = {}", match[i][0], match[i][1]);
        }

        return match;
    }

    private List<Integer> findOddDegreeNodes(Node root) {
        List<Integer> oddNodes = new ArrayList<>();
        root.visitFindOddDegreeNodes(oddNodes);
        return oddNodes;
    }
}