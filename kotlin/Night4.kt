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

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun part1(input : List<String>) : Int {
    var numValid = 0

    for (phrase in input) {
        val set = HashSet<String>()
        var anyDups = false
        for (word in phrase.split(" ")) {
            if (set.contains(word)) {
                anyDups = true
                break
            } else {
                set.add(word)
            }
        }

        if (!anyDups) {
            numValid++
        }
    }

    return numValid
}

fun part2(input : List<String>) : Int {
    var numValid = 0

    for (phrase in input) {
        val set = HashSet<String>()
        var anyDups = false
        for (word in phrase.split(" ")) {
            val sortedWord = word.toCharArray().sortedArray().joinToString()
            if (set.contains(sortedWord)) {
                anyDups = true
                break
            } else {
                set.add(sortedWord)
            }
        }

        if (!anyDups) {
            numValid++
        }
    }

    return numValid
}