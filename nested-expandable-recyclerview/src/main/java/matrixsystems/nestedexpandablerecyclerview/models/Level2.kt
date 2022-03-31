package matrixsystems.nestedexpandablerecyclerview.models

/**
 * Created by shahbazhashmi on 31/03/22
 */
abstract class Level2 : BaseLevel() {
    companion object {
        const val level = 2
    }
    abstract fun getChildList() : List<Level3>?
    override fun getLevel()= level
}