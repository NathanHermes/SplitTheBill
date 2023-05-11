package br.edu.ifsp.scl.pdm.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import br.edu.ifsp.scl.pdm.splitthebill.R
import br.edu.ifsp.scl.pdm.splitthebill.adapter.PersonAdapter
import br.edu.ifsp.scl.pdm.splitthebill.databinding.ActivityMainBinding
import br.edu.ifsp.scl.pdm.splitthebill.model.Person

class MainActivity : AppCompatActivity() {
  private val activityMainBinding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }
  private val people: MutableList<Person> = mutableListOf()
  private val personAdapter: PersonAdapter by lazy {
    PersonAdapter(this, people)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(activityMainBinding.root)

    fillContactList();
    with(activityMainBinding) {
      peopleLv.adapter = personAdapter
    }

  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  private fun fillContactList() {
    for (index: Int in 1..20) {
      people.add(
        Person(
          index, "Name $index", 10F, "Itens: Alguma coisa"
        )
      )
    }
  }
}