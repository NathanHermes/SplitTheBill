package br.edu.ifsp.scl.pdm.splitthebill.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.AdapterView
import br.edu.ifsp.scl.pdm.splitthebill.adapter.PersonAdapter
import br.edu.ifsp.scl.pdm.splitthebill.databinding.ActivitySplitBinding
import br.edu.ifsp.scl.pdm.splitthebill.model.Person
import kotlin.math.abs

class SplitActivity : BaseActivity() {
  private val activitySplitBinding: ActivitySplitBinding by lazy {
    ActivitySplitBinding.inflate(layoutInflater)
  }

  private val splitPerson: MutableList<Person> = mutableListOf()
  private val personAdapter: PersonAdapter by lazy {
    PersonAdapter(this, splitPerson)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(activitySplitBinding.root)

    with(activitySplitBinding) {
      peopleLv.adapter = personAdapter

      val peopleList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableArrayListExtra(EXTRA_PEOPLE, Person::class.java)
      } else {
        intent.getParcelableArrayListExtra(EXTRA_PEOPLE)
      }

      peopleList?.let { _peopleList ->
        var totalValue = 0F
        _peopleList.forEach { _person ->
          _person.value?.let { _value ->
            totalValue += _value
          }
        }

        val splitTheBill = totalValue / _peopleList.size

        splitPerson.clear()
        _peopleList.forEach { _person ->
          var value = 0F
          _person.value?.let {
            value = it - splitTheBill
          }

          val name = if (value < 0F) {
            "${_person.name} - à pagar"
          } else if (value > 0F) {
            "${_person.name} - à receber"
          } else {
            _person.name
          }

          val absoluteValue = abs(value)
          splitPerson.add(Person(_person.id, name, absoluteValue, ""))
        }
        personAdapter.notifyDataSetChanged()

        peopleLv.onItemClickListener =
          AdapterView.OnItemClickListener { _, _, position, _ ->
            val person = peopleList[position]
            val personIntent = Intent(this@SplitActivity, PersonActivity::class.java)
            personIntent.putExtra(EXTRA_PERSON, person)
            personIntent.putExtra(EXTRA_VIEW_PERSON, true)
            startActivity(personIntent)
          }
      }

      backBt.setOnClickListener {
        finish()
      }
    }
  }
}