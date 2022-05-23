package test.quwi.com.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import test.quwi.com.databinding.ChatFragmentBinding

class ChatFragment : Fragment() {

    private lateinit var binding: ChatFragmentBinding
    private val chatViewModel: ChatViewModel by viewModel()
    private val chatAdapter = ChatAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChatFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
    }

    @DelicateCoroutinesApi
    private fun init() {
        binding.chatRv.layoutManager = LinearLayoutManager(requireContext())
        binding.chatRv.adapter = chatAdapter
        chatViewModel.getChannels()
    }

    private fun observe() {
        chatViewModel.chatCardInfoListLiveData.observe(viewLifecycleOwner, { list ->
            chatAdapter.addItems(list)
        })
    }
}