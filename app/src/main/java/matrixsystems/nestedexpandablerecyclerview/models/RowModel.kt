package matrixsystems.nestedexpandablerecyclerview.models

import android.support.annotation.IntDef

/**
 * Created by Shahbaz Hashmi on 26/04/19.
 */
class RowModel {

    companion object {

        @IntDef(COUNTRY, STATE, CITY)
        @Retention(AnnotationRetention.SOURCE)
        annotation class RowType

        const val COUNTRY = 1
        const val STATE = 2
        const val CITY = 3
    }

    @RowType var type : Int

    lateinit var country : Country

    lateinit var state : State

    lateinit var city : City

    var isExpanded : Boolean

    constructor(@RowType type : Int, country : Country, isExpanded : Boolean = false){
        this.type = type
        this.country = country
        this.isExpanded = isExpanded
    }

    constructor(@RowType type : Int, state : State, isExpanded : Boolean = false){
        this.type = type
        this.state = state
        this.isExpanded = isExpanded
    }

    constructor(@RowType type : Int, city: City, isExpanded : Boolean = false){
        this.type = type
        this.city = city
        this.isExpanded = isExpanded
    }

}