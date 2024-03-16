package com.example.breakpasswordnew.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.example.breakpasswordnew.R
import com.example.breakpasswordnew.databinding.WinFragmentBinding
import com.example.breakpasswordnew.presentation.router.HasRouter
import java.util.*

class WinFragment : Fragment() {

    private var viewHolder: WinFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewHolder = WinFragmentBinding.inflate(inflater, container, false)
        return viewHolder!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWinText()
        viewHolder?.root?.setOnClickListener {
            (requireActivity() as HasRouter).getRouter().onBackPressed()
        }
    }

    private fun setWinText() {
        val attempts = requireArguments().getInt(ATTEMPTS_KEY)
        val bestValue = requireArguments().getInt(BEST_VALUE_KEY)
        with(viewHolder!!) {
            tvWin.text = requireContext().resources.getString(getTextRes(attempts))
            if (bestValue > 0) {
                tvAttempts.visibility = View.VISIBLE
                tvAttempts.text = String.format(requireContext().resources.getString(R.string.committedAttemptsAndBest), attempts, bestValue)
            } else {
                tvAttempts.visibility = View.GONE
            }
        }
    }

    @StringRes
    private fun getTextRes(times: Int): Int {
        return when (times) {
            1, 2 -> R.string.win_1
            3, 4, 5 -> R.string.win_2
            in 6..10 -> {
                val count: Int = Random().nextInt(2)
                return if (count == 0) R.string.win_3 else R.string.win_4
            }
            else -> {
                val count: Int = Random().nextInt(3)
                return if (count == 0) R.string.win_8 else if (count == 1) R.string.win_9 else R.string.win_10
            }
        }
    }

    companion object {

        const val ATTEMPTS_KEY = "attempts_key"
        const val BEST_VALUE_KEY = "best_value_key"

        fun createInstance(attempts: Int, bestValue: Int): WinFragment {
            return WinFragment().apply {
                arguments = Bundle().apply {
                    putInt(ATTEMPTS_KEY, attempts)
                    putInt(BEST_VALUE_KEY, bestValue)
                }
            }
        }
    }
}