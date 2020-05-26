package actions

import ACTIONS
import ACTIONS_STRING
import GREETING

object IOAction {
    fun greet() {
        println(GREETING)
        if (State.exit == -1) {
            println(ACTIONS_STRING)
            State.exit = 0
        }
    }

    fun show() {
        ACTIONS.forEach { println(it) }
    }

    fun emptyLine() {
        println()
    }
}