import Fitness.AbstractFitness

class GraphFitness : AbstractFitness()
{
    private lateinit var graph : Graph
    override fun Fitness(c: BooleanArray): Int {
        return c.count { s -> s }
    }

    override fun Init(ites: MutableList<Any>) {
       this.graph = ites[0] as Graph
    }

    override fun TotalWeight(c: BooleanArray): Int {
        return this.Fitness(c)
    }
}