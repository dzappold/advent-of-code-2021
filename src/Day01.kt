fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map(String::toInt)
            .zipWithNext { a: Int, b: Int -> a < b }
            .count { it }
    }

    fun part2(input: List<String>): Int {
        return input
            .map(String::toInt)
            .windowed(size = 3, step = 1, transform = List<Int>::sum)
            .zipWithNext { a: Int, b: Int -> a < b }
            .count { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
