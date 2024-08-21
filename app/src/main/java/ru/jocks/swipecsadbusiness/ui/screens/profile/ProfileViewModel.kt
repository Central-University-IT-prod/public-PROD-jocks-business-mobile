package ru.jocks.swipecsadbusiness.ui.screens.profile

import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.jocks.domain.business.model.BusinessProfileState
import ru.jocks.domain.business.repository.BusinessRepository
import ru.jocks.swipecsadbusiness.Application
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.util.*


sealed interface FileDataState {
    data object None : FileDataState
    data object Loading : FileDataState
    data class Success(val fileData: Byte) : FileDataState
    data class Error(val message: String) : FileDataState
}

class ProfileViewModel(private val businessRepository: BusinessRepository, private val application : android.app.Application) : AndroidViewModel(application) {
    var businessDetails: BusinessProfileState by mutableStateOf(BusinessProfileState.None)
        private set

    init {
        getBusinessInfo()
    }

    fun logout() = viewModelScope.launch {
        businessRepository.logoutBusiness()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun download() = CoroutineScope(Dispatchers.IO).launch {
        val inputStream = businessRepository.exportData()

        val name = UUID.randomUUID().toString() + ".csv"

        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, name)
            put(MediaStore.Downloads.MIME_TYPE, "text/csv")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val uri = application.contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)

        viewModelScope.launch {
            Toast.makeText(application, uri.toString(), Toast.LENGTH_LONG).show()
        }

        if (uri != null) {
            val fileOutputStream = application.contentResolver.openOutputStream(uri)!!
            inputStream.use { fileOut -> fileOut.copyTo(fileOutputStream) }
            fileOutputStream.close()
        } else {
            viewModelScope.launch {
                Toast.makeText(application, "Can\'t resolve media path", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun getBusinessInfo() = viewModelScope.launch {
        businessDetails = BusinessProfileState.Loading
        businessDetails = businessRepository.getBusinessInfo()
    }
}