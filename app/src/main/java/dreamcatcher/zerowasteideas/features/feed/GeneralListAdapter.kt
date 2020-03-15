package dreamcatcher.zerowasteideas.features.feed

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dreamcatcher.zerowasteideas.R
import dreamcatcher.zerowasteideas.data.database.items.ItemEntity
import kotlinx.android.synthetic.main.feed_single_item.view.*


class GeneralListAdapter (private val clickListener: (String) -> Unit) : RecyclerView.Adapter<GeneralListAdapter.ViewHolder>() {

    private var itemsList: List<ItemEntity> = ArrayList()
    private var context: Context? = null

    fun updateItems(items: List<ItemEntity>) {
        this.itemsList = items
        notifyDataSetChanged()
    }

    fun setItems(items: List<ItemEntity>) {
        this.itemsList = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(parent.context)
        val singleItemView = inflater.inflate(R.layout.feed_single_item, parent, false)
        return OwnViewHolder(singleItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolder = holder as OwnViewHolder
        configureItemView(viewHolder, position)
    }

    private fun configureItemView(holder: OwnViewHolder, position: Int) {

        val item = itemsList[position]

        try {

            // Prepare fetched data
            val title =  item.title
            val description =  item.description
            var tags =  item.tags?.joinToString(" #")
            if (!tags.isNullOrEmpty()) tags = "#" + tags
            val authorName =  item.authorName
            val imageLink = item.imageLink
            val currentGrades = item.currentGrade
            val gradesAmount = item.amountOfGrades

            // Set data within the holder
            holder.title.text = title
            holder.description.text = description
            tags?.let{ holder.tags.text = it }
            holder.author.text = authorName
            holder.currentGrade.text = currentGrades.toString()
            holder.gradesAmunt.text = gradesAmount.toString()

            // Set onClickListener
            val itemId = item.id
            holder.container.setOnClickListener{
                //clickListener(itemId)
            }

            // Load thumbnail
            if (!imageLink.isNullOrEmpty()) {
                holder.thumbnailContainer.visibility = View.VISIBLE
                Picasso.get().load(imageLink).into(holder.thumbnail)
            } else {
                holder.thumbnail.visibility = View.GONE
            }

        } catch(e: Exception) {
            e.message?.let{
                Log.e("Exception", it)
            }
        }
    }

    abstract class ViewHolder (view: View) : RecyclerView.ViewHolder(view)

    open inner class OwnViewHolder (view: View) : ViewHolder(view) {
        val title = view.title
        val description = view.description
        val tags = view.tags
        val author = view.author
        val currentGrade = view.current_grade
        val gradesAmunt = view.grades_amount
        val thumbnail = view.thumbnail
        val thumbnailContainer = view.thumbnail_container
        val container = view.single_item_container
    }
}