package com.myoptimind.lilo_xpress.shared

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import android.graphics.BitmapFactory
import android.text.format.DateFormat
import androidx.core.app.ActivityCompat.startActivityForResult
import com.epson.epos2.Epos2Exception
import com.epson.epos2.linedisplay.LineDisplay
import com.epson.epos2.printer.Printer
import com.epson.epos2.printer.PrinterStatusInfo
import com.epson.epos2.printer.ReceiveListener
import com.epson.eposprint.Print
import com.myoptimind.lilo_xpress.R
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


private const val DEVICE_ADDRESS = "BT:00:01:90:76:B8:08"

private const val PRINTER_ERROR_404 = "Printer is offline."

private const val PRINTER_LINE_WIDTH = 38

@Singleton
class LiloPrinter
@Inject
constructor(
    val context: Context // pass application context here
): ReceiveListener {

    private var mPrinter: Printer? = null

    init {
        initializePrinter()
    }

    fun initializePrinter(): Boolean {

        try {
            mPrinter = Printer(
                Printer.TM_P20,
                Printer.MODEL_ANK,
                context
            )
        } catch (e: java.lang.Exception) {
            Timber.e(e.message)
            return false
        }
        mPrinter?.setReceiveEventListener(this)
        return true
    }

    private fun connectPrinter(): Boolean {

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
        var address = ""

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        pairedDevices?.forEach { device ->
            val deviceName = device.name
            if(deviceName.toUpperCase().contains("TM-P20")){
                address = device.address // MAC address
            }
        }

        if(address.isNotBlank()){
            return try {
                if(mPrinter?.status?.connection == LineDisplay.FALSE){
                    mPrinter?.connect(
                        "BT:$address",
                        Printer.PARAM_DEFAULT
                    )
                    return true
                }else if(mPrinter?.status?.connection == LineDisplay.TRUE){
                    return true
                }else{
                    throw Exception("Unable to connect to printer.")

                }
            } catch (e: Exception) {
                Timber.e(e.message)
                throw Exception("Printer is offline.")
            }

        }else{
            throw Exception("Printer is offline.")
        }
        return false
    }

    suspend fun printReceipt(
        header: String = "CESB LETTERHEAD",
        dateAndTime: String,
        fullname: String,
        agency: String,
        attachedAgency: String,
        email: String,
        divisionName: String,
        personVisited: String,
        purposeOfVisit: String,
        temperature: String,
        region: String,
        city: String,
        pinCode: String? = null,
        loginString: String,
        logoutString: String? = null,
        duration: String? = null
    ): Boolean {
        val printer = mPrinter ?: throw Exception("Printer not initialized.")

        printer.clearCommandBuffer()

        val logoData = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
        val textData = StringBuilder()


        printer.addTextFont(Printer.FONT_D)
        printer.addTextAlign(Printer.ALIGN_CENTER)

        textData.append("Republic of the Philippines\n")
        textData.append("CAREER EXECUTIVE SERVICE BOARD\n")
        textData.append("No. 3 Marcelino St., Isidora Hills\n")
        textData.append("Brgy. Holy Spirit, Diliman\n")
        textData.append("Quezon City 1127, Philippines\n")
        textData.append("www.cesboard.gov.ph\n")

        textData.append("------------------------------\n")
        printer.addAndClear(textData)


        printer.addImage(
            logoData, 0, 0,
            logoData.getWidth(),
            logoData.getHeight(),
            Printer.COLOR_1,
            Printer.MODE_MONO,
            Printer.HALFTONE_DITHER,
            Printer.PARAM_DEFAULT.toDouble(),
            Printer.COMPRESS_AUTO
        )

        printer.addFeedLine(2)

        textData.append("The CESB Express\n")
        textData.append("Log-in, Log-out System\n")
        textData.append("------------------------------\n")
        printer.addAndClear(textData)
        printer.addTextSize(2,1)
        textData.append("APPEARANCE RECEIPT\n")
        printer.addAndClear(textData)
        printer.addFeedLine(1)
        printer.addTextSize(1,1)
        textData.append("Guest : $fullname\n")
        textData.append("Department/Agency: ${agency.wordWrapped(5)}\n")
        textData.append("Attached Agency: $attachedAgency\n".wordWrapped())
        textData.append("Person to Visit: $personVisited\n".wordWrapped())
        textData.append("Purpose of Visit: $purposeOfVisit\n".wordWrapped())
        printer.addAndClear(textData)
        if(pinCode != null){
            printer.addFeedLine(1)
            textData.append("PIN CODE:\n")
            printer.addAndClear(textData)
            printer.addFeedLine(1)
            printer.addTextSize(2,2)
            printer.addText("${pinCode.replace("0","Ø")}\n")
            printer.addTextSize(1,1)
        }

        printer.addFeedLine(1)

        if(logoutString != null){
            printer.addText(getSummaryOfVisit(
                loginString,
                logoutString,
                duration
            ))
            printer.addFeedLine(1)
        }



        // Footer
        printer.addTextFont(Printer.FONT_C)
        printer.addText(getFooter())
        printer.addFeedLine(1)
        printer.addCut(Printer.CUT_FEED)
        printer.addSound(Printer.PATTERN_C,5,Printer.PARAM_DEFAULT)



        if(connectPrinter()){
            try{
                printer.sendData(Printer.PARAM_DEFAULT)
            }catch (exception: Exception){
                throw Exception(PRINTER_ERROR_404)
            }
        }

        return true
    }

    private fun getSummaryOfVisit(loginString: String, logoutString: String?, duration: String?): String {
        return """
            Summary of visit:
            Login:  $loginString
            Logout: ${logoutString ?: "-"}
            Duration: ${duration ?: "-" }
            
        """.trimIndent()
    }

    fun String.wordWrapped(lineWidth: Int = PRINTER_LINE_WIDTH): String {
        val words = this.split(' ')
        val sb = StringBuilder(words[0])
        var spaceLeft = PRINTER_LINE_WIDTH - words[0].length
        for (word in words.drop(1)) {
            val len = word.length
            if (len + 1 > spaceLeft) {
                sb.append("\n").append(word)
                spaceLeft = PRINTER_LINE_WIDTH - len
            }
            else {
                sb.append(" ").append(word)
                spaceLeft -= (len + 1)
            }
        }
        return sb.toString()
    }




    private fun getFooter(): String {
        return """
            THIS SERVES AS YOUR CERTIFICATE
            OF APPEARANCE.
            
            Date Printed: ${DateFormat.format("d MMMM yyyy", Date())}
              ${DateFormat.format("hh:mmaa", Date())}
            
            For compliments or suggestions, please
            email us at feedback@cesboard.gov.ph
            
            "Isang Karangalan ang Maglingkod sa Bayan" 
        """.trimIndent()
    }

    private fun Printer.addAndClear(builder: StringBuilder){
        this.addText(builder.toString())
        builder.delete(0,builder.length)
    }

    override fun onPtrReceive(p0: Printer?, p1: Int, p2: PrinterStatusInfo?, p3: String?) {
//        TODO("Not yet implemented")
        Timber.e("on pointer receive")
        // not needed
    }
}