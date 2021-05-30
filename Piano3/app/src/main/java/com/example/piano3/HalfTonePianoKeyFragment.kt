package com.example.piano3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.piano3.databinding.FragmentHalfTonePianoKeyBinding
import kotlinx.android.synthetic.main.fragment_half_tone_piano_key.view.*


class HalfTonePianoKeyFragment : Fragment() {

    private var _binding: FragmentHalfTonePianoKeyBinding? = null
    private val binding get() = _binding!! // remember to add !!
    private lateinit var note:String

    var onKeyDown:((note:String) -> Unit)? = null
    var onKeyUp:((note:String) -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            note = it.getString("NOTE") ?: "?" // Elvis operator to allow default value in case of NULL
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHalfTonePianoKeyBinding.inflate(inflater)
        val view = binding.root // remember to add !! at binding get()

        view.halfKey.setOnTouchListener(object: View.OnTouchListener{

            var startTime = 0

            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event?.action){
                    MotionEvent.ACTION_DOWN -> this@HalfTonePianoKeyFragment.onKeyDown?.invoke(note)
                    MotionEvent.ACTION_UP -> this@HalfTonePianoKeyFragment.onKeyUp?.invoke(note)
                }
                return true
            }
        })

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(note: String) =
            HalfTonePianoKeyFragment().apply {
                arguments = Bundle().apply {
                    putString("NOTE", note)
                }
            }
    }
}