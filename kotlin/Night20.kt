fun main(args: Array<String>) {
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
    // println("Part 2: ${part2(input)}")
}

class Particle(
    val p: Triple<Int, Int, Int>,
    val v: Triple<Int, Int, Int>,
    val a: Triple<Int, Int, Int>
) {
    fun tick() : Particle =
        Particle(
            Triple(p.first + v.first + a.first, p.second + v.second + a.second, p.third + v.third + a.third),
            Triple(v.first + a.first, v.second + a.second, v.third + a.third),
            a
        )
    
    fun dist() = p.toList().map(Math::abs).reduce(Int::plus)
}

fun parseParticle(input: String) : Particle {
    val (pStr, vStr, aStr) = input
        .split("<", ">").slice(1..5 step 2)
        .map {
            it.split(",").map(String::toInt)
        }

    return Particle(
        Triple(pStr[0], pStr[1], pStr[2]),
        Triple(vStr[0], vStr[1], vStr[2]),
        Triple(aStr[0], aStr[1], aStr[2])
    )
}

fun closestParticleIdx(particles: List<Particle>) : Int {
    var closestIdx = 0
    
    particles.forEachIndexed { idx, curr ->
        if (curr.dist() < particles[closestIdx].dist())
            closestIdx = idx
    }

    return closestIdx
}

fun part1(input: List<String>) : Int {
    var particles = input.map(::parseParticle)
    var currClosest = closestParticleIdx(particles)

    while (true) {
        (1..1000).forEach {
            particles = particles.map {it.tick()}
        }

        val newClosest = closestParticleIdx(particles)
        if (newClosest == currClosest) {
            break
        }

        currClosest = newClosest
    }

    return currClosest
}

fun part2(input: List<String>) : Int {
    var particles = input.map(::parseParticle)
    var currClosest = closestParticleIdx(particles)

    while (true) {
        (1..50).forEach {
            particles = particles.map {it.tick()}
        }

        val newClosest = closestParticleIdx(particles)
        if (newClosest == currClosest) {
            break
        }

        currClosest = newClosest
    }

    return currClosest
}