package matrixsystems.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import matrixsystems.example.models.City
import matrixsystems.example.models.Country
import matrixsystems.example.models.RowModel
import matrixsystems.example.models.State

class MainActivity : AppCompatActivity() {

    lateinit var rowAdapter: RowAdapter
    lateinit var recyclerView : RecyclerView
    private val rows : MutableList<RowModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        rowAdapter = RowAdapter(this, rows)

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL,
            false
        )

        recyclerView.adapter = rowAdapter

        populateData()
    }


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
}
