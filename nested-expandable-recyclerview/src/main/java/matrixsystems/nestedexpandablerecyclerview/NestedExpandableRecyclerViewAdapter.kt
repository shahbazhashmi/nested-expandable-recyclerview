package matrixsystems.nestedexpandablerecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import matrixsystems.nestedexpandablerecyclerview.models.BaseLevel
import matrixsystems.nestedexpandablerecyclerview.models.Level1
import matrixsystems.nestedexpandablerecyclerview.models.Level2
import matrixsystems.nestedexpandablerecyclerview.models.Level3

/**
 * Created by shahbazhashmi on 31/03/22
 */
class NestedExpandableRecyclerViewAdapter(
    private val context: Context,
    private val openIcon: Int,
    private val closeIcon: Int,
    private var rowModels: MutableList<BaseLevel>
)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    /**
     * flag to restrict expand / collapse action it is already expanding / collapsing
     */
    private var actionLock = false

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val nameTv: TextView = itemView.findViewById(R.id.name_tv) as TextView
        internal val toggleBtn : ImageButton = itemView.findViewById(R.id.toggle_btn) as ImageButton
    }

    class StateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val nameTv: TextView = itemView.findViewById(R.id.name_tv) as TextView
        internal val toggleBtn : ImageButton = itemView.findViewById(R.id.toggle_btn) as ImageButton
    }

    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val nameTv: TextView = itemView.findViewById(R.id.name_tv) as TextView
    }

    override fun getItemViewType(position: Int): Int {
        return rowModels[position].getLevel()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            Level1.level -> CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.level1_row, parent, false))
            Level2.level -> StateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.level2_row, parent, false))
            Level3.level -> CityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.level3_row, parent, false))
            else -> CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.level1_row, parent, false))
        }
        return viewHolder
    }


    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val row = rowModels[p1]

        when(row.getLevel()){
            Level1.level -> {
                (p0 as CountryViewHolder).nameTv.text = row.getName()
                val level1Object = row as Level1
                if(level1Object.getChildList().isNullOrEmpty()) {
                    p0.toggleBtn.visibility = View.GONE
                }
                else {
                    if(p0.toggleBtn.visibility == View.GONE){
                        p0.toggleBtn.visibility = View.VISIBLE
                    }

                    if (row.isExpanded) {
                        p0.toggleBtn.background =
                            ContextCompat.getDrawable(context,
                                closeIcon
                            )
                    } else {
                        p0.toggleBtn.background =
                            ContextCompat.getDrawable(context,
                                openIcon
                            )
                    }

                    p0.toggleBtn.setOnClickListener {
                        if (!actionLock) {
                            actionLock = true
                            if (row.isExpanded) {
                                row.isExpanded = false
                                collapse(p1)
                            } else {
                                row.isExpanded = true
                                expand(p1)
                            }
                        }
                    }
                }
            }
            Level2.level -> {
                (p0 as StateViewHolder).nameTv.text = row.getName()
                val level2Object = row as Level2
                if(level2Object.getChildList().isNullOrEmpty()) {
                    p0.toggleBtn.visibility = View.GONE
                }
                else {
                    if(p0.toggleBtn.visibility == View.GONE){
                        p0.toggleBtn.visibility = View.VISIBLE
                    }

                    if (row.isExpanded) {
                        p0.toggleBtn.background =
                            ContextCompat.getDrawable(context,
                                closeIcon
                            )
                    } else {
                        p0.toggleBtn.background =
                            ContextCompat.getDrawable(context,
                                openIcon
                            )
                    }
                }

                p0.toggleBtn.setOnClickListener {
                    if (!actionLock) {
                        actionLock = true
                        if (row.isExpanded) {
                            row.isExpanded = false
                            collapse(p1)
                        } else {
                            row.isExpanded = true
                            expand(p1)
                        }
                    }
                }
            }
            Level3.level -> {
                (p0 as CityViewHolder).nameTv.text = row.getName()
            }
        }
    }


    private fun expand(position: Int) {

        var nextPosition = position

        val row = rowModels[position]

        when (row.getLevel()) {

            Level1.level -> {

                /**
                 * add element just below of clicked row
                 */
                for (state in (row as Level2).getChildList()!!) {
                    rowModels.add(++nextPosition, RowModel(RowModel.STATE, state))
                }

                notifyDataSetChanged()
            }

            Level2.level -> {

                /**
                 * add element just below of clicked row
                 */
                for (city in row.state.cityList!!) {
                    rowModels.add(++nextPosition, RowModel(RowModel.CITY, city))
                }

                notifyDataSetChanged()
            }
        }

        actionLock = false
    }

    private fun collapse(position: Int) {
        val row = rowModels[position]
        val nextPosition = position + 1

        when (row.getLevel()) {

            Level1.level -> {

                /**
                 * remove element from below until it ends or find another node of same type
                 */
                outerloop@ while (true) {
                    if (nextPosition == rowModels.size || rowModels.get(nextPosition).type === RowModel.COUNTRY) {
                        break@outerloop
                    }

                    rowModels.removeAt(nextPosition)
                }

                notifyDataSetChanged()
            }

            Level2.level -> {

                /**
                 * remove element from below until it ends or find another node of same type or find another parent node
                 */
                outerloop@ while (true) {
                    if (nextPosition == rowModels.size || rowModels.get(nextPosition).type === RowModel.COUNTRY || rowModels.get(nextPosition).type === RowModel.STATE
                    ) {
                        break@outerloop
                    }

                    rowModels.removeAt(nextPosition)
                }

                notifyDataSetChanged()
            }
        }

        actionLock = false
    }


    override fun getItemCount() = rowModels.size
}