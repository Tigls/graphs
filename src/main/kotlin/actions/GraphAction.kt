package actions

import graph.FordFulkersonDfsSolverAdjacencyList
import graph.Graph
import util.GraphParsing.adjMatrixToGraph
import util.GraphParsing.incMatrixToGraph
import util.GraphParsing.readMatrix
import util.printMatrix

object GraphAction {
    fun addFromUserInput() {
        println("Enter matrix type (1 - adjacency, 2 - incidence):")
        when (readLine()!!) {
            "1" -> {
                val inputMatrix = readMatrix()
                State.currentGraph = adjMatrixToGraph(inputMatrix)
            }
            "2" -> {
                val inputMatrix = readMatrix()
                State.currentGraph = incMatrixToGraph(inputMatrix)
            }
            else -> {
                println("Wrong matrix type. Returning to main menu...")
                return
            }
        }
        State.graphs.add(State.currentGraph)
        println(State.currentGraph)
    }

    fun addVertex() {
        println("Enter the vertex id:")
        val id = readLine()!!
        State.currentGraph.addVertex(id)
        println("The vertex has been added")
    }

    fun printDistanceMatrix() {
        val matrix = State.currentGraph.distanceMatrix()
        printMatrix(matrix)
    }

    fun generateGraph() {
        println("Enter number of vertices, edges and graph type (0-undirected, 1-directed) Example: 6 8 1")
        val input = readLine()!!
        val data = input.split(" ")
        if (data.size == 3) {
            State.currentGraph = Graph.generate(data[0].toInt(), data[1].toInt(), data[2].toBoolean())
            println()
            println(State.currentGraph)
        } else {
            println("Wrong format of settings. Need: vertices edges type. Returning to main menu...")
            return
        }
    }

    fun inputSourceDest() {
        println("Input id of a source vertex: ")
        val sourceId = readLine()!!
        State.currentGraph.setSourceDest(sourceId, true)
        println("Input id of a destination vertex: ")
        val destId = readLine()!!
        State.currentGraph.setSourceDest(destId, false)
        println("Source and Dest vertices have been set")
    }

    fun calcPathTerry() {
        val path = State.currentGraph.dfsIterative(State.currentGraph.getSource()!!, State.currentGraph.getDest()!!)
        println("Result of the Terry algorithm:")
        path.forEach {
            if (path.last() == it) print("${it.id} ") else print("${it.id} -> ")
        }
        println()
    }

    fun executeEx1NetworkGraph() {
        FordFulkersonDfsSolverAdjacencyList.exampleMultipleSources()
    }
}

