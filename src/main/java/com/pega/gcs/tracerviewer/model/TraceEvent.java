import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

public class Solution {

    public static class DirectedGraph {
        /* Adjacency List representation of the given graph */
        private Map<Integer, List<Integer>> adjList = new HashMap<Integer, List<Integer>>();

        public String toString() {
            StringBuffer s = new StringBuffer();
            for (Integer v : adjList.keySet())
                s.append("\n    " + v + " -> " + adjList.get(v));
            return s.toString();
        }

        public void add(Integer vertex) {
            if (adjList.containsKey(vertex))
                return;
            adjList.put(vertex, new ArrayList<Integer>());
        }

        public void add(Integer source, Integer dest) {
            add(source);
            add(dest);
            adjList.get(source).add(dest);
        }

        /* Indegree of each vertex as a Map<Vertex, IndegreeValue> */
        public Map<Integer, Integer> inDegree() {
            Map<Integer, Integer> result = new HashMap<Integer, Integer>();
            for (Integer v : adjList.keySet())
                result.put(v, 0);
            for (Integer from : adjList.keySet()) {
                for (Integer to : adjList.get(from)) {
                    result.put(to, result.get(to) + 1);
                }
            }
            return result;
        }

        public Map<Integer,Integer> outDegree () {
            Map<Integer,Integer> result = new HashMap<Integer,Integer>();
            for (Integer v: adjList.keySet()) result.put(v, adjList.get(v).size());
            return result;
        }
            
    }

   // Complete the isDAG function below.
    public static boolean isDag(DirectedGraph digraph) {
        List res = new ArrayList<>();
        Stack stack = new Stack<>();
        Map map = digraph.inDegree();
        for (Object v : map.keySet()) {
            if ((int) map.get(v) == 0) {
                stack.push(v);
            }
        }
        while (!stack.isEmpty()) {
            Object v = stack.pop();
            res.add(v);
            for (int i = 0; i < digraph.adjList.get(v).size(); i++) {
                int item = digraph.adjList.get(v).get(i);
                map.put(item, (int) map.get(item) - 1);
                if ((int) map.get(item) == 0) {
                    stack.push(item);
                }
            }
        }
        if (res.size() == digraph.adjList.size()) {
            return true;
        } else {
            return false;
        }
    }


    public static void main(String[] args) throws IOException {
        
      	BufferedWriter bufferredWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        BufferedReader bufferredReader = new BufferedReader(new InputStreamReader(System.in));

        DirectedGraph digraph = new DirectedGraph();
        
        String line;
        while ((line = bufferredReader.readLine()) != null) {
            String[] v = line.split(" ");
            digraph.add(Integer.parseInt(v[0]), Integer.parseInt(v[1]));
        }

		bufferredWriter.write((isDag(digraph) ? "1" : "0"));
      
        bufferredReader.close();
      	bufferredWriter.close();
    }
}
