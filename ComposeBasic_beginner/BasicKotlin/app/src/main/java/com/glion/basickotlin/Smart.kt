package com.glion.basickotlin

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object Smart {
    // MEMO : SmartDevice(arg1: Type, arg2: Type) 은 공개 생성자. 이 생성자의 공개 상태를 바꾸기 위해선 다음과 같이 해주면 됨. 이제 이 생성자는 protected함
//  open 키워드는 클래스에 붙이면 상속가능함을, 함수나 변수에 붙이면 override 할 수 있도록 열어준다(open) 정도의 의미
    internal open class SmartDevice protected constructor (val name: String, val category: String){
        open var deviceStatus = "online"
            protected set
        open val deviceType = "unknown"
        constructor(name: String, category: String, statusCode: Int): this(name, category){
            deviceStatus = when(statusCode){
                0 -> "offline"
                1-> "online"
                else -> "unknown"
            }
        }
        open fun turnOn(){
            deviceStatus = "on"
        }

        open fun turnOff(){
            deviceStatus = "off"
        }

        open fun printDeviceInfo(){
            println("Device name : $name, category : $category, type : $deviceType")
        }
    }

    internal class SmartTvDevice(deviceName: String, deviceCategory: String) : SmartDevice(name = deviceName, category = deviceCategory){
        override val deviceType = "Smart TV"
        private var speakerVolume by RangeRegulator(2, 0, 100)
        private var channelNumber by RangeRegulator(1,0,100)
        override fun turnOn() {
            super.turnOn()
            println(
                "$name is turned on. Speaker volume is set to $speakerVolume and channel number is " +
                        "set to $channelNumber."
            )
        }

        override fun turnOff() {
            super.turnOff()
            println("$name turned off")
        }

        fun increaseSpeakerVolume(){
            speakerVolume++
            println("Speaker Volume increased to $speakerVolume.")
        }

        fun decreaseSpeakerVolume(){
            speakerVolume--
            println("Speaker Volume decreased to $speakerVolume")
        }

        fun nextChannel(){
            channelNumber++
            println("Channel number increased to $channelNumber.")
        }

        fun previousChannel(){
            channelNumber--
            println("Channel number decreased to $channelNumber")
        }
    }

    // MEMO : IS-A 관계 - SmartLightDevice는 SmartDevice를 상속하고있는 서브클래스이며, SmartDevice의 모든 동작을 할 수 있다. 포함관계이다.
    internal class SmartLightDevice(deviceName: String, deviceCategory: String) : SmartDevice(name = deviceName, category = deviceCategory){
        override val deviceType = "Smart Light"
        private var brightnessLevel by RangeRegulator(0,0,100)

        override fun turnOn() {
            super.turnOn()
            brightnessLevel = 2
            println("$name turned on. The brightness level is $brightnessLevel.")
        }

        override fun turnOff() {
            super.turnOff()
            brightnessLevel = 0
            println("Smart Light turned off")
        }

        fun increaseBrightness() {
            brightnessLevel++
            println("Brightness increased to $brightnessLevel.")
        }

        fun decreaseBrightness(){
            brightnessLevel--
            println("Brightness decreased to $brightnessLevel")
        }
    }

    // MEMO : HAS-A 관계 - SmartHome 라는 클래스는 SmartTvDevice 클래스를 매개변수로 가지고, 이를 이용할 수 있다.
    internal class SmartHome(val smartTvDevice: SmartTvDevice, val smartLightDevice: SmartLightDevice){
        var deviceTurnOnCount = 0
            private set

        fun printSmartTvInfo(){
            if(smartTvDevice.deviceStatus == "on")
                smartTvDevice.printDeviceInfo()
        }
        fun printSmartLightInfo(){
            if(smartLightDevice.deviceStatus == "on")
                smartLightDevice.printDeviceInfo()
        }
        fun turnOnTv(){
            deviceTurnOnCount++
            smartTvDevice.turnOn()
        }

        fun turnOffTv(){
            deviceTurnOnCount--
            smartTvDevice.turnOff()
        }

        fun increaseTvVolume() {
            smartTvDevice.increaseSpeakerVolume()
        }

        fun changeTvChannelToNext() {
            smartTvDevice.nextChannel()
        }

        fun changeTvChannelToPrevious(){
            smartTvDevice.previousChannel()
        }

        fun turnOnLight() {
            deviceTurnOnCount++
            smartLightDevice.turnOn()
        }

        fun turnOffLight() {
            deviceTurnOnCount--
            smartLightDevice.turnOff()
        }

        fun increaseLightBrightness() {
            smartLightDevice.increaseBrightness()
        }

        fun decreateLightBrightness(){
            smartLightDevice.decreaseBrightness()
        }

        fun turnOffAllDevices(){
            turnOffTv()
            turnOffLight()
        }
    }

    class RangeRegulator(
        initialValue: Int,
        private val minValue: Int,
        private val maxValue: Int
    ) : ReadWriteProperty<Any?, Int> {

        var fieldData = initialValue
        override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
            return fieldData
        }

        override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
            if(value in minValue..maxValue){
                fieldData = value
            }
        }

    }
}