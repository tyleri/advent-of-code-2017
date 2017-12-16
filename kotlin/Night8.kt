fun main(args: Array<String>) {
    val input = ArrayList<String>()

    while (true) {
        val line = readLine()

        if (line == null) {
            break
        }

        input.add(line)
    }

    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

enum class Comparator {
    GT, LT, GTE, LTE, EQ, NE
}

data class Operation (
    val condVar: String,
    val comp: Comparator,
    val condConst: Int,
    val modVar: String,
    val modConst: Int
)

fun parseLine(input: String) : Operation {
    val splitLine = input.split(" ")

    return Operation(
        condVar = splitLine[4],
        comp = when (splitLine[5]) {
            ">" -> Comparator.GT
            "<" -> Comparator.LT
            ">=" -> Comparator.GTE
            "<=" -> Comparator.LTE
            "==" -> Comparator.EQ
            "!=" -> Comparator.NE
            else -> throw IllegalArgumentException("Bad conditional operator")
        },
        condConst = splitLine[6].toInt(),
        modVar = splitLine[0],
        modConst = when (splitLine[1]) {
            "inc" -> splitLine[2].toInt()
            "dec" -> -splitLine[2].toInt()
            else -> throw IllegalArgumentException("Bad modifying operator")
        }
    )
}

fun part1(input: List<String>) : Int {
    val map: HashMap<String, Int> = HashMap<String, Int>()

    for (line: String in input) {
        val op: Operation = parseLine(line)

        val condVarValue = map.getOrDefault(op.condVar, 0)

        val shouldExecute: Boolean = when (op.comp) {
            Comparator.GT -> condVarValue > op.condConst
            Comparator.LT -> condVarValue < op.condConst
            Comparator.GTE -> condVarValue >= op.condConst
            Comparator.LTE -> condVarValue <= op.condConst
            Comparator.EQ -> condVarValue == op.condConst
            Comparator.NE -> condVarValue != op.condConst
        }

        if (shouldExecute) {
            val oldVal: Int = map.getOrDefault(op.modVar, 0)
            val newVal: Int = oldVal + op.modConst
            map.put(op.modVar, newVal)
        }
    }

    return map.values.max() ?: 0
}

fun part2(input: List<String>) : Int {
    val map: HashMap<String, Int> = HashMap<String, Int>()

    var runningMax: Int = 0

    for (line: String in input) {
        val op: Operation = parseLine(line)

        val condVarValue = map.getOrDefault(op.condVar, 0)

        val shouldExecute: Boolean = when (op.comp) {
            Comparator.GT -> condVarValue > op.condConst
            Comparator.LT -> condVarValue < op.condConst
            Comparator.GTE -> condVarValue >= op.condConst
            Comparator.LTE -> condVarValue <= op.condConst
            Comparator.EQ -> condVarValue == op.condConst
            Comparator.NE -> condVarValue != op.condConst
        }

        if (shouldExecute) {
            val oldVal: Int = map.getOrDefault(op.modVar, 0)
            val newVal: Int = oldVal + op.modConst
            map.put(op.modVar, newVal)

            runningMax = Math.max(newVal, runningMax)
        }
    }

    return runningMax
}
