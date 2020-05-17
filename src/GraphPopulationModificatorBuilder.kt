import Builders.ModificatorBuilding
import Modificator.AbstractModificator

class GraphPopulationModificatorBuilder : ModificatorBuilding {
    override fun Build(): AbstractModificator {
        return GraphPopulationModificator()
    }
}