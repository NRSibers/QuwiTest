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
import java.text.SimpleDateFormat
import java.util.*

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
                                        isSavedChannel = true,
                                        pinToTop = channel.pinToTop,
                                        dateText = reformatDate(channel.messageLast?.dtaCreate ?: ""),
                                        readIndicator = if (channel.messageLast?.idUser == sharedPreferences.userId) {
                                            ReadIndicatorEnum.getByValue(channel.messageLast.isRead)
                                        } else {
                                            ReadIndicatorEnum.NON
                                        }
                                    )
                                else {
                                    when(val partnerResult = chatRepository.getUsers(listOf(channel.idPartner))) {
                                        is RequestResult.Success -> {
                                            ChatCardInfo(
                                                ChannelTypeEnum.getByType(channel.type),
                                                channel.messageLast?.user?.name ?: "",
                                                channel.messageLast?.text ?: "",
                                                partnerResult.data.first().avatarUrl,
                                                (channel.messageLast?.idUser ?: Long.MIN_VALUE) == sharedPreferences.userId,
                                                pinToTop = channel.pinToTop,
                                                dateText = reformatDate(channel.messageLast?.dtaCreate ?: ""),
                                                readIndicator = if (channel.messageLast?.idUser == sharedPreferences.userId) {
                                                    ReadIndicatorEnum.getByValue(channel.messageLast.isRead)
                                                } else {
                                                    ReadIndicatorEnum.NON
                                                }
                                            )
                                        }
                                        is RequestResult.Error -> {
                                            ChatCardInfo(
                                                ChannelTypeEnum.getByType(channel.type),
                                                channel.messageLast?.user?.name ?: "",
                                                channel.messageLast?.text ?: "",
                                                null,
                                                (channel.messageLast?.idUser ?: Long.MIN_VALUE) == sharedPreferences.userId,
                                                pinToTop = channel.pinToTop,
                                                dateText = reformatDate(channel.messageLast?.dtaCreate ?: ""),
                                                readIndicator = if (channel.messageLast?.idUser == sharedPreferences.userId) {
                                                    ReadIndicatorEnum.getByValue(channel.messageLast.isRead)
                                                } else {
                                                    ReadIndicatorEnum.NON
                                                }
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

    private fun reformatDate(dateString: String): String {
        if (dateString.isEmpty()) return ""
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = dateFormat.parse(dateString) ?: Date()
        val today = Date()
        val dayTime = 24 * 60 * 60 * 1000L

        val newFormat = if (today.time - date.time < dayTime) {
            SimpleDateFormat("HH:mm", Locale.getDefault())
        } else {
            SimpleDateFormat("dd MMM", Locale.getDefault())
        }
        return newFormat.format(date)
    }
}