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
                                val users = channel.idUsers.filter { it != sharedPreferences.userId }
                                if (users.size == 1) {
                                    val participateId = users.first()
                                    when(val participateResult = chatRepository.getUsers(listOf(participateId))) {
                                        is RequestResult.Success -> {
                                            ChatCardInfo(
                                                ChannelTypeEnum.getByType(channel.type),
                                                channel.messageLast?.user?.name ?: "",
                                                channel.messageLast?.text ?: "",
                                                participateResult.data.first().avatarUrl
                                            )
                                        }
                                        is RequestResult.Error -> {
                                            ChatCardInfo(
                                                ChannelTypeEnum.getByType(channel.type),
                                                channel.messageLast?.user?.name ?: "",
                                                channel.messageLast?.text ?: "",
                                                null
                                            )
                                        }
                                    }

                                } else {
                                    ChatCardInfo(
                                        ChannelTypeEnum.getByType(channel.type),
                                        channel.messageLast?.user?.name ?: "",
                                        channel.messageLast?.text ?: "",
                                        null
                                    )
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