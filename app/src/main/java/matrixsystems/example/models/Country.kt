package matrixsystems.example.models

/**
 * Created by Shahbaz Hashmi on 26/04/19.
 */
data class Country(val name : String, var stateList : MutableList<State>?) {}