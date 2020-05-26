package graph

data class AdjElem(
        val vertex: Vertex,
        val connectedVertices: MutableList<Vertex>
) {}