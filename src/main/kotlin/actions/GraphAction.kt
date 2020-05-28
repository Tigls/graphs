package actions

import graph.FordFulkersonDfsSolverAdjacencyList
import graph.Graph
import util.GraphParsing.adjMatrixToGraph
import util.GraphParsing.incMatrixToGraph
import util.GraphParsing.readMatrix
import util.printMatrix

object GraphAction {
    fun addFromUserInput() {
        println("Введіть тип матриці (1 - суміжності, 2 - інцидентності):")
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
                println("Неправильний номер типу матриці. Повертаємось до головного меню...")
                return
            }
        }
        State.graphs.add(State.currentGraph)
        println(State.currentGraph)
    }

    fun addVertex() {
        println("Введіть індентифікатор вершини:")
        val id = readLine()!!
        State.currentGraph.addVertex(id)
        println("Вершина додана")
    }

    fun printDistanceMatrix() {
        val matrix = State.currentGraph.distanceMatrix()
        printMatrix(matrix)
    }

    fun generateGraph() {
        println("Введіть кількість вершни, ребер, та тип графу (0-неорієнтований, 1-орієнтований). Приклад: 6 8 1")
        val input = readLine()!!
        val data = input.split(" ")
        if (data.size == 3) {
            State.currentGraph = Graph.generate(data[0].toInt(), data[1].toInt(), data[2].toBoolean())
            println()
            println(State.currentGraph)
        } else {
            println("Невірно задано граф. Потрібно в такому порядку: вершини ребра тип-графу. Повертаємось до головного меню...")
            return
        }
    }

    fun inputSourceDest() {
        println("Введіть індентифікатор початкової вершини: ")
        val sourceId = readLine()!!
        State.currentGraph.setSourceDest(sourceId, true)
        println("Введіть ідентифікатор кінцевої вершини: ")
        val destId = readLine()!!
        State.currentGraph.setSourceDest(destId, false)
        println("Початкова та кінцева вершини виставлені.")
    }

    fun calcPathTerry() {
        val path = State.currentGraph.dfsIterative(State.currentGraph.getSource()!!, State.currentGraph.getDest()!!)
        println("Результат виконання алгоритму Террі:")
        path.forEach {
            if (path.last() == it) print("${it.id} ") else print("${it.id} -> ")
        }
        println()
    }

    fun executeEx1NetworkGraph() {
        FordFulkersonDfsSolverAdjacencyList.exampleMultipleSources()
    }
}

