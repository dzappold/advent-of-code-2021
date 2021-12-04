fun main() {
    fun part1(input: List<String>): Int {
        val gammaRates = input.map { s ->
            s.map { c -> c.digitToInt() }
        }

        val average = input.size / 2
        val gamma = (0 until input.first().length).map { i: Int ->
            val sumOf: Int = gammaRates.sumOf {
                it[i]
            }
            if (sumOf >= average) 1 else 0
        }
            .joinToString(separator = "") { "$it" }

        val epsilonRate =
            (0 until input.first().length).map { i: Int -> if (gammaRates.sumOf { it[i] } >= average) 0 else 1 }
                .joinToString(separator = "") { "$it" }


        return gamma.toInt(2) * epsilonRate.toInt(2)
    }

    fun part2(input: List<String>): Int {
        var oxygenGeneratorRating = input
        (1..input.first().length).forEachIndexed { index, _ ->
            val groupBy = oxygenGeneratorRating.partition { s -> s[index] == '0' }
            oxygenGeneratorRating = when {
                groupBy.first.size > groupBy.second.size -> groupBy.first
                else -> groupBy.second
            }
            if (oxygenGeneratorRating.size == 1) return@forEachIndexed
        }

        var co2ScrubberRating = input
        var co2Scrubber: String? = null
        (1..input.first().length).forEachIndexed { index, _ ->
            val groupBy = co2ScrubberRating.partition { s -> s[index] == '0' }
            co2ScrubberRating = when {
                groupBy.first.size <= groupBy.second.size -> groupBy.first
                else -> groupBy.second
            }
            if (co2ScrubberRating.size == 2) {
                co2ScrubberRating = when {
                    co2ScrubberRating.first()[index] == '0' -> listOf(co2ScrubberRating.first())
                    else -> listOf(co2ScrubberRating.last())
                }
            }
            if (co2ScrubberRating.size == 1) {
                co2Scrubber = co2ScrubberRating.first()
            }
        }
        val toInt = co2Scrubber?.toInt(2) ?: 0
        return oxygenGeneratorRating[0].toInt(2) * toInt
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

