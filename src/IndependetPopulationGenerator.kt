import Fitness.AbstractFitness
import Individual.AbstractIndividual
import Population.AbstractPopulation
import PopulationGenerator.AbstractPopulationGenerator
import java.util.*
import kotlin.collections.HashSet
import kotlin.random.Random

class IndependetPopulationGenerator : AbstractPopulationGenerator() {
    private lateinit var graph: Graph
    private lateinit var fitness: AbstractFitness
    override fun InitFitness(fit: AbstractFitness) {
        if (!::fitness.isInitialized) {
            fitness = fit
        } else throw Exception("ReInit Fitness in HungryPopulationGenerator")
    }

    override fun Init(_items: MutableList<Any>) {
        if (!::graph.isInitialized) {
            graph = _items[0] as Graph
        } else {
            throw Exception("Reinit grpah in independent population generator")
        }
    }

    override fun GeneratePopolation(size: Int): AbstractPopulation {
        if (!::graph.isInitialized && !::fitness.isInitialized && !this.isInitialized()) throw Exception("Independent populayion generator is not initialized")
        if (size > graph.edges.size) throw Exception("Population is too big for this graph")
        val result = this.populationBuilder.Build()

        val subResult = mutableListOf<AbstractIndividual>()

        val used: HashSet<Int> = hashSetOf()

        for (i in 0 until size) {
            val newGene = this.geneBuilder.Build()

            var rndVertex = Random.nextInt(0, graph.edges.size)
            while (!used.add(rndVertex)) {
                rndVertex = Random.nextInt(0, graph.edges.size)
            }

            val bannedVertex = hashSetOf<Int>()
            val subCode = BooleanArray(graph.edges.size) { false }
            subCode[rndVertex] = true
            //bannedVertex.add(rndVertex)
            for(j in graph.edges[rndVertex]){
                bannedVertex.add(j)
            }

            for(j in rndVertex until graph.edges.size){
                if( bannedVertex.add(j)){
                    subCode[j] = true
                    for(k in graph.edges[j]){
                        bannedVertex.add(k)
                    }
                }
            }

            newGene.Init(subCode)
            val newInd = this.individualBuilder.Build()
            newInd.Init(newGene)
            newInd.InitFitness(this.fitness)
            subResult.add(newInd)
        }

        result.Init(subResult)
        return result
    }
}