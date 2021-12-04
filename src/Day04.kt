fun main() {
    fun List<Board>.markDrawingOnBoards(drawing: Int): List<Board> =
        map { board ->
            board.mark(drawing)
            board
        }

    fun part1(input: List<String>): Int {
        val drawings = drawings(input)
        var boards = boards(input)

        drawings.forEach { drawing ->
            boards = boards.markDrawingOnBoards(drawing)
            val find = boards.find(Board::hasBingo)
            if (find != null) {
                return find.sumOfAllUnmarked() * drawing
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val drawings = drawings(input)
        var boards = boards(input)

        drawings.forEach { drawing ->
            boards = boards.markDrawingOnBoards(drawing)
            when {
                boards.size > 1 -> boards = boards.filter { board -> !board.hasBingo() }
                boards.size == 1 -> {
                    val find = boards.find(Board::hasBingo)
                    if (find != null) {
                        return find.sumOfAllUnmarked() * drawing
                    }
                }
            }
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

private fun drawings(input: List<String>): List<Int> =
    input.first().split(",").map(String::toInt)

private fun boards(input: List<String>): List<Board> =
    mutableListOf<Board>().apply {
        var allBoards = input.drop(2)
        while (allBoards.isNotEmpty()) {
            val board = Board(
                allBoards
                    .take(5)
                    .map { s: String ->
                        s
                            .split(" ")
                            .map(String::trim)
                            .filter { k ->
                                k.isNotBlank()
                            }
                            .map { t ->
                                t.toInt() to false
                            }
                    }
            )
            add(board)
            allBoards = allBoards.drop(6)
        }
    }.toList()

data class Board(var board: List<List<Pair<Int, Boolean>>>) {
    fun mark(number: Int) {
        board =
            board.toMutableList().map { pairs ->
                pairs.toMutableList().map { pair ->
                    if (pair.first == number)
                        pair.copy(second = true)
                    else
                        pair
                }.toList()
            }.toList()
    }

    private fun hasBingoInRow(): Boolean {
        return board.any { map -> map.all { it.second } }
    }

    private fun hasBingoInColumn(): Boolean {
        val newBoard: MutableList<MutableList<Pair<Int, Boolean>>> =
            mutableListOf<MutableList<Pair<Int, Boolean>>>().apply {
                repeat(board.size) { add(mutableListOf()) }
            }
        for (row in board) {
            row.forEachIndexed { index, item ->
                newBoard[index].add(item)
            }
        }

        return newBoard.any { map -> map.all { it.second } }
    }

    fun hasBingo() = hasBingoInRow() || hasBingoInColumn()

    fun sumOfAllUnmarked(): Int {
        return board.flatten().filter { pair -> !pair.second }.sumOf { pair -> pair.first }
    }
}

