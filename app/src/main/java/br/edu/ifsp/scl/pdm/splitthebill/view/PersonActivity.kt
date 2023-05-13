package br.edu.ifsp.scl.pdm.splitthebill.view

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.widget.addTextChangedListener
import br.edu.ifsp.scl.pdm.splitthebill.databinding.ActivityPersonBinding
import br.edu.ifsp.scl.pdm.splitthebill.model.Person

class PersonActivity : BaseActivity() {
  private val activityPersonBinding: ActivityPersonBinding by lazy {
    ActivityPersonBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(activityPersonBinding.root)

    val people = intent.getParcelableExtra(EXTRA_PEOPLE)
    Log.i("PEOPLE", people.toString())

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
          purchasedItemsAlertTv.visibility = VISIBLE
          purchasedItemsEt.isEnabled = false
        }
      }

      backBt.setOnClickListener { finish() }

      saveBt.setOnClickListener {
        val person: Person? = getValueFromView()

        if (person != null) {
          val resultIntent = Intent()
          resultIntent.putExtra(EXTRA_NEW_PERSON, person)

          setResult(RESULT_OK, resultIntent)
          finish()
        }
      }
    }
  }

  private fun getValueFromView(): Person? {
    with(activityPersonBinding) {
      if (nameEt.text.isBlank()) {
        nameAlertTv.visibility = VISIBLE
        return null
      }

      val totalValue: Float =
        if (totalValueEt.text.isBlank()) 0F
        else totalValueEt.text.toString().toFloat()

      val name: String = nameEt.text.toString()
      val purchasedItems: String = purchasedItemsEt.text.toString()

      return Person(null, name, totalValue, purchasedItems)
    }
  }
}