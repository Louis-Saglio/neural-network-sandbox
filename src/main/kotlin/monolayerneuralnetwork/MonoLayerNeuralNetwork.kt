package monolayerneuralnetwork

class MonoLayerNeuralNetwork(perceptronNumber: Int, inputNumber: Int) {
    private var perceptrons = (0 until perceptronNumber).map { Perceptron(inputNumber) }
    private var learningRate = 0.01F
    var id = (1000 until 10000).random()

    fun run(inputs: List<Float>): List<Float> {
        assert(inputs.size == perceptrons.size)
        return perceptrons.map { it.run(inputs) }
    }

    fun improve(inputs: List<Float>, expectedOutputs: List<Float>, actualOutputs: List<Float>) {
        assert(inputs.size == expectedOutputs.size && actualOutputs.size == inputs.size)
        // Widrow-Hoff
        for (perceptron in perceptrons) {
            for (i in perceptron.weights.indices) {
                perceptron.weights[i].value += learningRate * (expectedOutputs[i] - actualOutputs[i]) * inputs[i]
            }
        }
        // learningRate *= 0.99F
    }

    override fun toString(): String {
        return "${"%.02f".format(evaluate())}\t$id\t$perceptrons"
    }

    fun mutate() {
        println(this)
        perceptrons.random().mutate()
        id = (1000 until 10000).random()
        println(this)
    }

    fun clone(): MonoLayerNeuralNetwork {
        val new = MonoLayerNeuralNetwork(0, 0)
        new.id = id
        new.perceptrons = perceptrons.map { it.clone() }
        return new
    }
}
