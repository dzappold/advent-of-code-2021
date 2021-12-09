
fun main() {

    fun part1(input: List<String>): Int {
        var total = 0
        (input.indices step 2).forEach { index ->
            val determineNumbers = determineNumbers(input[index])
            val oneFourSevenEight = determineNumbers.filterValues { value -> value in listOf(1, 4, 7, 8) }.keys
            val lineToTranslate = input[index + 1].split(" ").map { s -> s.toList() }
            val result = lineToTranslate.map { number ->
                oneFourSevenEight.filter { s ->
                    s.sorted().containsAll(number.sorted()) && number.sorted().containsAll(s.sorted())
                }.size
            }.count { i -> i == 1 }
            total += result
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0
        (input.indices step 2).forEach { index ->
            val determineNumbers = determineNumbers(input[index])
            val lineToTranslate = input[index + 1].split(" ").map { s -> s.toList() }
            val result = lineToTranslate.map { number ->
                determineNumbers.filter { (s, _): Map.Entry<List<Char>, Int> ->
                    s.sorted().containsAll(number.sorted()) && number.sorted().containsAll(s.sorted())
                }.map { entry -> entry.value }.first()
            }.joinToString(separator = "").toInt()
            total += result
        }
        return total

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}


val REAL_VALUES = mapOf(
    1 to "cf",
    7 to "acf",
    4 to "bcdf",
    2 to "acdeg",
    3 to "acdfg",
    5 to "abdfg",
    6 to "abdefg",
    9 to "abcdfg",
    0 to "abcefg",
    8 to "abcdefg",
)

fun determineNumbers(numbersToDetermine: String): Map<List<Char>, Int> {
    val filter = numbersToDetermine.split(" ", "|").filter(String::isNotBlank)
    val groupBy = filter.groupBy(String::length)

    // a,b -> c,f
    val one = groupBy[2]?.first()?.toList()?.sorted() ?: emptyList()

    // e,a,f,b -> b,c,d,f
    val four = groupBy[4]?.first()?.toList()?.sorted() ?: emptyList()

    // d,a,b -> a,c,f
    val seven = groupBy[3]?.first()?.toList()?.sorted() ?: emptyList()
    val eight = groupBy[7]?.first()?.toList()?.sorted() ?: emptyList()

    // groupby[6] -> 9, 6, 0 => 6 does not contain 7
    val group6 = groupBy[6]?.map { s -> s.toList().sorted() }
    val nineOrZero = group6?.filter { set -> set.containsAll(seven) } ?: emptyList()
    val six = group6?.filter { set -> !set.containsAll(seven) }?.flatten() ?: emptyList()
    val nine = nineOrZero.filter { set -> set.containsAll(four) }.flatten()
    val zero = nineOrZero.filter { set -> !set.containsAll(nine) }.flatten()

    val d = eight.minus(zero.toSet())

    // groupBy[5] -> 2,3,5 => 3 like 6
    val twoOrFive =
        groupBy[5]?.map { s -> s.toList().sorted() }?.filter { set -> !set.containsAll(seven) } ?: emptyList()
    val three = groupBy[5]?.map { s -> s.toList().sorted() }?.filter { set -> set.containsAll(seven) }?.flatten()
        ?: emptyList()
    val five = twoOrFive.filter { set -> nine.containsAll(set) }.flatten()
    val two = twoOrFive.filter { set -> !nine.containsAll(set) }.flatten()
    val a = seven.minus(one.toSet())
    val c = (nine - five)

    return mapOf(
        one to 1,
        two to 2,
        three to 3,
        four to 4,
        five to 5,
        six to 6,
        seven to 7,
        eight to 8,
        nine to 9,
        zero to 0,
    )
}
