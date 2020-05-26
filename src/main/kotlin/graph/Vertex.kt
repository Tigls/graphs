package graph

class Vertex (
    val id: String,
    val weight: Int = 1
) {
    companion object Factory {
        fun create(n: Int): MutableList<Vertex> {
            val vertices = mutableListOf<Vertex>()
            repeat(n) {
                vertices.add(Vertex(it.toString()))
            }
            return vertices
        }
    }
}

