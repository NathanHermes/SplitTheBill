package br.edu.ifsp.scl.pdm.splitthebill.model

import androidx.room.*

@Dao
interface PersonDAO {
  @Query("SELECT * FROM Person WHERE id = :id")
  fun findPersonById(id: Int): Person?

  @Query("SELECT * FROM Person")
  fun findAll(): MutableList<Person>

  @Insert
  fun create(person: Person)

  @Update
  fun update(person: Person)

  @Delete
  fun delete(person: Person)
}