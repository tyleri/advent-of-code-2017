package night12

import java.util.LinkedList

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

fun ufGetGroup(groups: Array<Int>, elt: Int) : Int {
    var currElt = elt
    while (groups[currElt] != currElt) {
        currElt = groups[currElt]
    }

    return currElt
}

fun part1(input: List<String>) : Int {
    val unionFind = Array<Int>( size=input.size, init={it} )
    input.forEach {
        val lineVals = it.split(",", "<->").map(String::trim).map(String::toInt)

        val topRoot = ufGetGroup(unionFind, lineVals.first())
        lineVals.drop(1).forEach {
            val oldRoot = ufGetGroup(unionFind, it)
            unionFind[oldRoot] = topRoot
        }
    }

    val zeroGroup = ufGetGroup(unionFind, 0)
    return unionFind.indices.count { zeroGroup == ufGetGroup(unionFind, it) }
}

fun part2(input: List<String>) : Int {
    val unionFind = Array<Int>( size=input.size, init={it} )
    input.forEach {
        val lineVals = it.split(",", "<->").map(String::trim).map(String::toInt)

        val topRoot = ufGetGroup(unionFind, lineVals.first())
        lineVals.drop(1).forEach {
            val oldRoot = ufGetGroup(unionFind, it)
            unionFind[oldRoot] = topRoot
        }
    }

    return unionFind
        .indices
        .map {ufGetGroup(unionFind, it)}
        .distinct()
        .size
}