package com.example.stree20.utils.broadcastreciever

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import com.example.stree20.data.repository.StreeRepo
import com.example.stree20.utils.constants.Constants
import com.example.stree20.utils.extensions.toast
import com.example.stree20.utils.helpers.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SmsReceiver @Inject constructor(
    private val repo: StreeRepo,
    private val coroutineScope: CoroutineScope,
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val token = repo.getToken()!!
        extractMessage(intent) { msg, num ->
            postMessageToSlack(
                number = num,
                token = token,
                msg = msg,
                context = context
            )
        }
    }

    private fun postMessageToSlack(
        number: String,
        token: String,
        msg: String,
        context: Context?
    ) {
        coroutineScope.launch(Dispatchers.Main) {
            repo.observeAllStreeItem().collect { groupList ->
                for (j in groupList) {
                    if (j.source == number) {
                        repo.postMessage("Bearer $token", j.channel, msg)
                            .collect { response ->
                                when (response) {
                                    is Resource.Success -> {
                                        context?.toast("Success")
                                    }
                                    is Resource.Error -> {
                                        context?.toast("Failure")
                                    }
                                }
                            }

                    }
                }
            }
        }
    }


    @SuppressLint("NewApi")
    private fun extractMessage(intent: Intent?, onComplete: (String, String) -> Unit) {

        val body: StringBuilder = StringBuilder()
        var number: String?
        val bundle: Bundle? = intent?.extras
        val format = bundle?.getString("format")
        val messages: Array<SmsMessage?>

        if (bundle != null) {
            val isVersionM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            val msgObjects: Array<*>? = bundle.get(Constants.SMS_BUNDLE) as Array<*>?
            messages = arrayOfNulls(msgObjects!!.size)

            for (i in messages.indices) {
                if (isVersionM) {
                    // If Android version M or newer:
                    messages[i] = SmsMessage.createFromPdu(msgObjects[i] as ByteArray, format)
                } else {
                    // If Android version L or older:
                    messages[i] = SmsMessage.createFromPdu(msgObjects[i] as ByteArray)
                }
                messages[i]?.run {
                    body.append(messageBody)
                    number = originatingAddress.toString()
                    if (number!!.length == 13) {
                        number = "0" + number!!.substring(3)
                    }
                    onComplete(body.toString(), number!!)
                }
            }
        }
    }
}