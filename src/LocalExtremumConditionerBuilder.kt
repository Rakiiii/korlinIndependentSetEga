import Builders.ConditionerBuilding

class LocalExtremumConditionerBuilder (var steps : Int): ConditionerBuilding
{
    override fun Build(): Conditioner.Conditioner {
        return Conditioner.LocalExtremumConditioner( steps)
    }
}