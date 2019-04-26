package matrixsystems.nestedexpandablerecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import matrixsystems.nestedexpandablerecyclerview.models.City
import matrixsystems.nestedexpandablerecyclerview.models.Country
import matrixsystems.nestedexpandablerecyclerview.models.RowModel
import matrixsystems.nestedexpandablerecyclerview.models.State

class MainActivity : AppCompatActivity() {

    lateinit var rowAdapter: RowAdapter
    lateinit var rows : MutableList<RowModel>
    lateinit var recyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        rows = mutableListOf()
        rowAdapter = RowAdapter(this, rows)

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        recyclerView.adapter = rowAdapter

        populateData()
    }


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
}
