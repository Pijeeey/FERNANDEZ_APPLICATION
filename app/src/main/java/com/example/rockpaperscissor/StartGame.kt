package com.example.rockpaperscissor

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rockpaperscissor.databinding.ActivityStartGameBinding

class StartGame : AppCompatActivity() {
    // SORRY PO SIR SABI PO NG MGA PINAPANUOOD KO SA YOUTUBE MAS MAGANDA DAW PO
    // VIEW BINDING KESA VIEWFINDING BY ID. INAARAL KO NAMAN PO TO DI KO LANG PO MAGETS SYNTAX
    // KUNG BAT MAY "?." yung binding pero po alam ko po ang uses nila
    // AND MAS SMOOTH ANIMATION NYA KUMPARA SA DICE NOON NA VIEWFIND BY ID
    // Sorry po sir kung binding ang nagamit ko at nirecycle ko lang ang source code na napanuod
    // ko na tutorial. pero alam ko po pano paikot ikot ng codes na to kahit po tanungin nyo po ako
    // kung ano gamit ng bawat line. at may mga enedit din po ako dito para mag trial and error po
    // kung nagana ba yung saken mismong code


    private var binding:ActivityStartGameBinding? = null

    private var animation1:AnimationDrawable? = null
    private var animation2:AnimationDrawable? = null
    private var setTime:CountDownTimer? = null

    private var allowPlaying = true

    private lateinit var selectionP1: String
    private lateinit var selectionBot: String

    private var scoreP1 = 0
    private var scoreBot = 0


// eto ung code mismo kung saan lalabas sa android simulator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartGameBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnrock?.setOnClickListener {
            onPlay("rock")
        }
        binding?.btnpaper?.setOnClickListener {
            onPlay("paper")
        }
        binding?.btnscissor?.setOnClickListener {
            onPlay("scissor")
        }
    }

    // ETO YUNG PINAKAANIMATION NG RANDOMIZE IMAGE,
    // napansin ko po kasi yung sa viewFindbyID sa roller dice nun hindi nagpiplay
    // yung mismong animatioon, nagrarandom lang talaga, dito po may xml na crineate (animation_rps)
    // kung saan nagpiplay mismong animation nyaa

    private fun playAnimation()
    {
        binding?.handIconP1?.setImageResource(0)
        binding?.handIconBot?.setImageResource(0)
        binding?.statusP1?.text = ""
        binding?.statusBot?.text = ""

        binding?.handIconP1?.setBackgroundResource(R.drawable.animation_rps)
        animation1 = binding?.handIconP1?.background as AnimationDrawable

        binding?.handIconBot?.setBackgroundResource(R.drawable.animation_rps)
        animation2 = binding?.handIconBot?.background as AnimationDrawable

        setTime = object :CountDownTimer(3000,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                animation1?.start()
                animation2?.start()
            }

            override fun onFinish() {
                animation1?.stop()
                animation2?.stop()
                allowPlaying = true

                binding?.handIconP1?.setBackgroundResource(0)
                binding?.handIconBot?.setBackgroundResource(0)

                // yung mga function na to tinawag kasi after ng animation, kailangan magtrigger yung
                // ibang function
                setSelectedIcon()
                setScore()
                end()

            }

        }.start()
    }

    // mismong game
    private fun onPlay(selection:String)
    {
        if (allowPlaying)
        {
            selectionBot = listOf("rock", "paper", "scissor").random()
            selectionP1 = selection
            allowPlaying = false
            playAnimation()
        }
    }

    //eto naman yung code pano naglink yung icon ng rock.png sa string na "rock" and so on..
    private fun setSelectedIcon()
    {
        when(selectionP1)
        {
            "rock" -> binding?.handIconP1?.setImageResource(R.drawable.rock)
            "paper" -> binding?.handIconP1?.setImageResource(R.drawable.paper)
            "scissor" -> binding?.handIconP1?.setImageResource(R.drawable.scissor)
        }

            when(selectionBot)
            {
                "rock" -> binding?.handIconBot?.setImageResource(R.drawable.rock)
                "paper" -> binding?.handIconBot?.setImageResource(R.drawable.paper)
                "scissor" -> binding?.handIconBot?.setImageResource(R.drawable.scissor)
            }

    }


    //dito naman yung logic ng pinakalaro kung sino mananalo
    private fun getResult():String
    {
        return if(selectionP1==selectionBot)
            "tie"
        else if (selectionP1=="rock" && selectionBot=="scissor" ||
                 selectionP1=="scissor" && selectionBot=="paper" ||
                 selectionP1=="paper" && selectionBot=="rock")
            "P1"
        else
            "Bot"
    }

    // dito nagseset ng score tapos star at pano nagpaplus yung star
    private fun setScore()
    {
        if(getResult()=="tie")
        {
            binding?.statusP1?.text="Tie"
            binding?.statusBot?.text="Tie"
        }
        else if(getResult()=="P1")
        {
            binding?.statusP1?.text="Win"
            binding?.statusBot?.text="Lose"
            scoreP1++
            when(scoreP1)
            {
                1-> binding?.p1Circle1?.setImageResource(R.drawable.circleshaded)
                2-> binding?.p1Circle2?.setImageResource(R.drawable.circleshaded)
                3-> binding?.p1Circle3?.setImageResource(R.drawable.circleshaded)
            }
        }
        else if(getResult()=="Bot")
        {
            binding?.statusP1?.text="Lose"
            binding?.statusBot?.text="Win"
            scoreBot++
            when(scoreBot)
            {
                1-> binding?.botCircle1?.setImageResource(R.drawable.circleshaded)
                2-> binding?.botCircle2?.setImageResource(R.drawable.circleshaded)
                3-> binding?.botCircle3?.setImageResource(R.drawable.circleshaded)
            }
        }
    }

//  NAKAKAPAGDECLARE NG WINNER
    private fun end()
    {
        //di nagana saken yung sa reference ko kaya binago ko  sya
        if(scoreP1 == 3)
        {
            //dito dederetso mismo sa winner.kt
            val intent = Intent(this,winner::class.java)
            startActivity(intent) //eto yung line kung saan dedertso sa winner class file
            finish()
        }
        else if(scoreBot == 3)
        {
            val intent = Intent(this,Loser::class.java)
            startActivity(intent)//eto naman sa lose class file
            finish()
        }
    }


    //to avoid data leakage daw
    override fun onDestroy() {
        super.onDestroy()
        binding = null
        setTime = null
    }
}