
package lapr.project.data.graphs.adjacencymatrix;

import java.util.LinkedList;

/**
 *
 * @author DEI-ESINF
 */
public class EdgeAsDoubleGraphAlgorithms {

    /**
     * Determine the shortest path to all vertices from a vertex using Dijkstra's algorithm
     * To be called by public short method
     * @param graph graph.Graph object
     * @param sourceIdx Source vertex
     * @param knownVertices previously discovered vertices
     * @param verticesIndex index of vertices in the minimum path
     * @param minDist minimum distances in the path
     *
     */
    private static <V> void shortestPath(AdjacencyMatrixGraph<V,Double> graph, int sourceIdx, boolean[] knownVertices, int[] verticesIndex, double [] minDist){
        int vkey = sourceIdx;
        minDist[vkey] = 0;
        while (vkey != -1) {
            V vOrig = graph.vertices.get(vkey);
            knownVertices[vkey] = true;
            for (V vAdj : graph.directConnections(vOrig)) {
                int vkeyAdj = graph.toIndex(vAdj);
                if (!knownVertices[vkeyAdj] && minDist[vkeyAdj] > minDist[vkey] + graph.getEdge(vOrig, vAdj)) {
                    minDist[vkeyAdj] = minDist[vkey] + graph.getEdge(vOrig, vAdj);
                    verticesIndex[vkeyAdj] = vkey;
                }
            }
            double minDistance = Double.MAX_VALUE;
            vkey = -1;
            for (int i = 0; i < graph.numVertices; i++) {
                if (!knownVertices[i] && minDist[i] < minDistance) {
                    minDistance = minDist[i];
                    vkey = i;
                }
            }
        }
    }

    /**
     * Determine the shortest path between two vertices using Dijkstra's algorithm
     * @param graph graph.Graph object
     * @param source Source vertex
     * @param dest Destination vertices
     * @param path Returns the vertices in the path (empty if no path)
     * @return minimum distance, -1 if vertices not in graph or no path
     *
     */
    public static <V> double shortestPath(AdjacencyMatrixGraph<V, Double> graph, V source, V dest, LinkedList<V> path){

        int sourceIdx = graph.toIndex(source);
        if (sourceIdx == -1)
            return -1;

        int destIdx = graph.toIndex(dest);
        if (destIdx == -1)
            return -1;

        path.clear();

        boolean[] knownVertices = new boolean[graph.numVertices];
        int[] verticesIndex = new int[graph.numVertices];
        double[] minDist = new double[graph.numVertices];

        for (int i = 0; i < graph.numVertices; i++) {
            minDist[i] = Double.MAX_VALUE;
            verticesIndex[i] = -1;
        }

        shortestPath(graph, sourceIdx, knownVertices, verticesIndex, minDist);
        if (knownVertices[destIdx] == false)
            return -1;

        recreatePath(graph,sourceIdx,destIdx,verticesIndex,path);

        // recreatePath builds path in reverse order, so reverse
        LinkedList<V> stack = new LinkedList<V>();  //create a stack
        while (!path.isEmpty())
            stack.push(path.remove());

        while(!stack.isEmpty())
            path.add(stack.pop());

        return minDist[destIdx];
    }


    /**
     * Recreates the minimum path between two vertex, from the result of Dikstra's algorithm
     * @param graph graph.Graph object
     * @param sourceIdx Source vertex
     * @param destIdx Destination vertices
     * @param verticesIndex index of vertices in the minimum path
     * @param Queue Vertices in the path (empty if no path)
     */
    private static <V> void recreatePath(AdjacencyMatrixGraph<V, Double> graph, int sourceIdx,
                                           int destIdx, int[] verticesIndex, LinkedList<V> path){

        path.add(graph.vertices.get(destIdx));
        if (sourceIdx != destIdx){
            destIdx = verticesIndex[destIdx];
            recreatePath(graph, sourceIdx, destIdx, verticesIndex, path);
        }
    }

    public static <V> AdjacencyMatrixGraph<V, Double> matrizAdjFloydWarshall (AdjacencyMatrixGraph<V, Double> matrix) {
        @SuppressWarnings("unchecked") AdjacencyMatrixGraph<V, Double> cloneMatrix = (AdjacencyMatrixGraph<V, Double>) matrix.clone();
        int n = cloneMatrix.numVertices;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                if (i != k && cloneMatrix.privateGet(i,k) != null) {
                    for (int j = 0; j < n; j++) {
                        if (i != j && k != j && cloneMatrix.privateGet(k,j) != null) {
                            if (cloneMatrix.privateGet(i,j) == null || cloneMatrix.privateGet(i,k) + cloneMatrix.privateGet(k,j) < cloneMatrix.privateGet(i,j)) {
                                cloneMatrix.privateSet(i,j,cloneMatrix.privateGet(i,k) + cloneMatrix.privateGet(k,j));
                            }
                        }
                    }
                }
            }
        }
        return cloneMatrix;
    }



}
