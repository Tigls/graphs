package util

import graph.*

object GraphParsing {
    fun readMatrix(): Array<IntArray> {
        println("How big is the matrix NxM, where N - number of rows, M - number of columns")
        val size = readLine()!!.split("x")
        val rows = size[0].toInt()
        val cols = size[2].toInt()
        val matrix: Array<IntArray> = Array(rows){ IntArray(cols) {0} }
        for (i in 0 until rows) {
            val row = readLine()!!.split(" ")
            val rowInt = row.map { it.toInt() }.toIntArray()
            matrix[i] = rowInt
        }
        return matrix
    }

    fun adjMatrixToGraph(matrix: Array<IntArray>): Graph {
        val graph = Graph.createEmpty()
        matrix.indices.forEach { graph.addVertex(it.toString())}
        for (i in matrix.indices) for (j in matrix[i].indices) {
            if (matrix[i][j] > 0) {
                graph.addEdge(i.toString(), j.toString())
            }
        }
        return graph
    }

    fun incMatrixToGraph(matrix: Array<IntArray>): Graph {
        val graph = Graph.createEmpty()
        matrix.indices.forEach { graph.addVertex(it.toString())}
        val m = matrix.indices
        val n = matrix[0].indices
        val transposedMatrix = transposeMatrix(matrix)

        for (i in n) {
            val map = LinkedHashMap<Int, Int>()
            for (j in m) {
                val value = transposedMatrix[i][j]
                if (value > 0 || value < 0) {
                    map[j] = value
                }
            }
            val isDirected = map.filterValues { it > 0 }.size == 1 && map.filterValues { it < 0 }.size == 1
            val isUnDirected = map.filterValues { it > 0 }.size == 2
            when {
                isDirected -> {
                    var sourceId = ""
                    var targetId = ""
                    map.keys.forEach {
                        if (map[it]!! < 0) {
                            sourceId = it.toString()
                        } else {
                            targetId = it.toString()
                        }
                    }
                    graph.addEdge(sourceId, targetId)
                }
                isUnDirected -> {
                    val iterator = map.entries.iterator()
                    val first = iterator.next()
                    val second = iterator.next()
                    graph.addEdge(first.key.toString(), second.key.toString())
                    graph.addEdge(second.key.toString(), first.key.toString())
                }
                else -> {
                    println("Wrong values in incidence matrix. Empty graph created.")
                    return Graph.createEmpty()
                }
            }
        }
        return graph
    }

    private fun transposeMatrix(matrix: Array<IntArray>): Array<IntArray> {
        val m = matrix.size
        val n = matrix[0].size
        val transposedMatrix = Array(n) { IntArray(m) {0} }
        for (i in 0 until n) for (j in 0 until m) {
            transposedMatrix[i][j] = matrix[j][i]
        }
        return transposedMatrix
    }
}