package com.stream.reactions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import com.stream.reactions.databinding.ActivityChannelMessagesBinding
import com.stream.reactions.utils.*
import com.stream.reactions.utils.brilliantDrawable
import io.getstream.chat.android.ui.ChatUI
import io.getstream.chat.android.ui.SupportedReactions
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory

class ChannelMessagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChannelMessagesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChannelMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val channelId = checkNotNull(intent.getStringExtra("channelId")) {
            "Specifying a channel id is required when starting ChannelActivity"
        }

        Log.d("channelId",channelId)

        val factory = MessageListViewModelFactory(channelId)
        val messageListViewModel: MessageListViewModel by viewModels() { factory }
        messageListViewModel.bindView(binding.messageListView, this)

        val reactions: Map<String, SupportedReactions.ReactionDrawable> = mapOf(
            "like" to likeDrawable(applicationContext),
            "clap" to clapDrawable(applicationContext),
            "wondering" to wonderingDrawable(applicationContext),
            "brilliant" to brilliantDrawable(applicationContext),
            "handshake" to handShakeDrawable(applicationContext),
        )

        ChatUI.supportedReactions = SupportedReactions(applicationContext, reactions)

    }
}