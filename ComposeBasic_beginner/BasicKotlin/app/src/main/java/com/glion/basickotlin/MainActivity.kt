package com.glion.basickotlin

import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.NullPointerException
import kotlin.system.measureTimeMillis
import kotlin.time.measureTime

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

            // TODO : fun main() 코드를 여기 넣으면됨
            // MEMO : Smart 코드
            val smartTvDevice = Smart.SmartTvDevice("Android TV", "Entertainment")
            smartTvDevice.turnOn()

            val smartLightDevice = Smart.SmartLightDevice("Google Light", "Utility")
            smartLightDevice.turnOn()

            val smartHome = Smart.SmartHome(smartTvDevice, smartLightDevice)
            smartHome.printSmartTvInfo()
            smartHome.printSmartLightInfo()

            // LaMDA 코드
            val coins: (Int) -> String = { quantity ->
                "$quantity quarters" // MEMO : 반환되는 String 값. 따로 return이 존재하진 않는다.
            }
            val cupcake: (Int) -> String = {
                "Have a cupcake!"
            }
            val treatFunction = Lamda.trickOrTreat(false, coins) // MEMO : 함수를 실행한 것이 아니라 참조한 것이므로 결과값이 나오지 않음
            // MEMO : coins 자리에 결과인 { $it quarters } 를 사용할 수도 있다.
            val treatFunction2 = Lamda.trickOrTreat(false, { "$it quearters" })
            // MEMO : 함수의 매개변수 마지막이 람다표현식일 경우 밖으로 뺄 수 있다. 의미는 동일하다.
            val treatFunction3 = Lamda.trickOrTreat(false) { "$it quearters" }
            val trickFunction = Lamda.trickOrTreat(true, null)
            repeat(4){ // MEMO : for문을 간결하게 표현할 수 있음.
                trickFunction()
            }
            treatFunction()


            // MEMO : 코루틴 실습
            Log.w("shhan", "============================= 코루틴 실습 시작 지점 =============================")
            Log.d("shhan","Weather forecast - 1") // MEMO : 동기호출 - Weather forecast 출력 완료 후 Sunny 출력됨
            // delay(1000) // MEMO : delay 함수는 코루틴이 제공하는 특수 정지 함수. 이 아래부분의 실행은 1000ms(1초) 지연됨. 사용을 위해서 runBlocking 으로 래핑해줘야함
            Log.d("shhan","Sunny")

            // MEMO : runBlocking 으로 래핑함 - 여전히 동기식. 람다 블록 내의 모든 작업이 완료되기 전까지 반환되지 않는다.
            runBlocking{
                Log.d("shhan","Weather forecast - 2")
                delay(1000)
                Log.d("shhan","Sunny")
            }

            // MEMO : 정지함수는 코루틴이나 다른 정지함수에서만 호출할 수 있음(suspend 키워드)
            runBlocking{
                Log.d("shhan","Weather forecast - 3")
                printForecast()
            }

            // MEMO : runBlocking 은 동기식이며, 본문의 각 함수는 순차적으로 이루어진다. 정지 함수가 하나씩 차례대로 실행된다.
            //  => 코루틴의 코드는 기본적으로 순차적으로 호출된다. 여러 작업을 동시에 실행하기 위해선 명시적으로 표현해줄 필요가 있다.
            val time = measureTimeMillis {
                runBlocking {
                    Log.d("shhan","Weather forecast - 4")
                    printForecast()
                    printTemperature()
                    Log.d("shhan","Have a good day")
                }
            }
            Log.d("shhan", "suspend 함수 2개 사용 runBlocking 소요시간 : ${time / 1000.0}")

            // MEMO : launch 를 사용하여 suspend 함수 2개를 자체 코루틴에서 실행하도록 변경한다.
            //  출력 자체는 동일하지만, 프로그램 시간상 더 빠르게 실행된다. 위의 코드에서는 suspend 함수 printForecast가 완료되어야 printTemperature가 실행됬다.
            //  launch를 하게되어 printForecast() 와 printTemperature가 동시에 실행된다.
            val time2 = measureTimeMillis {
                runBlocking {
                    Log.d("shhan","Weather forecast - 5")
                    launch{
                        printForecast()
                    }
                    launch{
                        printTemperature()
                    }
                    Log.d("shhan","Have a good day")
                }
            }
            Log.d("shhan", "suspend 함수 launch() 에서 동시실행 소요시간 : ${time2 / 1000.0}")

            // MEMO : 코루틴의 완료 시점과, 반환값에 관심이 있을때 async() / await() 사용 가능
            //  getForecast() 와 getTemperature() 가 동시에 실행되고, 두 값이 모두 반환이 완료되었을때 println 실행됨
            val time3 = measureTimeMillis {
                runBlocking{
                    Log.d("shhan","Weather forecast - 6")
                    val forecast: Deferred<String> = async { // MEMO : async로 실행 1
                        getForecast()
                    }
                    val temperature: Deferred<String> = async {// MEMO : async로 실행 2
                        getTemperature()
                    }
                    // MEMO : 위에서 async로 실행한 1,2 는 동시에 실행됨. 또한 Log문은 순차적으로 실행됨.
                    Log.d("shhan","Forecast : ${forecast.await()}, Temperature : ${temperature.await()}") // MEMO : 완료될때까지 대기
                    Log.d("shhan","Have a good day!")
                }
            }
            Log.d("shhan", "async 소요시간 : ${time3/1000.0}")

            // MEMO : coroutineScope 사용.
            runBlocking{
                Log.d("shhan","Weather forecast - 7")
                Log.d("shhan",getWeatherReport())
                Log.d("shhan","Have a good day!")
            }

            // MEMO : 예외 및 취소 처리. 서버에서 데이터를 가져올 때 예상치 못한 오류가 발생하는 것을 시뮬레이션
            runBlocking {
                Log.d("shhan","Weather forecast - 8")
                try{
                    Log.d("shhan",getWeatherReportWithError())
                } catch(e: NullPointerException){
                    Log.e("shhan", "의도적인 NullPointerException 발생 Catch")
                }
                Log.d("shhan","Have a good day!")
            }

            // MEMO : async에서 예외처리. 상위 코루틴인 getWeatherReportWithError2 의 async부분에서 에러처리. runBlocking 에서는 따로 에러처리 안해줌
            runBlocking{
                Log.d("shhan","Weather forecast - 9")
                Log.d("shhan",getWeatherReportWithError2())
                Log.d("shhan","Have a good day!")
            }
            Log.w("shhan", "============================= 코루틴 실습 종료 지점 =============================")
        }
    }

    // MEMO : 정지함수 테스트용 함수
    suspend fun printForecast(){
        delay(1000) // MEMO : 정지함수에서만 delay 호출 가능
        Log.d("shhan","Sunny")
    }
    suspend fun printTemperature(){
        delay(1000)
        Log.d("shhan","30\u00b0C")
    }
    suspend fun getForecast(): String{
        delay(1000) // MEMO : 정지함수에서만 delay 호출 가능
        return "Sunny"
    }
    suspend fun getTemperature(): String{
        delay(1000)
        return "30\u00b0C"
    }
    suspend fun getTemperatureWithError(): String{
        delay(500)
        throw NullPointerException("의도한 NullPointerException") // MEMO : 에러 발생시 getTemperatureWithError 함수의 실행이 중지됨
        return "30\u00b0C"
    }
    // MEMO : 태스크의 범위를 지정. coroutineScope 내에서 실행된 코루틴은 이 범위 안에 그룹화된다. 내부적으로 동시에 작업을 하더라도,
    //  모든 작업 전까지는 coroutineScope 가 반환되지 않으므로, 함수가 동기 작업인것처럼 보이게 된다.
    suspend fun getWeatherReport() = coroutineScope {
        val forecast = async { getForecast() }
        val temperature = async { getTemperature() }
        "${forecast.await()} ${temperature.await()}" // MEMO : coroutineScope 람다의 리턴값
    }

    // MEMO : suspend 함수 내에서 try-catch 처리
    suspend fun getWeatherReportWithError() = coroutineScope {
        val forecast = async { getForecast() }
        val temperature = async { getTemperatureWithError() }
        "${forecast.await()} ${temperature.await()}" // MEMO : coroutineScope 람다의 리턴값
    }
    // MEMO : coroutineScopre 내에서 try-catch
    suspend fun getWeatherReportWithError2() = coroutineScope {
        val forecast = async { getForecast() }
        val temperature = async {
            try {
                getTemperatureWithError()
            } catch (e: NullPointerException) {
                "{ Error Occur, Not Found }"
            }
        }
        "${forecast.await()} ${temperature.await()}" // MEMO : coroutineScope 람다의 리턴값
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