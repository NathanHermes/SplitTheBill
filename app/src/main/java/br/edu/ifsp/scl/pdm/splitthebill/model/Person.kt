package br.edu.ifsp.scl.pdm.splitthebill.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Person(
  @PrimaryKey(autoGenerate = true) val id: Int?,
  var name: String,
  var value: Float?,
  var items: String?,
): Parcelable