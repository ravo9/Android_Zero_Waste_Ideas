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
import kotlinx.android.synthetic.main.grid_single_item.view.*


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
        val singleItemView = inflater.inflate(R.layout.grid_single_item, parent, false)
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
            var imageLink = item.imageLink

            // Set data within the holder
            holder.title.text = title
            holder.description.text = description

            // Set onClickListener
            val itemId = item.id
            holder.container.setOnClickListener{
                clickListener(itemId)
            }

            // Load thumbnail
            if (!imageLink.isNullOrEmpty()) {
                Picasso.get().load(imageLink).into(holder.thumbnail)
                holder.thumbnail.visibility = View.INVISIBLE
            } else {
                holder.thumbnail.visibility = View.GONE
            }

        } catch(e: Exception) {
            Log.e("Exception", e.message);
        }

    }

    abstract class ViewHolder (view: View) : RecyclerView.ViewHolder(view)

    open inner class OwnViewHolder (view: View) : ViewHolder(view) {
        val title = view.title
        val description = view.description
        val thumbnail = view.thumbnail
        val container = view.single_item_container
    }

    // Converter grouping items together into 2-items clusters
    private fun convertSingleItemsListIntoClustersList(itemsList: List<ItemEntity>)
            : List<List<ItemEntity>> {
        val clustersList = ArrayList<List<ItemEntity>>()
        itemsList.chunked(2).forEach {
            clustersList.add(it)
        }
        return clustersList
    }
}