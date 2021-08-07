package com.example.moviesearch



import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.widget.Toast
import kotlin.random.Random

// API класса:
// объявление класса
// вызов функии initHandler
// используем поток с помощью переменной handler
// вызывает функцию quit с параметром при уничтожении активности.

class CustomHandler {
    lateinit var handler: Handler
    //для взаимодействия с главным потоком, который будет давать возможность нам, отправлять задачи
    // в наш главный поток, с помощью Looper.getMainLooper() мы можем обратиться к главному
    // lopere галвного потока.
    val mainHandler = Handler(Looper.getMainLooper())

    fun initHandler() {
        // для удобной работы с потоками
        val backgroundThread = HandlerThread("handler thread").apply {
            //при запуски этоко класса автоматически создадуться лупер и хендлер
            start()
        }
        handler = Handler(backgroundThread.looper)
    }

    // функция завершения потока false - без завершения задачи, true с завершением задачи
    // вызывается при разашении активити onDestroy() .
    fun quit(complete: Boolean) {
        if (!complete) handler.looper.quit()
        else handler.looper.quitSafely()
    }


    // пример использования потока? эту функцию не использовать !
    private fun handlerPrimer() {
        handler.post {
            Log.d("Handler", "Execute task from thread = ${Thread.currentThread().name}")
            val randomNumber = Random.nextLong()
            // передача переменной в главный поток
            mainHandler.post {
                Log.d("Handler", "Execute view task thread = ${Thread.currentThread().name}")
                // textView.text = randomNumber.toString()
            }
        }

        // пример выполнения с задержкой
        mainHandler.postDelayed({
            //какой то код
            //      Toast.makeText(this, "Generated nimber = $randomNumber", Toast.LENGTH_LONG).show()
        }, 500)
    }

    private fun potok() {
        Thread {
            // инициализация Looper
            Looper.prepare()
            handler = Handler()


            //для работы лупера, внутри этого метода запускается бесконечный цикл по обработки
            // сообщений. метод выполняется до тех пор, пока мы не скажем, что необходимо остановиться
            //для выполнения задча используются Handler
            Looper.loop()
            // получение лупера
            // Looper.myLooper()
            // получение MessageQueue()
            Log.d("Handler", "End thread = ${Thread.currentThread().name}")

        }.start()
    }
}
