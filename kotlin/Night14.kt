import helper.knotHash
import helper.UnionFind

fun main(args: Array<String>) {
    val input = readLine() ?: throw IllegalArgumentException("No input")

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun hexCountOnes(char: Char) : Int {
    var hexNum = when (char) {
        in '0'..'9' -> char - '0'
        'a' -> 10
        'b' -> 11
        'c' -> 12
        'd' -> 13
        'e' -> 14
        'f' -> 15
        else -> throw IllegalArgumentException("Invalid hex character")
    }

    var count = 0
    while (hexNum != 0) {
        if (hexNum % 2 == 1) {
            count++
        }
        hexNum = hexNum ushr 1
    }

    return count
}

fun part1(input: String) : Int {
    var sumUsed = 0

    for (i in 0..127) {
        val hash = knotHash("$input-$i")
        sumUsed += hash.fold(0, {acc, char -> acc + hexCountOnes(char)})
    }
    
    return sumUsed
}

fun hexToBitArr(char: Char) : Array<Boolean> {
    var hexNum = when (char) {
        in '0'..'9' -> char - '0'
        in 'a'..'f' -> 10 + (char - 'a')
        else -> throw IllegalArgumentException("Invalid hex character")
    }

    return arrayOf(
        hexNum and 8 != 0,
        hexNum and 4 != 0,
        hexNum and 2 != 0,
        hexNum and 1 != 0
    )
}

fun part2(input: String) : Int {
    val diskArr = Array<Array<Boolean>>(
        128,
        {
            val hash = knotHash("$input-$it")
            hash.fold(arrayOf<Boolean>(), {acc, char -> acc.plus(hexToBitArr(char))})
        }
    )

    // add indices to union find
    var usedList = mutableListOf<Pair<Int, Int>>()
    for (i in diskArr.indices) {
        // println(diskArr[i].map {if (it) 1 else 0}.joinToString(separator=""))
        for (j in diskArr[i].indices) {
            if (diskArr[i][j]) {
                usedList.add(Pair(i, j))
            }
        }
    }
    val uf = UnionFind<Pair<Int, Int>>(usedList)

    usedList.forEach {
        val (i, j) = it
        val upPair = Pair(i-1, j)
        val downPair = Pair(i+1, j)
        val leftPair = Pair(i, j-1)
        val rightPair = Pair(i, j+1)
        val upUsed = diskArr.getOrNull(upPair.first)?.getOrNull(upPair.second) ?: false
        val downUsed = diskArr.getOrNull(downPair.first)?.getOrNull(downPair.second) ?: false
        val leftUsed = diskArr.getOrNull(leftPair.first)?.getOrNull(leftPair.second) ?: false
        val rightUsed = diskArr.getOrNull(rightPair.first)?.getOrNull(rightPair.second) ?: false

        if (upUsed) {
            uf.union(it, upPair)
        }
        if (downUsed) {
            uf.union(it, downPair)
        }
        if (leftUsed) {
            uf.union(it, leftPair)
        }
        if (rightUsed) {
            uf.union(it, rightPair)
        }
    }

    return uf.mapping.keys.map {uf.find(it)}.distinct().size
}