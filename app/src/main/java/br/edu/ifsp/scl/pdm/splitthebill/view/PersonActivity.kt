package br.edu.ifsp.scl.pdm.splitthebill.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.addTextChangedListener
import br.edu.ifsp.scl.pdm.splitthebill.R
import br.edu.ifsp.scl.pdm.splitthebill.databinding.ActivityPersonBinding
import br.edu.ifsp.scl.pdm.splitthebill.model.Person

class PersonActivity : BaseActivity() {
  private val activityPersonBinding: ActivityPersonBinding by lazy {
    ActivityPersonBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(activityPersonBinding.root)

    val receivePerson =
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        intent.getParcelableExtra(EXTRA_PERSON, Person::class.java)
      } else {
        intent.getParcelableExtra(EXTRA_PERSON)
      }
    receivePerson?.let { _person ->
      setValuesIntoView(_person)
    }

    with(activityPersonBinding) {
      nameEt.addTextChangedListener { text ->
        if (text.toString().isNotBlank()) {
          nameAlertTv.visibility = GONE
        }
      }

      totalValueEt.addTextChangedListener { text ->
        if (text.toString().isNotBlank()) {
          purchasedItemsAlertTv.visibility = GONE
          purchasedItemsEt.isEnabled = true
        } else if (text.toString().isBlank()) {
          purchasedItemsEt.setText("")
        }
      }

      purchasedItemsEt.setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus && totalValueEt.text.isBlank()) {
          purchasedItemsAlertTv.setText(R.string.purchased_items_alert_tv)
          purchasedItemsAlertTv.visibility = VISIBLE
          purchasedItemsEt.isEnabled = false
        }
      }

      backBt.setOnClickListener { finish() }

      saveBt.setOnClickListener {
        val person: Person? = getValueFromView(receivePerson?.id)

        if (person != null) {
          val resultIntent = Intent()
          resultIntent.putExtra(EXTRA_PERSON, person)

          setResult(RESULT_OK, resultIntent)
          finish()
        }
      }
    }
  }

  private fun getValueFromView(id: Int?): Person? {
    with(activityPersonBinding) {
      if (nameEt.text.isBlank()) {
        nameAlertTv.setText(R.string.name_blank_alert_tv)
        nameAlertTv.visibility = VISIBLE
      } else if (totalValueEt.text.isNotBlank() && purchasedItemsEt.text.isBlank()) {
        purchasedItemsAlertTv.setText(R.string.purchased_items_blank_alert_tv)
        purchasedItemsAlertTv.visibility = VISIBLE
      } else {
        val name: String = nameEt.text.toString()
        val totalValue: Float =
          if (totalValueEt.text.isBlank()) 0F
          else totalValueEt.text.toString().toFloat()
        val purchasedItems: String = purchasedItemsEt.text.toString()

        return Person(id, name, totalValue, purchasedItems)
      }
    }
    return null
  }

  private fun setValuesIntoView(person: Person) {
    val viewPerson = intent.getBooleanExtra(EXTRA_VIEW_PERSON, false)
    with(activityPersonBinding) {
      if (viewPerson) {
        nameEt.hint = person.name

        val strongTotalValue = "R$${person.value.toString()}"
        totalValueEt.hint = strongTotalValue.replace(".", ",")

        purchasedItemsEt.hint = person.items
      } else {
        nameEt.setText(person.name)

        if (person.value == null) {
          totalValueEt.setText("")
        } else {
          totalValueEt.setText(person.value.toString())
        }

        purchasedItemsEt.setText(person.items)
      }
      nameEt.isEnabled = !viewPerson
      totalValueEt.isEnabled = !viewPerson
      purchasedItemsEt.isEnabled = !viewPerson

      saveBt.visibility = if (viewPerson) GONE else VISIBLE
    }
  }
}