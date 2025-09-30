package com.example.snack

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class SnackFrag : Fragment(), GameSurfaceView.OnFinishGameCallBack {

    var surf: GameSurfaceView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.frag_snack, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surf = view.findViewById<GameSurfaceView?>(R.id.surface)
        surf?.setCallBack(this)
        val btn = view.findViewById<Button>(R.id.btn)
        btn.setOnClickListener{
            (activity as MainActivity).navController.popBackStack()
        }
    }

    override fun finishGame() {

        (activity as MainActivity).navController.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //surf?.stopGame()
    }


}