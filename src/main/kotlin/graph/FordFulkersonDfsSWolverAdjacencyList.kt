package graph

import INF

/**
 * An implementation of the Ford-Fulkerson (FF) method with a DFS as a method of finding augmenting
 * paths. FF allows you to find the max flow through a directed graph and the min cut as a
 * byproduct.
 *
 *
 * Time Complexity: O(fE), where f is the max flow and E is the number of edges
 *
 */
class FordFulkersonDfsSolverAdjacencyList (n: Int, s: Int, t: Int) : NetworkFlowSolverBase(n, s, t) {
    // Performs the Ford-Fulkerson method applying a depth first search as
    // a means of finding an augmenting path.
    override fun solve() {

        // Find max flow by adding all augmenting path flows.
        var f = dfs(s, INF)
        while (f != 0L) {
            markAllNodesAsUnvisited()
            maxFlow += f
            f = dfs(s, INF)
        }

        // Find min cut.
        for (i in 0 until n) if (visited(i)) minCut[i] = true
    }

    private fun dfs(node: Int, flow: Long): Long {
        // At sink node, return augmented path flow.
        if (node == t) return flow
        val edges: MutableList<Edge?>? = graph[node]
        visit(node)
        for (edge in edges!!) {
            val rcap = edge!!.remainingCapacity()
            if (rcap > 0 && !visited(edge.to)) {
                val bottleNeck = dfs(edge.to, Math.min(flow, rcap))

                // Augment flow with bottle neck value
                if (bottleNeck > 0) {
                    edge.augment(bottleNeck)
                    return bottleNeck
                }
            }
        }
        return 0
    }

    companion object {
        private fun exampleFromSlides2() {
            val n = 12
            val s = n - 2
            val t = n - 1
            val solver: FordFulkersonDfsSolverAdjacencyList
            solver = FordFulkersonDfsSolverAdjacencyList(n, s, t)
            solver.addEdge(s, 1, 2)
            solver.addEdge(s, 2, 1)
            solver.addEdge(s, 0, 7)
            solver.addEdge(0, 3, 2)
            solver.addEdge(0, 4, 4)
            solver.addEdge(1, 4, 5)
            solver.addEdge(1, 5, 6)
            solver.addEdge(2, 3, 4)
            solver.addEdge(2, 7, 8)
            solver.addEdge(3, 6, 7)
            solver.addEdge(3, 7, 1)
            solver.addEdge(4, 5, 8)
            solver.addEdge(4, 8, 3)
            solver.addEdge(5, 8, 3)
            solver.addEdge(6, t, 1)
            solver.addEdge(7, t, 3)
            solver.addEdge(8, t, 4)
            println(solver.getMaxFlow())
            val g: Array<MutableList<Edge?>?> = solver.getGraphL()
            for (edges in g) {
                if (edges != null) {
                    for (e in edges) {
                        if (e?.to == s || e?.from == t) continue
                        if (e?.from == s || e?.to == t || e?.from!! < e.to) println(e.toString(s, t))
                        // System.out.println(e.residual.toString(s, t));
                    }
                }
            }
        }

        private fun exampleFromSlides() {
            val n = 6
            val s = n - 2
            val t = n - 1
            val solver: FordFulkersonDfsSolverAdjacencyList
            solver = FordFulkersonDfsSolverAdjacencyList(n, s, t)
            solver.addEdge(s, 1, 10)
            solver.addEdge(1, 3, 15)
            solver.addEdge(3, 0, 6)
            solver.addEdge(0, 2, 25)
            solver.addEdge(2, t, 10)
            solver.addEdge(s, 0, 10)
            solver.addEdge(3, t, 10)
            println(solver.getMaxFlow())
            val g: Array<MutableList<Edge?>?> = solver.getGraphL()
            for (edges in g) {
                if (edges != null) {
                    for (e in edges) {
                        println(e?.toString(s, t))
                        // System.out.println(e.residual.toString(s, t));
                    }
                }
            }
        }

        // Testing graph from:
        // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
        private fun testSmallFlowGraph() {
            val n = 6
            val s = n - 1
            val t = n - 2
            val solver: FordFulkersonDfsSolverAdjacencyList
            solver = FordFulkersonDfsSolverAdjacencyList(n, s, t)

            // Source edges
            solver.addEdge(s, 0, 10)
            solver.addEdge(s, 1, 10)

            // Sink edges
            solver.addEdge(2, t, 10)
            solver.addEdge(3, t, 10)

            // Middle edges
            solver.addEdge(0, 1, 2)
            solver.addEdge(0, 2, 4)
            solver.addEdge(0, 3, 8)
            solver.addEdge(1, 3, 9)
            solver.addEdge(3, 2, 6)
            println(solver.getMaxFlow()) // 19
        }

        fun exampleMultipleSources() {
            val n = 14
            val s = 0
            val t = 13
            val solver = FordFulkersonDfsSolverAdjacencyList(n, s, t)
            solver.addEdge(0, 1, INF)
            solver.addEdge(0, 2, INF)
            solver.addEdge(0, 3, INF)
            solver.addEdge(0, 4, INF)
            solver.addEdge(0, 5, INF)

            solver.addEdge(1, 6, 10)
            solver.addEdge(2, 6, 12)
            solver.addEdge(2, 7, 5)
            solver.addEdge(3, 7, 8)
            solver.addEdge(3, 8, 14)
            solver.addEdge(4, 8, 7)
            solver.addEdge(4, 9, 11)
            solver.addEdge(5, 9, 2)

            solver.addEdge(6, 10, 3)
            solver.addEdge(7, 10, 15)
            solver.addEdge(7, 11, 6)
            solver.addEdge(8, 11, 20)
            solver.addEdge(8, 12, 13)
            solver.addEdge(9, 12, 18)

            solver.addEdge(10, 13, INF)
            solver.addEdge(11, 13, INF)
            solver.addEdge(12, 13, INF)

            println(solver.getMaxFlow()) // 19
            val g: Array<MutableList<Edge?>?> = solver.getGraphL()
            for (edges in g) {
                if (edges != null) {
                    for (e in edges) {
                        if (e?.to == s || e?.from == t) continue
                        if (e?.from == s || e?.to == t || e?.from!! < e.to) println(e.toString(s, t))
                        // System.out.println(e.residual.toString(s, t));
                    }
                }
            }
        }
    }
}