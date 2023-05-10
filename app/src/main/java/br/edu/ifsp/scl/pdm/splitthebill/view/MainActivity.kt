package br.edu.ifsp.scl.pdm.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import br.edu.ifsp.scl.pdm.splitthebill.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
}