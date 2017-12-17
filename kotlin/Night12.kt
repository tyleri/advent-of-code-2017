import java.util.LinkedList
import helper.UnionFind

fun main(args: Array<String>) {
    var input = LinkedList<String>()

    while (true) {
        val line = readLine()
        if (line == null) {
            break
        } else if (line == "") {
            continue
        }

        input.add(line)
    }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input: List<String>) : Int {
    val uf = UnionFind(0 until input.size)
    input.forEach {
        val lineVals = it.split(",", "<->").map(String::trim).map(String::toInt)

        lineVals.drop(1).forEach {
            uf.union(lineVals.first(), it)
        }
    }

    val zeroGroup = uf.find(0)
    return (0 until input.size).count { zeroGroup == uf.find(it) }
}

fun part2(input: List<String>) : Int {
    val uf = UnionFind(0 until input.size)
    input.forEach {
        val lineVals = it.split(",", "<->").map(String::trim).map(String::toInt)

        lineVals.drop(1).forEach {
            uf.union(lineVals.first(), it)
        }
    }

    return uf.mapping.values.map {uf.find(it)}.distinct().size
}