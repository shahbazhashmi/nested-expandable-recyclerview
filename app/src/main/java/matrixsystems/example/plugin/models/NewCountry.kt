package matrixsystems.example.plugin.models

import matrixsystems.nestedexpandablerecyclerview.models.Level1
import matrixsystems.nestedexpandablerecyclerview.models.Level2

/**
 * Created by shahbazhashmi on 31/03/22
 */
data class NewCountry(val countryName: String, val states: List<NewState>) : Level1() {
    override fun getChildList(): List<Level2> = states
    override fun getName() = countryName
}