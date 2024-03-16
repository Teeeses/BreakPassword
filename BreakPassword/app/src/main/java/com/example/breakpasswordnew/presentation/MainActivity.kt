package com.example.breakpasswordnew.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.breakpasswordnew.R
import com.example.breakpasswordnew.presentation.router.HasRouter
import com.example.breakpasswordnew.presentation.router.Router
import com.example.breakpasswordnew.presentation.router.RouterImpl

class MainActivity : AppCompatActivity(), HasRouter {

    private val router: Router = RouterImpl(supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        router.showGameFragment()
    }

    override fun getRouter() = router

}