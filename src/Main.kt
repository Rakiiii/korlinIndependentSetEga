import Builders.EvolutionalGeneticalAlgorithmBuildingDirector
import Conditioner.ItterationConditionerBuilder
import Conditioner.LocalExtremumConditioner
import Crossover.DoubleDotCrossoverBuilder
import Crossover.SingleDotCrossoverBuilder
import Gene.BasicGeneBuilder
import Individual.AbstractIndividual
import Individual.BasicIndividualBuilder
import MutationManager.BasicMutationManagerBuilder
import Mutator.InversionMutatorBuilder
import Mutator.SaltationMutatorBuilder
import ParentSelector.RandomParentSelectorBuilder
import Population.BasicPopulationBuilder
import Selector.ProportionalSelectorBuilder
import java.io.File

fun main(args: Array<String>) {

    val direcor = EvolutionalGeneticalAlgorithmBuildingDirector(
        fitnessBuilder = GraphFitnesBuilder(),
        selectorBuilder = ProportionalSelectorBuilder(),
        parentSelectorBuilder = RandomParentSelectorBuilder(),
        modificatorBuilder = GraphPopulationModificatorBuilder(),
        mutatorBuilder = SaltationMutatorBuilder(),//InversionMutatorBuilder(),
        geneBuilder = BasicGeneBuilder(),
        individualBuilder = BasicIndividualBuilder(),
        populationBuilder = BasicPopulationBuilder(),
        crossoverBuilder = SingleDotCrossoverBuilder(),//DoubleDotCrossoverBuilder(),
        populationGeneratorBuilder = IndependentPopulationGeneratorBuilder(),
        mutationManagerBuilder = BasicMutationManagerBuilder(),
        parserBuilder = GraphParser(),
        conditionerBuilder = ItterationConditionerBuilder(1000)
    )
    var bestRes: AbstractIndividual? = null

    for (i in 0 until args[2].toInt()) {
        val alg = direcor.Build()


        alg.SetPopulationSize(3 * args[1].toInt() / 4)
        alg.SetFileWithItems(args[0])

        val result = alg.Run(0)

        println(result.toString())
        for (i in result.Gene().getCode().indices) {
            if (result.Gene().getCode()[i]) {
                print(i.toString() + " ")
            }
        }
        println()
        println(result.Fitness())
        bestRes?.let {
            if (bestRes!!.Fitness() < result.Fitness()) {
                bestRes = result
            }
        }
        if (bestRes == null) {
            bestRes = result
        }
    }

    var str = bestRes!!.Fitness().toString() + "\n"
    for (i in bestRes!!.Gene().getCode()) {
        if (i) {
            str += "1"
        } else {
            str += "0"
        }
    }
    File("result_" + args[0]).writeText(str)
}
