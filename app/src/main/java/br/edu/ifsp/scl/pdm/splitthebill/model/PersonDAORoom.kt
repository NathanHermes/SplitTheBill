package br.edu.ifsp.scl.pdm.splitthebill.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Person::class], version = 1)
abstract class PersonDAORoom : RoomDatabase() {
  companion object Constants {
    const val PERSON_DATABASE_FILE = "people_room"
  }

  abstract fun getPersonDAO(): PersonDAO
}