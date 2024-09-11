package com.example.coroutinesapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var resultState by mutableStateOf("")
        private set

    var countTime by mutableStateOf(0)
        private set

    var countTimedos by mutableStateOf(0)
        private set

    private var oneCountdos by mutableStateOf(false)
    private var oneCount by mutableStateOf(false)

    private var fetchJob: Job? = null // Almacena el Job para cancelar las corrutinas.

    fun fetchData() {
        // Si ya hay un Job en curso, lo cancelamos antes de iniciar uno nuevo.
        fetchJob?.cancel()

        // Inicia una nueva corrutina para el primer conteo
        fetchJob = viewModelScope.launch {
            // Primer conteo
            for (i in 1..5) {
                delay(1000)  // Pausa por 1 segundo
                countTime = i  // Actualiza el primer contador
            }
            // Una vez completado el primer conteo, actualiza `oneCount`
            oneCount = true

            // Segundo conteo (comienza después del primer conteo)
            for (i in 1..5) {
                delay(1000)  // Pausa por 1 segundo
                countTimedos = i  // Actualiza el segundo contador
            }
            // Una vez completado el segundo conteo, actualiza `oneCountdos`
            oneCountdos = true

            // Actualiza el estado final después de ambos conteos
            resultState = "Respuesta que se obtuvo del servidor Web"
        }
    }

    fun cancelCounters() {
        // Cancela el Job que está ejecutando las corrutinas
        fetchJob?.cancel()

        viewModelScope.launch {
            // Espera 2 segundos antes de reiniciar
            delay(2000)
            // Reinicia los contadores y el estado
            countTime = 0
            countTimedos = 0
            resultState = "Contadores restablecidos"
            oneCount = false
            oneCountdos = false
        }
    }

    fun resetCount() {
        countTime = 1  // Reiniciar el contador a 0
        resultState = "" // Restablecer el estado del resultado
    }
}



    /*
      Thread trabaja en el mismo contexto de la IU

    fun bloqueoApp(){
        Thread.sleep(5000)
        resultState = "Respuesta del Servidor Web"

    }
  */

    /*
    fun fetchData(){
        viewModelScope.launch {
            delay(5000)
            resultState = "Respuesta desde el Servidor Web"
        }
    }*/





