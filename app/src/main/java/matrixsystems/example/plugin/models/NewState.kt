package matrixsystems.example.plugin.models

import matrixsystems.nestedexpandablerecyclerview.models.Level2
import matrixsystems.nestedexpandablerecyclerview.models.Level3

/**
 * Created by shahbazhashmi on 31/03/22
 */
data class NewState(val stateName: String, val cities: List<NewCity>) : Level2() {
    override fun getChildList(): List<Level3> = cities
    override fun getName()= stateName
}
