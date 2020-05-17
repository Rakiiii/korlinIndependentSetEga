import Builders.PopulationGeneratorBuilding
import PopulationGenerator.AbstractPopulationGenerator

class IndependentPopulationGeneratorBuilder : PopulationGeneratorBuilding {
    override fun Build(): AbstractPopulationGenerator {
        return IndependetPopulationGenerator()
    }
}