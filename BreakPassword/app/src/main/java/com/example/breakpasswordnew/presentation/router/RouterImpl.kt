package com.example.breakpasswordnew.presentation.router

import androidx.fragment.app.FragmentManager
import com.example.breakpasswordnew.R
import com.example.breakpasswordnew.presentation.GameFragment
import com.example.breakpasswordnew.presentation.WinFragment

class RouterImpl(
    private val fm: FragmentManager
): Router {

    override fun showGameFragment() {
        fm.beginTransaction()
            .replace(R.id.fragmentContainer, GameFragment())
            .commit()
    }

    override fun showWinFragment(attempts: Int, bestValue: Int) {
        fm.beginTransaction()
            .add(R.id.fragmentContainer, WinFragment.createInstance(attempts, bestValue))
            .addToBackStack(null)
            .commit()
    }

    override fun showRulesGameDialog() {
        TODO("Not yet implemented")
    }

    override fun onBackPressed() {
        fm.popBackStack()
    }
}