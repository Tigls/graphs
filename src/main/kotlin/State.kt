import graph.Graph

object State {
    var exit = -1
    val graphs: MutableList<Graph> = mutableListOf()
    var currentGraph: Graph = Graph.createEmpty()
}