package lapr.project.data.graphs.graphbase;/*
 * A collection of graph algorithms.
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author DEI-ESINF
 */

public class GraphAlgorithms {

    /**
     * Performs breadth-first search of a Graph starting in a Vertex
     *
     * @param g    Graph instance
     * @param vert information of the Vertex that will be the source of the search
     * @return qbfs a queue with the vertices of breadth-first search
     */
    public static <V, E> LinkedList<V> BreadthFirstSearch(Graph<V, E> g, V vert) {
        LinkedList<V> qbfs = new LinkedList<>();
        LinkedList<V> qaux = new LinkedList<>();

        if (!g.validVertex(vert))
            return null;

        qbfs.add(vert);
        qaux.add(vert);

        while (!qaux.isEmpty()) {
            vert = qaux.removeFirst();
            for (V vAdj : g.adjVertices(vert)) {
                if (!qbfs.contains(vAdj)) {
                    qbfs.add(vAdj);
                    qaux.add(vAdj);
                }
            }
        }
        return qbfs;
    }

    /**
     * Performs depth-first search starting in a Vertex
     *
     * @param g       Graph instance
     * @param vOrig   Vertex of graph g that will be the source of the search
     * @param visited set of discovered vertices
     * @param qdfs    queue with vertices of depth-first search
     */
    private static <V, E> void DepthFirstSearch(Graph<V, E> g, V vOrig, boolean[] visited, LinkedList<V> qdfs) {
        if (g.validVertex(vOrig)) {
            if (!visited[g.getKey(vOrig)]) {
                visited[g.getKey(vOrig)] = true;
                qdfs.add(vOrig);
                for (V vertex : g.adjVertices(qdfs.getLast())) {
                    if (!qdfs.contains(vertex)) {
                        DepthFirstSearch(g, vertex, visited, qdfs);
                    }
                }
            }
        }
    }

    /**
     * @param g    Graph instance
     * @param vert information of the Vertex that will be the source of the search
     * @return qdfs a queue with the vertices of depth-first search
     */
    public static <V, E> LinkedList<V> DepthFirstSearch(Graph<V, E> g, V vert) {
        if (!g.validVertex(vert)) {
            return null;
        }
        LinkedList<V> listaQdfs = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        DepthFirstSearch(g, vert, visited, listaQdfs);
        return listaQdfs;
    }

    /**
     * Returns all paths from vOrig to vDest
     *
     * @param g       Graph instance
     * @param vOrig   Vertex that will be the source of the path
     * @param vDest   Vertex that will be the end of the path
     * @param visited set of discovered vertices
     * @param path    stack with vertices of the current path (the path is in reverse order)
     * @param paths   ArrayList with all the paths (in correct order)
     */
    private static <V, E> void allPaths(Graph<V, E> g, V vOrig, V vDest, boolean[] visited,
                                        LinkedList<V> path, ArrayList<LinkedList<V>> paths) {
        if (!visited[g.getKey(vOrig)]) {
            visited[g.getKey(vOrig)] = true;
            path.push(vOrig);
            for (V vertice : g.adjVertices(vOrig)) {
                if (vertice == vDest) {
                    path.push(vDest);
                    paths.add(path);
                    path.pop();
                } else {
                    if (!path.contains(vertice)) {
                        allPaths(g, vertice, vDest, visited, path, paths);
                    }
                }
            }
            path.pop();
            visited[g.getKey(vOrig)] = false;
        }
    }

    /**
     * @param g     Graph instance
     * @param vOrig information of the Vertex origin
     * @param vDest information of the Vertex destination
     * @return paths ArrayList with all paths from vOrig to vDest
     */
    public static <V, E> ArrayList<LinkedList<V>> allPaths(Graph<V, E> g, V vOrig, V vDest) {
        if (!(g.validVertex(vOrig) && g.validVertex(vDest))) {
            return new ArrayList<>();
        }
        ArrayList<LinkedList<V>> paths = new ArrayList<>();
        LinkedList<V> path = new LinkedList<>();
        boolean[] visited = new boolean[g.numVertices()];
        allPaths(g, vOrig, vDest, visited, path, paths);
        return paths;
    }

