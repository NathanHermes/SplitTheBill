package br.edu.ifsp.scl.pdm.splitthebill.controller

import androidx.room.Room
import br.edu.ifsp.scl.pdm.splitthebill.model.Person
import br.edu.ifsp.scl.pdm.splitthebill.model.PersonDAO
import br.edu.ifsp.scl.pdm.splitthebill.model.PersonDAORoom
import br.edu.ifsp.scl.pdm.splitthebill.view.MainActivity
import kotlinx.coroutines.*

class PersonController(private val mainActivity: MainActivity) {
  private val personDAOImpl: PersonDAO = Room.databaseBuilder(
    mainActivity,
    PersonDAORoom::class.java,
    PersonDAORoom.PERSON_DATABASE_FILE
  ).build().getPersonDAO()

  fun findAllPeople() {
    CoroutineScope(Dispatchers.IO).launch {
      val result = async { personDAOImpl.findAll() }
      val people = result.await()

      withContext(Dispatchers.Main) {
        mainActivity.updatePeople(people)
      }
    }
  }

  fun createPerson(_person: Person) {
    CoroutineScope(Dispatchers.IO).launch {
      val result = async { personDAOImpl.create(_person) }
      result.await()

      withContext(Dispatchers.Main) {
        findAllPeople()
      }
    }
  }

  fun updatePerson(_person: Person) {
    CoroutineScope(Dispatchers.IO).launch {
      val result = async { personDAOImpl.update(_person) }
      result.await()

      withContext(Dispatchers.Main) {
        findAllPeople()
      }
    }
  }

  fun deletePerson(_person: Person) {
    CoroutineScope(Dispatchers.IO).launch {
      val result = async { personDAOImpl.delete(_person) }
      result.await()

      withContext(Dispatchers.Main) {
        findAllPeople()
      }
    }
  }
}