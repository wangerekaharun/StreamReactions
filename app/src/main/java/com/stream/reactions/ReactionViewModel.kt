package com.stream.reactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReactionViewModel: ViewModel() {
    private val _messageId = MutableLiveData<String>()
    val messageId: LiveData<String> get() = _messageId


    fun setMessageId(messageId: String) {
        _messageId.value = messageId
    }
}