    /**
     * Computes shortest-path distance from a source vertex to all reachable
     * vertices of a graph g with nonnegative edge weights
     * This implementation uses Dijkstra's algorithm
     *
     * @param g        Graph instance
     * @param vOrig    Vertex that will be the source of the path
     * @param visited  set of discovered vertices
     * @param pathKeys minimum path vertices keys
     * @param dist     minimum distances
     */
    protected static <V, E> void shortestPathLength(Graph<V, E> g, V vOrig, V[] vertices,
                                                    boolean[] visited, int[] pathKeys, double[] dist) {
        int vkey = g.getKey(vOrig);
        dist[vkey] = 0;
        while (vkey != -1) {
            vOrig = vertices[vkey];
            visited[vkey] = true;
            for (V vAdj : g.adjVertices(vOrig)) {
                int vkeyAdj = g.getKey(vAdj);
                Edge<V, E> edge = g.getEdge(vOrig, vAdj);
                if (!visited[vkeyAdj] && dist[vkeyAdj] > dist[vkey] + edge.getWeight()) {
                    dist[vkeyAdj] = dist[vkey] + edge.getWeight();
                    pathKeys[vkeyAdj] = vkey;
                }
            }
            double minDist = Double.MAX_VALUE;
            vkey = -1;
            for (int i = 0; i < g.numVertices(); i++) {
                if (!visited[i] && dist[i] < minDist) {
                    minDist = dist[i];
                    vkey = i;
                }
            }
        }
    }

    /**
     * Extracts from pathKeys the minimum path between vOrig and vDest
     * The path is constructed from the end to the beginning
     *
     * @param g        Graph instance
     * @param vOrig    information of the Vertex origin
     * @param vDest    information of the Vertex destination
     * @param pathKeys minimum path vertices keys
     * @param path     stack with the minimum path (correct order)
     */
    private static <V, E> void getPath(Graph<V, E> g, V vOrig, V vDest, V[] verts, int[] pathKeys, List<V> path) {

        if (!vOrig.equals(vDest)) {
            path.add(vDest);
            int vKey = g.getKey(vDest);
            int prevVKey = pathKeys[vKey];
            vDest = verts[prevVKey];
            getPath(g, vOrig, vDest, verts, pathKeys, path);
        } else {
            path.add(vOrig);
        }
    }

    //shortest-path between voInf and vdInf
    public static <V, E> double shortestPath(Graph<V, E> g, V vOrig, V vDest, List<V> shortPath) {

        if (!g.validVertex(vOrig) || !g.validVertex(vDest))
            return -1;

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] vertices = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);

        double lengthPath = dist[g.getKey(vDest)];

        if (lengthPath != Double.MAX_VALUE) {
            getPath(g, vOrig, vDest, vertices, pathKeys, shortPath);
            return lengthPath;
        }
        return -1;
    }

    //shortest-path between vOrig and all other
    public static <V, E> boolean shortestPaths(Graph<V, E> g, V vOrig, List<LinkedList<V>> paths, ArrayList<Double> dists) {

        if (!g.validVertex(vOrig)) return false;

        int nverts = g.numVertices();
        boolean[] visited = new boolean[nverts]; //default value: false
        int[] pathKeys = new int[nverts];
        double[] dist = new double[nverts];
        V[] vertices = g.allkeyVerts();

        for (int i = 0; i < nverts; i++) {
            dist[i] = Double.MAX_VALUE;
            pathKeys[i] = -1;
        }

        shortestPathLength(g, vOrig, vertices, visited, pathKeys, dist);

        dists.clear();
        paths.clear();
        for (int i = 0; i < nverts; i++) {
            paths.add(null);
            dists.add(null);
        }
        for (int i = 0; i < nverts; i++) {
            LinkedList<V> shortPath = new LinkedList<>();
            if (dist[i] != Double.MAX_VALUE)
                getPath(g, vOrig, vertices[i], vertices, pathKeys, shortPath);
            paths.set(i, shortPath);
            dists.set(i, dist[i]);
        }
        return true;
    }

    /**
     * Reverses the path
     *
     * @param path stack with path
     */
    private static <V, E> LinkedList<V> revPath(LinkedList<V> path) {

        LinkedList<V> pathcopy = new LinkedList<>(path);
        LinkedList<V> pathrev = new LinkedList<>();

        while (!pathcopy.isEmpty())
            pathrev.push(pathcopy.pop());

        return pathrev;
    }

    public static <V, E> LinkedList<V> shortestCycleFromVertex(Graph<V, E> graph, V vOrig, V vAtual, LinkedList<V> path, boolean[] visited) {
        if (path.size() == 0 || vOrig != vAtual) {
            for (Edge<V, E> edge : graph.outgoingEdgesSortedByWeight(vAtual)) {
                V vDest = edge.getVDest();
                if (!visited[graph.getKey(vDest)]) {
                    if (!vDest.equals(vOrig)) {
                        graph.removeEdge(vAtual, vDest);
                        graph.removeEdge(vDest, vAtual);
                        path.add(vDest);
                        visited[graph.getKey(vDest)] = true;
                        return shortestCycleFromVertex(graph, vOrig, vDest, path, visited);

                    } else {
                        path.add(vDest);
                        return path;
                    }
                }
            }
        }
        return path;
    }

}