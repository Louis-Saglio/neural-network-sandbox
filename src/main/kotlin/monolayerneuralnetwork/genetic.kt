package monolayerneuralnetwork

import java.lang.IllegalArgumentException

fun MonoLayerNeuralNetwork.evaluate(): Double {
    var score = 0
    val times = 10000
    repeat(times) {
        val inOut = generateInputOutput()
        if (this.run(inOut.input) == inOut.output) {
            score += 1
        }
    }
    return score * 100 / times.toDouble()
}

fun main() {
    var population = (0 until 10).map { MonoLayerNeuralNetwork(2, 2) }
    assert(population.size % 2 == 0)

    repeat(1000) {
        // Select
        try {
            population = population.sortedBy { it.evaluate() }
        } catch (e: IllegalArgumentException) {
            println(e)
        }
        println("Population :")
        println(population.joinToString("\n"))

        println("Selected :")
        population = population.subList(population.size / 2, population.size)
        println(population.joinToString("\n"))

        // Reproduce
        population = population + population.map { it.clone() }
        println("New population :")
        println(population.sortedBy { it.evaluate() }.joinToString("\n"))

        println("Population average :")
        println(population.map { it.evaluate() }.average())

        // Mutate
        for (neuralNetwork in population) {
            if ((0..9).random() == 0) {
                println("Mutation")
                neuralNetwork.mutate()
            }
        }
        println("------------------------------------------------------")
    }
    val inOut = generateInputOutput()
    val out = population.maxBy { it.evaluate() }!!.run(inOut.input)
    println("Input")
    println(inOut.input.map { "%.2f".format(it) })
    println("Expected output")
    println(inOut.output.map { "%.2f".format(it) })
    println("Actual output")
    println(out.map { "%.2f".format(it) })
}
