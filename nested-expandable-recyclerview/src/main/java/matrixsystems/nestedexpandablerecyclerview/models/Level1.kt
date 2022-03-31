package matrixsystems.nestedexpandablerecyclerview.models

/**
 * Created by shahbazhashmi on 31/03/22
 */
abstract class Level1 : BaseLevel() {
    companion object {
        const val level = 1
    }
    abstract fun getChildList() : List<Level2>?
    override fun getLevel()= level
}