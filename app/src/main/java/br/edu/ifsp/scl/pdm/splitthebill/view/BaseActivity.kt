package br.edu.ifsp.scl.pdm.splitthebill.view

import androidx.appcompat.app.AppCompatActivity

sealed class BaseActivity: AppCompatActivity() {
  protected val EXTRA_NEW_PERSON = "NewPerson"
  protected val EXTRA_PEOPLE = "People"
}