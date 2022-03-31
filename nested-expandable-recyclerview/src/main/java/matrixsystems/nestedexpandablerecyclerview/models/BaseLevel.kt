package matrixsystems.nestedexpandablerecyclerview.models

/**
 * Created by shahbazhashmi on 31/03/22
 */
abstract class BaseLevel {
    var isExpanded = false
    abstract fun getName(): String
    abstract fun getLevel(): Int
}