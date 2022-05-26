package test.quwi.com.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import test.quwi.com.base.ISharedPreferences
import test.quwi.com.base.RequestResult
import test.quwi.com.chat.repository.IChatRepository
import test.quwi.com.chat.response.Channel

class ChatViewModel(
    private val chatRepository: IChatRepository,
    private val sharedPreferences: ISharedPreferences
) : ViewModel() {

    private val mChannelLiveData = MutableLiveData<List<Channel>>()
    val channelLiveData: LiveData<List<Channel>>
        get() = mChannelLiveData

    private val mChatCardsInfoListLiveData = MutableLiveData<List<ChatCardInfo>>()
    val chatCardInfoListLiveData: LiveData<List<ChatCardInfo>>
        get() = mChatCardsInfoListLiveData

    @DelicateCoroutinesApi
    fun getChannels() {
        GlobalScope.launch {
                when(val result = chatRepository.getChannels()) {
                    is RequestResult.Success -> {
                        result.data.let { list ->

                            val chatCardInfoList = list.map { channel ->
                                if(channel.idPartner == sharedPreferences.userId)
                                    ChatCardInfo(
                                        ChannelTypeEnum.getByType(channel.type),
                                        channel.messageLast?.user?.name ?: "Saved Messages",
                                        channel.messageLast?.text ?: "",
                                        null,
                                        isSavedChannel = true
                                    )
                                else {
                                    when(val partnerResult = chatRepository.getUsers(listOf(channel.idPartner))) {
                                        is RequestResult.Success -> {
                                            ChatCardInfo(
                                                ChannelTypeEnum.getByType(channel.type),
                                                channel.messageLast?.user?.name ?: "",
                                                channel.messageLast?.text ?: "",
                                                partnerResult.data.first().avatarUrl,
                                                (channel.messageLast?.idUser ?: Long.MIN_VALUE) == sharedPreferences.userId
                                            )
                                        }
                                        is RequestResult.Error -> {
                                            ChatCardInfo(
                                                ChannelTypeEnum.getByType(channel.type),
                                                channel.messageLast?.user?.name ?: "",
                                                channel.messageLast?.text ?: "",
                                                null,
                                                (channel.messageLast?.idUser ?: Long.MIN_VALUE) == sharedPreferences.userId
                                            )
                                        }
                                    }
                                }
                            }

                            mChatCardsInfoListLiveData.postValue(chatCardInfoList)
                        }
                    }

                    is RequestResult.Error -> {

                    }
                }

        }
    }
}