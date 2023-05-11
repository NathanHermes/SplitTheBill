package br.edu.ifsp.scl.pdm.splitthebill.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.scl.pdm.splitthebill.R
import br.edu.ifsp.scl.pdm.splitthebill.adapter.PersonAdapter
import br.edu.ifsp.scl.pdm.splitthebill.databinding.ActivityMainBinding
import br.edu.ifsp.scl.pdm.splitthebill.model.Person

class MainActivity : BaseActivity() {
  private val activityMainBinding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }
  private lateinit var personActivityResultLauncher: ActivityResultLauncher<Intent>
  private val people: MutableList<Person> = mutableListOf()
  private val personAdapter: PersonAdapter by lazy {
    PersonAdapter(this, people)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(activityMainBinding.root)

    fillContactList()
    with(activityMainBinding) {
      peopleLv.adapter = personAdapter

      personActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

          if (result.resultCode == RESULT_OK) {
            val person = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
              result.data?.getParcelableExtra(EXTRA_NEW_PERSON, Person::class.java)
            } else {
              result.data?.getParcelableExtra(EXTRA_NEW_PERSON)
            }
          }
        }

      registerForContextMenu(peopleLv)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.addPersonMi -> {
        val personIntent = Intent(this, PersonActivity::class.java)
        personActivityResultLauncher.launch(personIntent)
        true
      }

      else -> false
    }
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