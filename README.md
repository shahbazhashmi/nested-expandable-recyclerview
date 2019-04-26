## nested-expendable-recyclerview

### Introduction

**Nested Expandable RecyclerView** is a simple and light weight demonstration to achieve nested (multi-level) and expandble/collapsable recyclerview in android. Suppose you want to add country as parent in the list. Then you need to add below code in RowModel.kt.

<img src="demo1.gif?raw=true" width="350px">

<br>

### Usage

1. Add model as per need. And make make their type constant and a contructor in RowModel.kt .

```markdown
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
        internal var name_tv: TextView
        internal var toggle_btn : ImageButton
        init {
            this.name_tv = itemView.findViewById(R.id.name_tv) as TextView
            this.toggle_btn = itemView.findViewById(R.id.toggle_btn) as ImageButton
        }
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
                    if (nextPosition == rowModels.size || rowModels.get(nextPosition).type === RowModel.COUNTRY) {
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
fun populateData(){
        var cityList1 : MutableList<City> = mutableListOf()
        cityList1.add(City("Patna"))
        cityList1.add(City("Gaya"))
        cityList1.add(City("Munger"))
        cityList1.add(City("Siwan"))
        cityList1.add(City("Chapra"))

        var cityList2 : MutableList<City> = mutableListOf()
        cityList2.add(City("Mumbai"))
        cityList2.add(City("Pune"))
        cityList2.add(City("Aurangabad"))
        cityList2.add(City("Nashik"))
        cityList2.add(City("Nagpur"))

        var stateList1 : MutableList<State> = mutableListOf()
        stateList1.add(State("Bihar", cityList1))
        stateList1.add(State("Maharashtra", cityList2))

        var stateList2 : MutableList<State> = mutableListOf()
        stateList2.add(State("New York", null))


        rows.add(RowModel(RowModel.COUNTRY, Country("India", stateList1)))
        rows.add(RowModel(RowModel.COUNTRY, Country("USA", stateList2)))

        rowAdapter.notifyDataSetChanged()
    }
```
