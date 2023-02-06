package com.example.caculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.caculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val model: CaculatorModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setlistener()
        setContentView(binding.root)
        updateScene()
    }
    fun updateScene()
    {
        model.debugstack()
        //Log.d("update", "model show"+model.show())
        binding.answer.setText(model.show())
    }

    fun setlistener()
    {
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.button0.setOnClickListener{ model.inputNum("0");updateScene()}
        binding.button1.setOnClickListener{ model.inputNum("1");updateScene()}
        binding.button2.setOnClickListener{ model.inputNum("2");updateScene()}
        binding.button3.setOnClickListener{ model.inputNum("3");updateScene()}
        binding.button4.setOnClickListener{ model.inputNum("4");updateScene()}
        binding.button5.setOnClickListener{ model.inputNum("5");updateScene()}
        binding.button6.setOnClickListener{ model.inputNum("6");updateScene()}
        binding.button7.setOnClickListener{ model.inputNum("7");updateScene()}
        binding.button8.setOnClickListener{ model.inputNum("8");updateScene()}
        binding.button9.setOnClickListener{ model.inputNum("9");updateScene()}
        binding.buttonadd.setOnClickListener{ view:View->var code:Int=model.inputOp("+");wrongcheck(code,view);//if (code==1) updateScene();else model.debugstack()
            }
        binding.buttonsub.setOnClickListener{ view:View->var code:Int=model.inputOp("—");wrongcheck(code,view);//if (code==1) updateScene();else model.debugstack()
        }
        binding.buttonmul.setOnClickListener{ view:View->var code:Int=model.inputOp("*");wrongcheck(code,view);//if (code==1) updateScene();else model.debugstack()
        }
        binding.buttondiv.setOnClickListener{ view:View->var code:Int=model.inputOp("/");wrongcheck(code,view);//if (code==1) updateScene();else model.debugstack()
        }
        binding.buttondot.setOnClickListener{ model.inputDot();updateScene()}
        binding.buttonsqrt.setOnClickListener{ model.sqrt();updateScene()}
        binding.buttonequal.setOnClickListener{ view:View-> wrongcheck(model.equal(),view);updateScene()}
        binding.answer.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(editable: Editable) {
                Log.d("change",editable.toString())
                model.updateNum(editable.toString())
            }
        }

        )
    }

    fun  wrongcheck(code:Int,view:View)
    {
        when (code)
        {
            -1->{
                Snackbar.make(
                    this,
                    view,
                    "Divide by 0 is not allowed!",
                    BaseTransientBottomBar.LENGTH_SHORT).show()
            }
        }
    }
}