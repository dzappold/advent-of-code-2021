fun main() {
    fun part1(input: List<String>): Int {
        val (horizontal, depth) = input.map { s ->
            val split = s.split(" ")
            val command = split.first()
            val steps = split.last().toInt()
            when {
                command.equals("up", true) -> Day02(0, -steps)
                command.equals("down", true) -> Day02(0, steps)
                else -> Day02(steps, 0)
            }
        }.reduce { day01, day02 ->
            Day02(day01.horizontal + day02.horizontal, day01.depth + day02.depth)
        }
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        val (horizontal, depth, aim) = input.map { s ->
            val split = s.split(" ")
            val command = split.first()
            val steps = split.last().toInt()
            when {
                command.equals("up", true) -> Day02b(0, 0, -steps)
                command.equals("down", true) -> Day02b(0, 0, steps)
                else -> Day02b(steps, 0, 0)
            }
        }.reduce { day01, day02 ->
            Day02b(
                day01.horizontal + day02.horizontal,
                day01.depth + if (day02.horizontal != 0) {
                    (day02.horizontal * (day01.aim + day02.aim))
                } else 0,
                day01.aim + day02.aim
            )
        }
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

data class Day02(val horizontal: Int, val depth: Int)
data class Day02b(val horizontal: Int, val depth: Int, val aim: Int)
