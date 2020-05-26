package util

fun printMatrix(matrix: Array<IntArray>) {
    matrix.forEach { row ->
        row.forEach { value ->
            print("$value  ")
        }
        println()
    }
}

fun printMatrix(matrix: Array<FloatArray>) {
    matrix.forEach { row ->
        row.forEach { value ->
            if (value == Float.POSITIVE_INFINITY) {
                print("$value    ")
            } else {
                print("$value         ")
            }
        }
        println()
    }
}
