package graph

import AdjList
import util.GraphParsing
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.pow
import kotlin.random.Random

class Graph (
    private val vertices: MutableList<Vertex> = mutableListOf(),
    private val edges: MutableList<Edge> = mutableListOf(),
    private val adjList: AdjList = HashMap(),
    private var source: Vertex? = null,
    private var dest: Vertex? = null) {
    companion object Factory {
        fun createEmpty(): Graph {
            return Graph()
        }
        fun generate(v: Int, edg: Int, directed: Boolean): Graph {
            var edges = if (directed) edg else edg/2
            val matrix = Array(v) { IntArray(v) {0} }
            while (edges > 0) {
                val random = Random.nextFloat()
                val n = (v.toFloat().pow(2) * random).toInt()
                val i = n / v
                val j = n - i * v
                if (matrix[i][j] != 1 && i != j) {
                    matrix[i][j] = 1
                    if (!directed) {
                        matrix[j][i] = 1
                    }
                    edges--
                }
            }
            return GraphParsing.adjMatrixToGraph(matrix)
        }
    }

    fun getVertices(): MutableList<Vertex> {
        return vertices
    }

    private fun findVertexById(id: String): Vertex? {
        return vertices.find { it.id == id }
    }

    private fun getVertexIndex(vertex: Vertex): Int {
        return vertices.indexOf(vertex)
    }

    fun addVertex(id: String): Vertex {
        val vertex = findVertexById(id) ?: Vertex(id)
        vertices.add(vertex)
        adjList[vertex] = mutableListOf()
        return vertex
    }

    fun addEdge(sourceId: String, targetId: String) {
        val source = vertices.find { it.id == sourceId }
        val target = vertices.find { it.id == targetId }
        if (source != null && target != null) {
            edges.add(Edge("1", source, target))
            val connectedVertices = adjList[source]!!
            connectedVertices.add(target)
        } else {
            println("No such sourceId or targetId")
        }
    }

    fun setSourceDest(id: String, isSource: Boolean) {
        val vertex = findVertexById(id)
        if (vertex == null) {
            println("No vertex with such id found")
            return
        }
        if (isSource) {
            this.source = vertex
        } else {
            this.dest = vertex
        }
    }

    fun getSource(): Vertex? {
        return this.source
    }

    fun getDest(): Vertex? {
        return this.dest
    }

    // генерація матриці відстаней, запускає пошук в ширину для кожної вершини
    fun distanceMatrix(): Array<FloatArray> {
        val size = vertices.size
        val distanceMatrix = Array (size){ FloatArray (size) {Float.POSITIVE_INFINITY} }
        for (sourceVertex in vertices) {
            val distanceMap = bfs(sourceVertex)
            val vertexIndex = getVertexIndex(sourceVertex)
            distanceMap.entries.forEach {
                val destIndex = getVertexIndex(it.key)
                distanceMatrix[vertexIndex][destIndex] = it.value
            }
        }
        return distanceMatrix
    }

    // алгоритм пошуку в ширину, breadth first search або bfs
    private fun bfs(initialVertex: Vertex): Map<Vertex, Float> {
        val explored = mutableSetOf(initialVertex)
        val queue: Queue<Vertex> = LinkedList(explored)
        val map = hashMapOf(initialVertex to 0.toFloat())
        while (queue.isNotEmpty()) {
            val vertex = queue.poll()
            val edgeVertices = adjList[vertex]!!
            edgeVertices.forEach {
                if (!explored.contains(it)) {
                    explored.add(it)
                    queue.offer(it)
                    map[it] = map[vertex]!! + 1
                }
            }
        }
        return map
    }

    fun dfsIterative(source: Vertex, dest: Vertex): List<Vertex> {
        val stack: LinkedList<Vertex> = LinkedList(listOf(source))
        val path: LinkedList<Vertex> = LinkedList()
        val explored = mutableSetOf<Vertex>()
        while (!stack.isEmpty()) {
            val vertex = stack.pop()
            val connectedVertices = adjList[vertex]!!.filter { !explored.contains(it) }
            if (!explored.contains(vertex)) {
                explored.add(vertex)
                path.offerLast(vertex)
                stack.push(vertex)
                for (adjVertex in connectedVertices.reversed()) {
                    stack.push(adjVertex)
                }
            } else {
                path.pollLast()
            }
            if (vertex == dest) break
        }
        return path
    }

    fun dfsRecursive(source: Vertex, dest: Vertex): List<Vertex> {
        fun dfsRecursiveImp(source: Vertex, path: List<Vertex>, explored: Set<Vertex>): List<Vertex> {
            if (source == dest) {
                return path
            } else {
                val connectedVertices = adjList[source]!!.filter { !explored.contains(it) }
                if (connectedVertices.isEmpty()) return path.take(1)
                return connectedVertices.fold(path) { acc, vertex ->
                    dfsRecursiveImp(vertex, acc.plus(vertex), explored.plus(source))
                }
            }
        }
        return dfsRecursiveImp(source, listOf(source), setOf())
    }

    override fun toString(): String {
        val strBuilder = StringBuilder()
        strBuilder.appendln("Кількість вершин: ${vertices.size}")
        strBuilder.appendln("Кількість ребер: ${edges.size}")
        strBuilder.appendln("Список суміжності графу:")
        for (adjElem in adjList.entries.sortedBy { it.key.id }) {
            strBuilder.append("${adjElem.key.id}:")
            for (connectedVertex in adjElem.value) {
                strBuilder.append(" -> ${connectedVertex.id} ")
            }
            strBuilder.appendln()
        }
        return strBuilder.toString()
    }
}

