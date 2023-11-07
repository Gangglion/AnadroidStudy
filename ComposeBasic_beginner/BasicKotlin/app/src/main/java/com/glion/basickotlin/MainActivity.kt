package com.glion.basickotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.glion.basickotlin.ui.theme.BasicKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }

            // fun main() 코드를 여기 넣으면됨
            var smartDevice: SmartDevice = SmartTvDevice("Android TV", "Entertainment")
            smartDevice.turnOn()

            smartDevice = SmartLightDevice("Google Light", "Utility")
            smartDevice.turnOn()
        }
    }
}
open class SmartDevice(val name: String, val category: String){
    var deviceStatus = "online"
    constructor(name: String, category: String, statusCode: Int): this(name, category){
        deviceStatus = when(statusCode){
            0 -> "offline"
            1-> "online"
            else -> "unknown"
        }
    }
    open fun turnOn(){
        println("Smart device is turned on.")
    }

    open fun turnOff(){
        println("Smart device is turned off.")
    }
}

class SmartTvDevice(deviceName: String, deviceCategory: String) : SmartDevice(name = deviceName, category = deviceCategory){
    var speakerVolume = 2
        set(value){
            if(value in 0..100){
                field = value
            }
        }
    var channelNumber = 1
        set(value){
            if(value in 0..200){
                field = value
            }
        }

    override fun turnOn() {
        deviceStatus = "on"
        println(
            "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                    "set to $channelNumber."
        )
    }

    override fun turnOff() {
        deviceStatus = "off"
        println("$name turned off")
    }

    fun increaseSpeakerVolume(){
        speakerVolume++
        println("Speaker Volume increased to $speakerVolume.")
    }

    fun nextChannel(){
        channelNumber++
        println("Channel number increased to $channelNumber.")
    }
}

class SmartLightDevice(deviceName: String, deviceCategory: String) : SmartDevice(name = deviceName, category = deviceCategory){
    var brightnessLevel = 0
        set(value) {
            if (value in 0..100) {
                field = value
            }
        }

    override fun turnOn() {
        deviceStatus = "on"
        brightnessLevel = 2
        println("$name turned on. The brightness level is $brightnessLevel.")
    }

    override fun turnOff() {
        deviceStatus = "off"
        brightnessLevel = 0
        println("Smart Light turned off")
    }

    fun increaseBrightness() {
        brightnessLevel++
        println("Brightness increased to $brightnessLevel.")
    }
}

class SmartHome(val smartTvDevice: SmartTvDevice, val smartLightDevice: SmartLightDevice){
    fun turnOnTv(){
        smartTvDevice.turnOn()
    }

    fun turnOffTv(){
        smartTvDevice.turnOff()
    }

    fun increaseTvVolume() {
        smartTvDevice.increaseSpeakerVolume()
    }

    fun changeTvChannelToNext() {
        smartTvDevice.nextChannel()
    }

    fun turnOnLight() {
        smartLightDevice.turnOn()
    }

    fun turnOffLight() {
        smartLightDevice.turnOff()
    }

    fun increaseLightBrightness() {
        smartLightDevice.increaseBrightness()
    }

    fun turnOffAllDevices(){
        turnOffTv()
        turnOffLight()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicKotlinTheme {
        Greeting("Android")
    }
}