package com.shakespace.advanceandroid.chapter02material

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.nav
import kotlinx.android.synthetic.main.fragment_material.*

/**
 * A simple [Fragment] subclass.
 */
class MaterialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_material, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_snack_bar.setOnClickListener {
            Snackbar.make(tv_snack_bar, "显示SnackBar", Snackbar.LENGTH_SHORT)
                .setAction("点击") {
                    Toast.makeText(requireContext(), "点击了SnackBar", Toast.LENGTH_SHORT).show()
                }.show()
        }

        tv_input_layout.setOnClickListener {
            it.nav(R.id.action_materialFragment_to_textInputFragment)
        }

        tv_tab_layout.setOnClickListener {
            it.nav(R.id.action_materialFragment_to_tabLayoutFragment)
        }

        tv_tab_act.setOnClickListener {
            startActivity(Intent(requireContext(), TabLayoutActivity::class.java))
        }

        tv_drawer.setOnClickListener {
            it.nav(R.id.action_materialFragment_to_drawerFragment)
        }

        tv_coordinate.setOnClickListener {
            it.nav(R.id.action_materialFragment_to_coordinatorFragment)
        }

        tv_behavior_one.setOnClickListener {
            it.nav(R.id.action_materialFragment_to_behaviorOneFragment)
        }

        tv_behavior_two.setOnClickListener {
            it.nav(R.id.action_materialFragment_to_behaviorTwoFragment)
        }

        tv_behavior_three.setOnClickListener {
            it.nav(R.id.action_materialFragment_to_behaviorThreeFragment)
        }


    }

}
