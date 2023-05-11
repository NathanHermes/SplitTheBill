package br.edu.ifsp.scl.pdm.splitthebill.controller

import androidx.room.Room
import br.edu.ifsp.scl.pdm.splitthebill.model.Person
import br.edu.ifsp.scl.pdm.splitthebill.model.PersonDAO
import br.edu.ifsp.scl.pdm.splitthebill.model.PersonDAORoom
import br.edu.ifsp.scl.pdm.splitthebill.view.MainActivity

class PersonController(private val mainActivity: MainActivity) {
  private val personDAOImpl: PersonDAO = Room.databaseBuilder(
    mainActivity,
    PersonDAORoom::class.java,
    PersonDAORoom.PERSON_DATABASE_FILE
  ).build().getPersonDAO()

  fun findAllPeople() {
    Thread {
      val people = personDAOImpl.findAll()
      mainActivity.runOnUiThread {
        mainActivity.updatePeople(people)
      }
    }.start()
  }

  fun createPerson(person: Person) {
    Thread {
      personDAOImpl.create(person)
      findAllPeople()
    }.start()

  }
}