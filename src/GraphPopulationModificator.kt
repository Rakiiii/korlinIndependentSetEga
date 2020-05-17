import Individual.AbstractIndividual
import Modificator.AbstractModificator
import Population.AbstractPopulation

class GraphPopulationModificator : AbstractModificator() {
    private lateinit var graph: Graph
    override fun ModifyPopulation(population: AbstractPopulation, items: MutableList<Any>): AbstractPopulation {
        graph = items[0] as Graph

        val newPopulation = this.populationBuilder.Build()
        val subResult = mutableListOf<AbstractIndividual>()

        //println("start modification")
        for (i in 0 until population.Size()) {
            //println("modification indv:" + i.toString())
            var checkIndividual = population.Individual(i)

            while (!checkIndependent(checkIndividual.Gene().getCode())) {
                //printCode(checkIndividual.Gene().getCode())

                val newGene = this.geneBuilder.Build()
                var newCode = BooleanArray(graph.edges.size)
                for (i in newCode.indices) {
                    newCode[i] = checkIndividual.Gene().getCode()[i]
                }

                var charSet = constarctCharecteristic(checkIndividual.Gene().getCode())

                //printCharSet(charSet)
                while (charSet.size != 0) {
                    var maxind = 0
                    for (ind in charSet.indices) {
                        if (charSet[ind].second > charSet[maxind].second) {
                            maxind = ind
                        }
                    }

                    val removement = mutableListOf<Int>()
                    val vertex = charSet[maxind].first
                    removement.add(vertex)
                    //println("charset size before:" + charSet.size.toString())
                    for (j in charSet.indices) {
                        if (j != maxind && charSet[j].third.contains(vertex)) {
                            if (charSet[j].third.size > 1) {
                                charSet[j].third.remove(vertex)
                                charSet[j] = Triple(j, charSet[j].second - 1, charSet[j].third)
                            } else {
                                removement.add(charSet[j].first)
                            }
                        }
                    }
                    //println("charset size after:" + charSet.size.toString())
                    val newCharSet = mutableListOf<Triple<Int, Int, MutableList<Int>>>()
                    for (j in charSet) {
                        if (!removement.contains(j.first)) {
                            newCharSet.add(j)
                        }
                    }
                    //println("new charset size after:" + newCharSet.size.toString())
                    //println("removed:" + charSet[maxind].first.toString())
                    //charSet.removeAt(maxind)
                    newCode[vertex] = false
                    charSet = newCharSet
                }
                //printCode(newCode)
                val newInd = this.individualBuilder.Build()
                newGene.Init(newCode)
                newInd.Init(newGene)
                newInd.InitFitness(checkIndividual.GetFitness())
                checkIndividual = newInd
            }

            subResult.add(checkIndividual)
        }

        newPopulation.Init(subResult)
        return newPopulation
    }

    fun checkIndependent(c: BooleanArray): Boolean {
        for (i in c.indices) {
            if (c[i]) {
                for (v in graph.edges[i]) {
                    if (c[v]) {
                        return false
                    }
                }
            }
        }
        return true
    }

    fun constarctCharecteristic(c: BooleanArray): MutableList<Triple<Int, Int, MutableList<Int>>> {
        val result = mutableListOf<Triple<Int, Int, MutableList<Int>>>()
        for (i in c.indices) {
            if (c[i]) {
                val set = mutableListOf<Int>()
                for (v in graph.edges[i]) {
                    if (c[v]) {
                        set.add(v)
                    }
                }
                if (set.size > 0) {
                    result.add(Triple(i, set.size, set))
                }

            }
        }
        return result
    }
}
/*
fun printCharSet(v : MutableList<Triple<Int, Int, MutableList<Int>>>){
    for(i in v){
        print("vertex:"+i.first.toString()+" size:"+i.second.toString()+" set:")
        for (j in i.third){
            print(j.toString()+" ")
        }
    }
}

fun printCode(c: BooleanArray) {
    for (i in c) {
        if (i) {
            print("1 ")
        } else {
            print("0 ")
        }
    }
}*/