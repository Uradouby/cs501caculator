package com.example.caculator

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.Stack
import kotlin.math.floor

class CaculatorModel : ViewModel() {

    private var numstack=Stack<Double>();
    private var opstack=Stack<Int>();
    private var buffer:String=""
    private var currentDot:Boolean=false

    init {
        buffer="0"
    }
    fun debugstack()
    {
        var str:String=" buffer\n"+ buffer+" \n opstack \n"
        for (op in opstack)
            str+=op.toString()+"\n"
        str+="numstack\n"
        for (num in numstack)
            str+=num.toString()+"\n"
        Log.d("update", str)
    }
    private fun format(str:String):String
    {
        var tmp=""
        if (str.endsWith(".")) tmp=str+"0"
            else tmp=str
        if (isNum(tmp))
        {
            var d:Double=tmp.toDouble()
            if (d-d.toInt()==0.0)
            {
                return d.toInt().toString()
            }
            return tmp
        }
        return str
    }

    private fun checkOp(str:String):Int
    {
        when(str)
        {
            "+"->{ return 0}
            "—"->{ return 1}
            "*"->{ return 2}
            "/"->{ return 3}
        }
        return -1
    }

    private fun isNum(str:String):Boolean
    {
        return str.matches("-?\\d+(\\.\\d+)?".toRegex())
    }

    private fun cal(op1:Double,op2:Double,op:Int):Double
    {
        when(op)
        {
            0->{return op1+op2}
            1->{return op1-op2}
            2->{return op1*op2}
            3->{return op1/op2}
        }
        return 0.0
    }

    fun inputOp(op:String):Int
    {
        buffer=format(buffer)
        if (isNum(buffer))
            numstack.push(buffer.toDouble())
        currentDot=false
        var check:Int=checkOp(op)
        if (opstack.size>1)
            if (opstack.peek()==2 || opstack.peek()==3)
                {
                    var Op:Int=opstack.pop()
                    var op2:Double=numstack.pop()
                    var op1:Double=numstack.pop()
                    currentDot=false
                    if (Op==3 && op2==0.0)
                    {
                        init()
                        return -1
                    }
                    numstack.push(cal(op1,op2,Op))
                    buffer=op
                    return 1
                }
        buffer=op
        Log.d("inputop",buffer)
        return 0
    }

    fun updateNum(n:String)
    {
        if (checkOp(buffer)!=-1)
        {
            opstack.push(checkOp(buffer))
            buffer=n
        }
        buffer=n
        currentDot = n.contains(".")
    }

    fun inputNum(n:String)
    {
        Log.d("inputnum",n)
        if (!currentDot) buffer=format(buffer)
        Log.d("inputnumbuffer1",buffer)
        var check:Int=checkOp(buffer)
        if (check!=-1)
        {
            opstack.push(check)
            buffer=n
            return
        }
        buffer += n;
        if (n=="0" && currentDot) else
            buffer=format(buffer)
        Log.d("inputnumbuffer2",buffer)

    }

    fun inputDot()
    {
        if (currentDot) return
        currentDot=true
        if (isNum(buffer))
        {
            buffer+="."
            return
        }
        var check:Int=checkOp(buffer)
        if (check!=-1)
        {
            opstack.push(check)
            buffer="0."
        }
    }

    fun sqrt()
    {
        buffer=format(buffer)
        if (isNum(buffer))
            buffer=format(Math.sqrt(buffer.toDouble()).toString())
        currentDot=true
        if (buffer.toDouble()-buffer.toDouble().toInt()==0.0) currentDot=false
        Log.d("sqrt",buffer+" "+currentDot.toString())
    }

    fun equal():Int
    {
        if (buffer.endsWith("."))buffer+="0"
        if (isNum(buffer))
            numstack.push(format(buffer).toDouble())
        while (!opstack.isEmpty())
        {
            var op:Int=opstack.pop()
            var op2:Double=numstack.pop()
            var op1:Double=numstack.pop()
            if (op==3 && op2==0.0)
            {
                init()
                return -1
            }
            numstack.push(cal(op1,op2,op))
        }
        buffer=format(numstack.pop().toString())
        if (buffer.toDouble()-buffer.toDouble().toInt()==0.0)  currentDot=false
        return 0
    }

    fun show():String
    {
        if (currentDot) return buffer
        if (isNum(buffer)) return format(buffer)
        return format(numstack.peek().toString())
    }

    fun init()
    {
        buffer="0"
        numstack.clear()
        opstack.clear()
        currentDot=false
    }
}