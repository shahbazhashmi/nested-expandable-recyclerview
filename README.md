## nested-expendable-recyclerview

### Introduction

**Nested Expandable RecyclerView** is a simple and light weight demonstration to achieve nested (multi-level) and expandable/collapsable recyclerview in android. For example - below country -> state -> city multilevel list.
<br>


<img src="demo1.gif?raw=true" width="350px">

<br>

### Usage

1. Add model as per need. And make make their type constant and a constructor in RowModel.kt .

```
const val COUNTRY = 1

@IntDef(COUNTRY, STATE, CITY)
        @Retention(AnnotationRetention.SOURCE)
        annotation class RowType

lateinit var country : Country

 constructor(@RowType type : Int, country : Country, isExpanded : Boolean = false){
        this.type = type
        this.country = country
        this.isExpanded = isExpanded
    }
```


2. Add ViewHolder class and other methods for newly added model in the adapter

```
class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val nameTv: TextView = itemView.findViewById(R.id.name_tv) as TextView
        internal val toggleBtn : ImageButton = itemView.findViewById(R.id.toggle_btn) as ImageButton
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder = when (viewType) {
            RowModel.COUNTRY -> CountryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row, parent, false))
            else -> CountryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row, parent, false))
        }
        return viewHolder
    }
```


3. You also need to do some change expand and collapse for your view type

```
fun expand(position: Int) {

        var nextPosition = position

        val row = rowModels[position]

        when (row.type) {

            RowModel.COUNTRY -> {

                /**
                 * add element just below of clicked row
                 */
                for (state in row.country.stateList!!) {
                    rowModels.add(++nextPosition, RowModel(RowModel.STATE, state))
                }

                notifyDataSetChanged()
            }
            
        actionLock = false
    }
    
    
    fun collapse(position: Int) {
        val row = rowModels[position]
        val nextPosition = position + 1

        when (row.type) {

            RowModel.COUNTRY -> {

                /**
                 * remove element from below until it ends or find another node of same type
                 */
                outerloop@ while (true) {
                    if (nextPosition == rowModels.size || rowModels.get(nextPosition).type == RowModel.COUNTRY) {
                        break@outerloop
                    }

                    rowModels.removeAt(nextPosition)
                }

                notifyDataSetChanged()
            }
            
        }

        actionLock = false
    }
```


4. Set source list (should be in nested structure) in the adapter

```
private fun populateData(){
        val cityList1 : MutableList<City> = mutableListOf<City>().also {
            it.add(City("Patna"))
            it.add(City("Gaya"))
            it.add(City("Munger"))
            it.add(City("Siwan"))
            it.add(City("Chapra"))
        }

        val cityList2 : MutableList<City> = mutableListOf<City>().also {
            it.add(City("Mumbai"))
            it.add(City("Pune"))
            it.add(City("Aurangabad"))
            it.add(City("Nashik"))
            it.add(City("Nagpur"))
        }


        val stateList1 : MutableList<State> = mutableListOf<State>().also {
            it.add(State("Bihar", cityList1))
            it.add(State("Maharashtra", cityList2))
        }

        val stateList2 : MutableList<State> = mutableListOf<State>().also {
            it.add(State("New York", null))
        }


        rows.add(RowModel(RowModel.COUNTRY, Country("India", stateList1)))
        rows.add(RowModel(RowModel.COUNTRY, Country("USA", stateList2)))

        rowAdapter.notifyItemInserted(rows.size)
    }
```
