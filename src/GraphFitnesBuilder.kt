import Builders.FitnessBuilding
import Fitness.AbstractFitness

class GraphFitnesBuilder : FitnessBuilding
{
    override fun Build(): AbstractFitness {
        return GraphFitness()
    }
}