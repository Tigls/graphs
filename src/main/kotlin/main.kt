import actions.GraphAction
import actions.IOAction

/*
*  Лаб 1
*  - Визначати кількість вершин                             - partly done
*  - Задати граф матрицею або списоком суміжності           - done
*  - Генерація матриці або списку суміжності                - done
*  - Задати граф матрицею інцидентності.                    - done
*  - Побудувати матрицю відстаней.                          - done
*  - Виводити на екран результат.                           - done
*  Лаб 2
*  - Задавати почат та кінцеві вершини графа                - done
*  - Алгоритм Тері пошуку маршруту від поч. до кінц.        - done
*  Лаб 3
*  - Задати матрицю через файл                              - via code
*  - Знайти макс потік що задовольняє обмеж дуг мережі      - done
*/

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


