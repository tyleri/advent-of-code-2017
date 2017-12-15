package night7

fun main(args : Array<String>) {
    var input : ArrayList<String> = ArrayList<String>()

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

fun part1(input : List<String>) : String {
    val tower = HashMap<String, String>()

    for (line in input) {
        if ("->" in line) {
            val (left, right) = line.split("->")
            val parent = left.split(" ")[0]
            val children = right.split(",").map { it.trim() }

            children.forEach { tower.put(it, parent) }
        }
    }

    var someProg : String = tower.values.first()
    while (tower.containsKey(someProg)) {
        tower.get(someProg)?.let { someProg = it }
    }

    return someProg
}

fun part2(input : List<String>) : Int {
    val parentToChildren = HashMap<String, List<String>>()
    val childToParent = HashMap<String, String>()
    val weights = HashMap<String, Int>()
    val combinedWeights = HashMap<String, Int>()

    for (line in input) {
        val splitLine = line.split("->")
        val splitLeft = splitLine[0].split(" ")
        val parent = splitLeft[0]
        val parentWeight = splitLeft[1].substring(1, splitLeft[1].lastIndex).toInt()

        weights.put(parent, parentWeight)

        if (splitLine.size == 1) {
            combinedWeights.put(parent, parentWeight)
        } else {
            val children = splitLine[1].split(",").map { it.trim() }
            parentToChildren.put(parent, children)
            children.forEach { childToParent.put(it, parent) }
        }
    }

    fun calcCombinedWeight(prog : String) : Int {
        val combWeight = combinedWeights.get(prog)

        if (combWeight != null) {
            return combWeight
        }

        val children = parentToChildren.get(prog) ?: throw IllegalArgumentException("Invalid program given to calcCombinedWeight") 
        val childrenWeights : List<Int> = children.map { calcCombinedWeight(it) }

        val sum = childrenWeights.sum() + (weights.get(prog) ?: throw IllegalArgumentException("Invalid program given to calcCombinedWeight"))
        combinedWeights.put(prog, sum)
        return sum
    }

    // get root
    var root = childToParent.values.first()
    while (true) {
        val rootParent = childToParent.get(root)
        
        if (rootParent == null) {
            break
        } else {
            root = rootParent
        }
    }

    var valueDiff : Int = 0

    do {
        val rootChildren = parentToChildren.get(root) ?: throw IllegalArgumentException("bad root")
        val childrenWeights = rootChildren.map { calcCombinedWeight(it) }

        val unbalancedIdx : Int? = getDifferentValIndex(childrenWeights)
        unbalancedIdx?.let {
            valueDiff = when (unbalancedIdx) {
                0 -> childrenWeights[1] - childrenWeights[0]
                else -> childrenWeights[0] - childrenWeights[unbalancedIdx]
            }
            root = rootChildren.get(unbalancedIdx)
        }
    } while (unbalancedIdx != null)

    val myWeight = weights.get(root) ?: throw IllegalArgumentException("Unbalanced node's weight not found")
    return myWeight + valueDiff
}

fun getDifferentValIndex(inputList : List<Int>) : Int? {
    if (inputList.size <= 2) {
        return null
    }

    if (inputList[0] != inputList[1]) {
        return when (inputList[2]) {
            inputList[0] -> 1
            else -> 0
        }
    }

    for (i in inputList.indices) {
        if (inputList[0] != inputList[i]) {
            return i
        }
    }

    return null
}