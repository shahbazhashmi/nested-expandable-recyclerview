package matrixsystems.example.plugin.models

import matrixsystems.nestedexpandablerecyclerview.models.Level3

/**
 * Created by shahbazhashmi on 31/03/22
 */
data class NewCity(val cityName: String): Level3() {
    override fun getName() = cityName
}
