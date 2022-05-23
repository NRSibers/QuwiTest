package test.quwi.com.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import okhttp3.internal.notifyAll
import test.quwi.com.R

class ChatAdapter : RecyclerView.Adapter<ChatHolder>()  {

    private val list = mutableListOf<ChatCardInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.chat_card_layout, parent, false
        )
        return ChatHolder(view)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun addItems(items: List<ChatCardInfo>) {
        list.addAll(items)
        notifyItemRangeInserted(list.size, items.size)
    }
}

class ChatHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val avatarView = view.findViewById<AppCompatImageView>(R.id.avatar_iv)
    private val chatNameView = view.findViewById<TextView>(R.id.chat_name)
    private val messageView = view.findViewById<TextView>(R.id.message)

    fun bind(info: ChatCardInfo) {
        chatNameView.text = info.name
        messageView.text = info.text
        Picasso.get().load(info.avatarUrl).transform(CropCircleTransformation()).into(avatarView)
    }

}