package monolayerneuralnetwork

import java.util.Random

class Weight(var value: Float) {
    override fun toString(): String {
        return "$value"
    }
}

val random = Random()

class Perceptron(inputNumber: Int) {
    var weights: List<Weight> =
        (0 until inputNumber).map { Weight(random.nextFloat() * 2 - 1) }
        private set

    private fun aggregate(inputs: List<Float>) = weights.zip(inputs).map { it.first.value * it.second }.sum()

    private fun activate(input: Float, threshold: Float = 0F): Float {
        // heavy side
        return if (input > threshold) 1F else 0F
    }

    fun run(inputs: List<Float>): Float {
        assert(weights.size == inputs.size)
        // Somme pondérée
        return activate(aggregate(inputs))
    }

    override fun toString(): String {
        return "P$weights"
    }

    fun mutate() {
        weights.random().value *= random.nextFloat() * 2
    }

    fun clone(): Perceptron {
        val new = Perceptron(0)
        new.weights = weights.map { Weight(it.value) }
        return new
    }
}
