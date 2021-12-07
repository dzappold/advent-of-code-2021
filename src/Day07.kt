import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val grabPositions = input.flatMap { line -> line.split(",").map { position -> position.toInt() } }
        val minimumPosition = grabPositions.minOrNull() ?: 0
        val maximumPosition = grabPositions.maxOrNull() ?: 1000
        val distanceMap = (minimumPosition..maximumPosition).associateWith { position ->
            grabPositions.sumOf { grabPos ->
                abs(grabPos - position)
            }
        }
        return distanceMap.values.minOrNull() ?: 0
    }

    fun part2(input: List<String>): Int {
        val grabPositions = input.flatMap { line -> line.split(",").map { position -> position.toInt() } }
        val minimumPosition = grabPositions.minOrNull() ?: 0
        val maximumPosition = grabPositions.maxOrNull() ?: 1000
        val distanceMap = (minimumPosition..maximumPosition).associateWith { position ->
            grabPositions.sumOf { grabPos ->
                val n = abs(grabPos - position)
                n * (n + 1) / 2
            }
        }
        return distanceMap.values.minOrNull() ?: 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
