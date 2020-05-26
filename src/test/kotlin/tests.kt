import graph.Graph
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import util.GraphParsing
import kotlin.test.assertEquals

class TestSource() {
    @Test fun adjMatrixToGraphTest() {
        val inputMatrix = arrayOf(
            intArrayOf(0,1,1,0),
            intArrayOf(1,0,0,1),
            intArrayOf(1,0,0,0),
            intArrayOf(0,1,0,0)
        )
        val graph = GraphParsing.adjMatrixToGraph(inputMatrix)
        val expectedString = """
            Number of vertices: 4
            Number of edges: 6
            Graph adj list:
            0: -> 1  -> 2 
            1: -> 0  -> 3 
            2: -> 0 
            3: -> 1 
            
        """.trimIndent()
        assertEquals(expectedString, graph.toString(), "Graph not equal")
    }

    @Test fun incMatrixToGraphTest() {
        val inputMatrix = arrayOf(
                intArrayOf( 1,1,0),
                intArrayOf(-1,0,1),
                intArrayOf( 0,1,0),
                intArrayOf( 0,0,1)
        )
        val graph = GraphParsing.incMatrixToGraph(inputMatrix)
        val expectedString = """
            Number of vertices: 4
            Number of edges: 5
            Graph adj list:
            0: -> 2 
            1: -> 0  -> 3 
            2: -> 0 
            3: -> 1 
            
        """.trimIndent()
        assertEquals(expectedString, graph.toString(), "Graph not equal")
    }

    @Test fun distanceMatrixTest() {
        val inputMatrix = arrayOf(
                intArrayOf(0,1,1,0),
                intArrayOf(1,0,0,1),
                intArrayOf(1,0,0,0),
                intArrayOf(0,1,0,0)
        )
        val expectedMatrix = arrayOf (
            floatArrayOf(0F,1F,1F,2F),
            floatArrayOf(1F,0F,2F,1F),
            floatArrayOf(1F,2F,0F,3F),
            floatArrayOf(2F,1F,3F,0F)
        )
        val graph = GraphParsing.adjMatrixToGraph(inputMatrix)
        val matrix = graph.distanceMatrix()
        assertTrue(matrix.contentDeepEquals(expectedMatrix), "Matrix no equal")
//        printMatrix(matrix)
    }

    @Test fun generateUndirectedTest() {
        val graph = Graph.generate(6, 7, true)
        print(graph)
    }

    @Test fun dfsIterativeTest() {
        val inputMatrix = arrayOf(
                intArrayOf(0,1,0,0),
                intArrayOf(1,0,1,0),
                intArrayOf(0,1,0,1),
                intArrayOf(0,0,1,0)
        )
        val graph = GraphParsing.adjMatrixToGraph(inputMatrix)
        val source = graph.getVertices()[1]
        val dest = graph.getVertices()[3]
        val path = graph.dfsIterative(source, dest)
        println("End")
        path.forEach { print("${it.id} -> ") }
        println()
    }

    @Test fun dfsRecursiveTest() {
        val inputMatrix = arrayOf(
                intArrayOf(0,1,0,0),
                intArrayOf(1,0,1,0),
                intArrayOf(0,1,0,1),
                intArrayOf(0,0,1,0)
        )
        val graph = GraphParsing.adjMatrixToGraph(inputMatrix)
        val source = graph.getVertices()[1]
        val dest = graph.getVertices()[3]
        val path = graph.dfsRecursive(source, dest)
        println("End")
        path.forEach { print("${it.id} -> ") }
        println()
    }

}

