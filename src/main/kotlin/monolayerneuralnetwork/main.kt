package monolayerneuralnetwork

import java.util.Random

class InOut(val input: List<Float>, val output: List<Float>) {
    override fun toString(): String {
        return "${this.javaClass.simpleName}(input=$input, output=$output)"
    }
}

fun generateInputOutput(): InOut {
    val random = Random()
    val input = if ((0..1).random() == 0) {
        listOf(random.nextFloat() * 2, random.nextFloat() * 2)
    } else {
        listOf(2 + random.nextFloat() * 2, 2 + random.nextFloat() * 2)
    }
    val out = if (input[0] <= 2) {
        listOf(1F, 0F)
    } else {
        listOf(0F, 1F)
    }
    return InOut(input, out)
}

val inOuts = (0..100).map { generateInputOutput() }

fun main() {
    val nt = MonoLayerNeuralNetwork(2, 2)

    repeat(10000) {
        for (inOut in inOuts) {
            val out = nt.run(inOut.input)

            nt.improve(inOut.input, inOut.output, out)
        }
        println(nt.evaluate())
    }

    val inOut = generateInputOutput()
    val out = nt.run(inOut.input)
    println("Input")
    println(inOuts.last().input.map { "%.2f".format(it) })
    println("Expected output")
    println(inOuts.last().output.map { "%.2f".format(it) })
    println("Actual output")
    println(out.map { "%.2f".format(it) })
}
