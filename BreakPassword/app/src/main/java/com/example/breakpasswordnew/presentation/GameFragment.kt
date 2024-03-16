package com.example.breakpasswordnew.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.breakpasswordnew.App
import com.example.breakpasswordnew.R
import com.example.breakpasswordnew.SharedPreferencesUtils
import com.example.breakpasswordnew.databinding.GameFragmentBinding
import com.example.breakpasswordnew.domain.Complexity
import com.example.breakpasswordnew.presentation.adapters.PasswordAdapter
import com.example.breakpasswordnew.presentation.di.DaggerGameScreenComponent
import com.example.breakpasswordnew.presentation.di.GameScreenComponent
import com.example.breakpasswordnew.presentation.dialog.DialogInfo
import com.example.breakpasswordnew.presentation.dialog.DialogMenu
import com.example.breakpasswordnew.presentation.router.HasRouter
import javax.inject.Inject


class GameFragment : Fragment(), LifecycleOwner {

    @Inject
    lateinit var viewModel: GameScreenViewModel

    private var viewHolder: GameFragmentBinding? = null

    private var isShowKeyboard: Boolean = false

    private val diComponent: GameScreenComponent by lazy {
        DaggerGameScreenComponent.factory()
            .create((requireContext().applicationContext as App).component!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        diComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewHolder = GameFragmentBinding.inflate(inflater, container, false).also {
            it.viewModel = viewModel
            it.lifecycleOwner = viewLifecycleOwner
        }
        return viewHolder!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPasswordList()
        createKeyboard()
        initObservers()
        
        showDialogInfo()
    }

    private fun initPasswordList() {
        with(viewHolder!!) {
            passwordListView.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = PasswordAdapter(requireContext())
                addItemDecoration(
                    DividerItemDecoration(requireContext(), LinearLayout.VERTICAL).apply {
                        setDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.divider
                            )!!
                        )
                    }
                )
            }
        }
    }

    private fun initObservers() {
        with(viewModel) {
            passwords.observe(viewLifecycleOwner) {
                (viewHolder?.passwordListView?.adapter as? PasswordAdapter)?.setItems(it, level.numberCells)
            }
            createGame.observe(viewLifecycleOwner) {
                viewHolder?.passwordListView?.adapter = PasswordAdapter(requireContext()).apply {
                    setItems(it, level.numberCells)
                }
            }
            winEvent.observe(viewLifecycleOwner) {
                SharedPreferencesUtils.saveSettings(requireContext(), it, viewModel.level)
                val bestValue = SharedPreferencesUtils.getBestValue(requireContext(), viewModel.level)
                (requireActivity() as HasRouter).getRouter().showWinFragment(it, bestValue)
            }
            countAttempts.observe(viewLifecycleOwner) {
                viewHolder?.attemptsTitle?.text = String.format(requireContext().resources.getString(R.string.committedAttempts), it)
            }
            winState.observe(viewLifecycleOwner) {
                viewHolder?.keyboardView?.setWinState(it)
            }
        }

        viewHolder?.menuIcon?.setOnClickListener {
            DialogMenu(requireContext(), viewModel.level, object : DialogMenu.MenuListener {
                override fun chooseLevel(level: Complexity) {
                    viewModel.chooseLevel(level)
                }
            }).show()
        }

        viewHolder?.infoIcon?.setOnClickListener {
            showDialogInfo()
        }
    }

    private fun createKeyboard() {
        showKeyboard()
    }

    private fun showDialogInfo() {
        DialogInfo(requireContext()).show()
    }

    /**
     * Открываем кастомную клавиатуру
     */
    private fun showKeyboard() {
        if (!isShowKeyboard) {
            val bottomUp = AnimationUtils.loadAnimation(context, R.anim.slide_up_dialog)
            with(viewHolder!!.keyboardView) {
                startAnimation(bottomUp)
                visibility = View.VISIBLE
            }
            isShowKeyboard = true
        }
    }

    /**
     * Закрываем кастомную клавиатуру
     */
    fun closeKeyboard() {
        if (isShowKeyboard) {
            isShowKeyboard = false
            val bottomDown = AnimationUtils.loadAnimation(context, R.anim.slide_out_down)
            with(viewHolder!!.keyboardView) {
                startAnimation(bottomDown)
                visibility = View.GONE
            }
        }
    }
}