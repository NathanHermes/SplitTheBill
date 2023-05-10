package br.edu.ifsp.scl.pdm.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.scl.pdm.splitthebill.R
import br.edu.ifsp.scl.pdm.splitthebill.databinding.TilePersonBinding
import br.edu.ifsp.scl.pdm.splitthebill.model.Person

class PersonAdapter(context: Context, private val people: MutableList<Person>) :
  ArrayAdapter<Person>(context, R.layout.tile_person, people) {
  private lateinit var tilePersonBinding: TilePersonBinding

  override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup): View {
    val person: Person = people[position]
    var tilePersonView = convertView

    if (tilePersonView == null) {
      tilePersonBinding = TilePersonBinding.inflate(
        context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater,
        parent, false
      )
      tilePersonView = tilePersonBinding.root

      val tilePersonViewHolder = TilePersonViewHolder(
        tilePersonView.findViewById(R.id.nameTv),
        tilePersonView.findViewById(R.id.productsTv),
        tilePersonView.findViewById(R.id.productPrinceTv)
      )

      tilePersonView.tag = tilePersonViewHolder
    }

    with(tilePersonView.tag as TilePersonViewHolder) {
      nameTv.text = person.name
      productsTv.text = person.purchasedItems
      productPrinceTv.text = person.totalSpent.toString()
    }

    return tilePersonView
  }

  private data class TilePersonViewHolder(
    val nameTv: TextView,
    val productsTv: TextView,
    val productPrinceTv: TextView
  )
}