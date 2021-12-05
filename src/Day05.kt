fun main() {

    fun part1(input: List<String>): Int {
        val coordinates = mutableListOf<Coordinate>()
        input.map { line ->
            val splittedValues = line.split(",", " -> ").map(String::toInt)
            if (splittedValues[0] == splittedValues[2] || splittedValues[1] == splittedValues[3]) {
                coordinates.addAll(
                    when {
                        splittedValues[0] < splittedValues[2] -> (splittedValues[0]..splittedValues[2]).map { x ->
                            Coordinate(x, splittedValues[1])
                        }
                        splittedValues[2] < splittedValues[0] -> (splittedValues[2]..splittedValues[0]).map { x ->
                            Coordinate(x, splittedValues[1])
                        }
                        splittedValues[1] < splittedValues[3] -> (splittedValues[1]..splittedValues[3]).map { y ->
                            Coordinate(splittedValues[0], y)
                        }
                        else -> {
                            (splittedValues[3]..splittedValues[1]).map { y -> Coordinate(splittedValues[0], y) }
                        }
                    }
                )
            }
        }
        return coordinates.groupBy { coordinate: Coordinate -> coordinate }.count { entry -> entry.value.size >= 2 }
    }

    fun part2(input: List<String>): Int {
        val coordinates = mutableListOf<Coordinate>()
        input.map { line ->
            val splittedValues = line.split(",", " -> ").map(String::toInt)
            val start = Coordinate(splittedValues[0], splittedValues[1])
            val end = Coordinate(splittedValues[2], splittedValues[3])
            coordinates.addAll(start..end)
        }
        return coordinates.groupBy { coordinate: Coordinate -> coordinate }.count { entry -> entry.value.size >= 2 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

data class Coordinate(val x: Int, val y: Int) {
    operator fun rangeTo(coordinate: Coordinate): List<Coordinate> {
        return when {
            x < coordinate.x && y < coordinate.y ->
                (x..coordinate.x).zip(y..coordinate.y).map { (newX, newY) -> Coordinate(newX, newY) }

            x > coordinate.x && y < coordinate.y ->
                (x downTo coordinate.x).zip(y..coordinate.y).map { (newX, newY) -> Coordinate(newX, newY) }

            x < coordinate.x && y > coordinate.y ->
                (x..coordinate.x).zip(y downTo coordinate.y).map { (newX, newY) -> Coordinate(newX, newY) }

            x > coordinate.x && y > coordinate.y ->
                (x downTo coordinate.x).zip(y downTo coordinate.y).map { (newX, newY) -> Coordinate(newX, newY) }

            x < coordinate.x && y == coordinate.y ->
                (x..coordinate.x).map { newX -> Coordinate(newX, y) }

            x > coordinate.x && y == coordinate.y ->
                (x downTo coordinate.x).map { newX -> Coordinate(newX, y) }

            x == coordinate.x && y < coordinate.y ->
                (y..coordinate.y).map { newY -> Coordinate(x, newY) }

            x == coordinate.x && y > coordinate.y ->
                (y downTo coordinate.y).map { newY -> Coordinate(x, newY) }

            else -> listOf(this)
        }
    }
}
