import graph.Vertex

val ACTIONS = arrayOf(
        "1-створити граф задавши матрицю",
        "2-додати вершину до графа",
        "3-побудувати матрицю відстаней",
        "4-згенерувати граф",
        "5-ввести початкову та кінцеву вершини графа",
        "6-знайти маршрут за алгоритмом Террі",
        "7-знайти максимальний потік у мережі"
)
const val GREETING = "Введіть номер команди:"
const val SHOW_ACTION = "show"
const val ACTIONS_STRING = "(щоб показати доступні команди введіть \"show\")"
typealias AdjList = HashMap<Vertex, MutableList<Vertex>>
// To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
const val INF = Long.MAX_VALUE / 2
