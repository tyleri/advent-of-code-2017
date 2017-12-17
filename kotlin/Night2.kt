fun main(args : Array<String>) {
    val input = mutableListOf<String>()

    while (true) {
        val line = readLine()

        if (line == null) {
            break
        } else if (line == "") {
            continue
        }
        input.add(line)
    }

    println("Part 1: ${calc_part1(input)}")
    println("Part 2: ${calc_part2(input)}")
}

fun calc_part1(input : List<String>) : Int {
    var summed = 0

    for (s in input) {
        val arrVals = s.split("\t").map {it.toInt()}

        if (arrVals.size != 0) {
            summed += arrVals.max()!! - arrVals.min()!!
        }
    }

    return summed
}

fun calc_part2(input : List<String>) : Int {
    var summed = 0

    for (s in input) {
        val arrVals = s.split("\t", " ").map {it.toInt()}

        for (i in arrVals.indices) {
            for (j in (i+1)..arrVals.lastIndex) {
                val val_i = arrVals[i]
                val val_j = arrVals[j]

                if (val_i % val_j == 0) {
                    summed += val_i / val_j
                } else if (val_j % val_i == 0) {
                    summed += val_j / val_i
                }
            }
        }
    }

    return summed
}