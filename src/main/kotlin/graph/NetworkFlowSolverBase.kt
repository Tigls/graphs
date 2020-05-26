package graph

import java.util.*

abstract class NetworkFlowSolverBase(// Inputs: n = number of nodes, s = source, t = sink
        protected val n: Int, protected val s: Int, protected val t: Int) {
    class Edge @JvmOverloads constructor(var from: Int, var to: Int, val capacity: Long, var cost: Long = 0 /* unused */) {
        var residual: Edge? = null
        var flow: Long = 0
        val originalCost: Long
        fun isResidual(): Boolean {
            return capacity == 0L
        }

        fun remainingCapacity(): Long {
            return capacity - flow
        }

        fun augment(bottleNeck: Long) {
            flow += bottleNeck
            residual!!.flow -= bottleNeck
        }

        fun toString(s: Int, t: Int): String {
            val u = if (from == s) "s" else if (from == t) "t" else from.toString()
            val v = if (to == s) "s" else if (to == t) "t" else to.toString()
            return String.format(
                    "Edge %s -> %s | flow = %d | capacity = %d | is residual: %s",
                    u, v, flow, capacity, isResidual())
        }

        init {
            originalCost = cost
        }
    }

    protected var maxFlow: Long = 0
    protected var minCut: BooleanArray
    protected lateinit var graph: Array<MutableList<Edge?>?>

    // 'visited' and 'visitedToken' are variables used for graph sub-routines to
    // track whether a node has been visited or not. In particular, node 'i' was
    // recently visited if visited[i] == visitedToken is true. This is handy
    // because to mark all nodes as unvisited simply increment the visitedToken.
    private var visitedToken = 1
    private val visited: IntArray

    // Indicates whether the network flow algorithm has ran. We should not need to
    // run the solver multiple times, because it always yields the same result.
    private var solved = false

    // Construct an empty graph with n nodes including the source and sink nodes.
    private fun initializeGraph() {
        graph = arrayOfNulls(n)
        for (i in 0 until n) graph[i] = ArrayList()
    }

    /**
     * Adds a directed edge (and residual edge) to the flow graph.
     *
     * @param from - The index of the node the directed edge starts at.
     * @param to - The index of the node the directed edge ends at.
     * @param capacity - The capacity of the edge.
     */
    fun addEdge(from: Int, to: Int, capacity: Long) {
        require(capacity >= 0) { "Capacity < 0" }
        val e1 = Edge(from, to, capacity)
        val e2 = Edge(to, from, 0)
        e1.residual = e2
        e2.residual = e1
        graph[from]?.add(e1)
        graph[to]?.add(e2)
    }

    /** Cost variant of [.addEdge] for min-cost max-flow  */
    fun addEdge(from: Int, to: Int, capacity: Long, cost: Long) {
        val e1 = Edge(from, to, capacity, cost)
        val e2 = Edge(to, from, 0, -cost)
        e1.residual = e2
        e2.residual = e1
        graph[from]?.add(e1)
        graph[to]?.add(e2)
    }

    // Marks node 'i' as visited.
    fun visit(i: Int) {
        visited[i] = visitedToken
    }

    // Returns whether or not node 'i' has been visited.
    fun visited(i: Int): Boolean {
        return visited[i] == visitedToken
    }

    // Resets all nodes as unvisited. This is especially useful to do
    // between iterations of finding augmenting paths, O(1)
    fun markAllNodesAsUnvisited() {
        visitedToken++
    }

    /**
     * Returns the graph after the solver has been executed. This allow you to inspect the [ ][Edge.flow] compared to the [capacity] in each edge. This is useful if you want to
     * figure out which edges were used during the max flow.
     */
    fun getGraphL(): Array<MutableList<Edge?>?> {
        execute()
        return graph
    }

    // Returns the maximum flow from the source to the sink.
    fun getMaxFlow(): Long? {
        execute()
        return maxFlow
    }


    // Wrapper method that ensures we only call solve() once
    private fun execute() {
        if (solved) return
        solved = true
        solve()
    }

    // Method to implement which solves the network flow problem.
    abstract fun solve()

    init {
        initializeGraph()
        minCut = BooleanArray(n)
        visited = IntArray(n)
    }
}