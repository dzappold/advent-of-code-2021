fun main() {
    fun part1(input: List<String>): Int {
        var lanternfishs = input.map { s -> s.split(",").map(String::toInt) }.flatten().toMutableList()
        (1..80).forEach {
            mutableListOf<Int>()
            val newFishes = mutableListOf<Int>()
            lanternfishs = (
                    (lanternfishs.map { fish ->
                        if (fish > 0) {
                            fish - 1
                        } else {
                            newFishes += 8
                            6
                        }
                    }).toMutableList()
                            + newFishes
                    ).toMutableList()
//            println(lanternfishs)
        }
//        println(lanternfishs.count())
        return lanternfishs.count()
    }

    tailrec fun fishes(lanternfishs: List<Fish>, counter: Int): Long {
        if (counter == 0)
            return lanternfishs.size.toLong()
        var lanternfishs1 = lanternfishs
        val newFishes = mutableListOf<Long>()
        lanternfishs1 = (lanternfishs1.map { fish ->
            when {
                // older then 0 -> x-1
                fish.clock > 0L -> {
                    Fish(fish.clock - 1)
                }
                // is 0 then becomes 6 and creates new fish (8)
                // fish.clock == 0L
                else -> {
                    newFishes += 8
                    Fish(6L)
                }
            }
        })
        if (newFishes.isNotEmpty()) {
            lanternfishs1 += newFishes.map { l: Long -> Fish(l) }
        }
        return fishes(lanternfishs1, counter - 1)
    }

    fun fishes(fishs: Map<Long, Long>, counter: Int): Long {
        var lanternfishs = fishs.toList()
        repeat(counter) {
            var newFishCount = 0L
            var newSixes = 0L
            lanternfishs = (lanternfishs.map { (days, amount) ->
                when {
                    days == 7L -> {
                        newSixes += amount
                        null
                    }
                    days > 0L -> days - 1 to amount
                    else -> {
                        newFishCount += amount
                        newSixes += amount
                        null
                    }
                }
            }).filterNotNull()
            if (newSixes != 0L)
                lanternfishs =
                    lanternfishs+  (6L to  newSixes)
            if (newFishCount != 0L)
                lanternfishs = lanternfishs+(8L to newFishCount)
            println("$lanternfishs - ${lanternfishs.sumOf { it.second.toLong() }}")
        }
        return lanternfishs.sumOf { pair: Pair<Long, Long> -> pair.second.toLong() }
    }

    fun part2(input: List<String>): Long {
        val lanternfishs = input
            .map { s ->
                s.split(",")
                    .map(String::toLong)
            }
            .flatten()
            .map { days -> Fish(days) }

        println(lanternfishs)
        //val fishes = fishes(lanternfishs, 256)
        val fishs = lanternfishs.map { fish -> fish.clock }.groupBy { l: Long -> l }
            .mapValues { entry -> entry.value.size.toLong() }
        println(fishs)
        val fishes = fishes(fishs, 256)
        println(fishes)
        return fishes
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

data class Fish(var clock: Long) {
    override fun toString(): String {
        return "$clock"
    }
}
