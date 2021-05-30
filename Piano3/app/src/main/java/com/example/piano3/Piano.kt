package com.example.piano3

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import com.example.piano3.data.Note
import com.example.piano3.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_piano.view.*
import java.io.File
import java.io.FileOutputStream
import kotlin.system.measureNanoTime


class Piano : Fragment() {

    var onSave:((file: Uri) -> Unit)? = null

    private var _binding:FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2", "A2", "B2")
    private val halfTones = listOf("C#", "D#", "F#", "G#", "A#",  "C2#", "D2#", "F2#", "G2#", "A2#")
    private val score: MutableList<Note> = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root


        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        fullTones.forEach{
            val checkIfHalfToneIsNotNull = halfTones.getOrNull(halfTones.indexOf("$it#"))

            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)

            var start:Long = 0
            var timeElapsed:Long = 0

            fullTonePianoKey.onKeyDown = {
                if(!Timer.timerActive){
                    Timer.startTime = 0
                    Timer.startTimeLong = System.nanoTime()
                    Timer.timerActive = true
                }else{
                    Timer.startTime = System.nanoTime() - Timer.startTimeLong
                }

                start = System.nanoTime()
                println("Piano key down $it")
            }

            fullTonePianoKey.onKeyUp = {
                timeElapsed = System.nanoTime() - start
                val note = Note(it, Timer.startTime/1000000, timeElapsed/1000000)

                score.add(note)

                println("Piano key up $note")
            }

            ft.add(view.pianoKeys.id, fullTonePianoKey, "note_$it")

            if(checkIfHalfToneIsNotNull != null){
                val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(halfTones.get(halfTones.indexOf("$it#")))

                halfTonePianoKey.onKeyDown = {
                    if(!Timer.timerActive){
                        Timer.startTime = 0
                        Timer.startTimeLong = System.nanoTime()
                        Timer.timerActive = true
                    }else{
                        Timer.startTime = System.nanoTime() - Timer.startTimeLong
                    }

                    start = System.nanoTime()
                    println("Piano key down $it")
                }

                halfTonePianoKey.onKeyUp = {
                    timeElapsed = System.nanoTime() - start
                    val note = Note(it, Timer.startTime/1000000, timeElapsed/1000000)

                    score.add(note)

                    println("Piano key up $note")
                }

                ft.add(view.pianoKeys.id, halfTonePianoKey, "note_$it")
            }

        }


        ft.commit()

        view.saveNotesBt.setOnClickListener{
            var fileName = view.saveNotes.text.toString()
            val path = this.activity?.getExternalFilesDir(null)

            if(score.count() > 0 && path != null) {
                if (fileName.isNotEmpty()) {
                    fileName = "$fileName.music"

                    if (!checkIfFileExists(path, fileName)) {

                        val file = File(path, fileName)

                        FileOutputStream(file, true).bufferedWriter().use{ writer ->
                            score.forEach {
                                writer.write("${it.toString()}\n")
                            }
                            this.onSave?.invoke(file.toUri())
                            Timer.startTimeLong = 0
                            Timer.startTime = 0
                            Timer.timerActive = false
                            Toast.makeText(activity, "File successfully saved", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(activity, "A file already has that name", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(activity, "Filename cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(activity, "No notes inserted", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    fun checkIfFileExists(path:File, fileName:String): Boolean{
        val file = File(path, fileName)
        return file.exists()
    }

    object Timer{
        var timerActive:Boolean = false
        var startTime:Long = 0
        var startTimeLong:Long = 0
    }

}