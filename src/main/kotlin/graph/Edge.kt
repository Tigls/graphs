package graph

class Edge (
    val id: String,
    val source: Vertex,
    val target: Vertex,
    val weight: Int = 1) {
}