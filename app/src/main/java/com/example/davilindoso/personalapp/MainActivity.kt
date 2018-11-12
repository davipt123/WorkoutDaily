package com.example.davilindoso.personalapp

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var txtNome: TextView
    private lateinit var auth: FirebaseAuth
    private var user: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        recuperarDadosUsuario()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun recuperarDadosUsuario(){
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser

        var displayName = user!!.displayName
        var emailUser = user!!.email
        var profileUri = user!!.photoUrl
        user!!.providerData.forEach() {
            if (displayName == null && it.displayName != null) {
                displayName = it.displayName
            }
            if (profileUri == null && it.photoUrl != null) {
                profileUri = it.photoUrl
            }
        }
        if (displayName != null) {
            val navigationView:NavigationView? = findViewById(R.id.nav_view);
            val hView:View = navigationView!!.getHeaderView(0);
            val nav_user:TextView = hView.findViewById(R.id.userId);
            nav_user.setText(displayName)
        }else{
            Toast.makeText(this,"Não foi possível recuperar dados do Usuário",Toast.LENGTH_LONG)
        }

        if(emailUser != null){
            val navigationView:NavigationView? = findViewById(R.id.nav_view);
            val hView:View = navigationView!!.getHeaderView(0);
            val nav_user:TextView = hView.findViewById(R.id.userEmail);
            nav_user.setText(emailUser)
        }else{
            Toast.makeText(this,"Não foi possível recuperar dados do Usuário",Toast.LENGTH_LONG)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_alunos -> {
                // Handle the camera action
            }
            R.id.nav_exercicios -> {

            }
            R.id.nav_info -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
