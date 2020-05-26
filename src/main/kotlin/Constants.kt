import graph.Vertex

val ACTIONS = arrayOf(
        "1-create graph via matrix",
        "2-add vertex",
        "3-print distance matrix",
        "4-generate graph",
        "5-input source and dest vertex",
        "6-find path using Terry algorithm",
        "7-execute example 1 of max-flow of network graph"
)
const val GREETING = "Input the action number:"
const val SHOW_ACTION = "show"
const val ACTIONS_STRING = "(to show available actions print \"show\")"
typealias AdjList = HashMap<Vertex, MutableList<Vertex>>
// To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
const val INF = Long.MAX_VALUE / 2
