import actions.GraphAction
import actions.IOAction

fun main(){
    IOAction.emptyLine()
    while (State.exit < 1) {
        IOAction.greet()
        when (readLine()!!) {
            "1"    -> GraphAction.addFromUserInput()
            "2"    -> GraphAction.addVertex()
            "3"    -> GraphAction.printDistanceMatrix()
            "4"    -> GraphAction.generateGraph()
            "5"    -> GraphAction.inputSourceDest()
            "6"    -> GraphAction.calcPathTerry()
            "7"    -> GraphAction.executeEx1NetworkGraph()
            "show" -> IOAction.show()
            "exit" -> State.exit = 1
        }
        IOAction.emptyLine()
    }
}


