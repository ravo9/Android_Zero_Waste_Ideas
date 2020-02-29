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
import kotlinx.android.synthetic.main.horizontal_row_view.view.*
import kotlinx.android.synthetic.main.two_items_row.view.left_item
import kotlinx.android.synthetic.main.two_items_row.view.right_item
import kotlinx.android.synthetic.main.two_items_with_protip_row.view.*

class GeneralListAdapter (private val clickListener: (String) -> Unit) : RecyclerView.Adapter<GeneralListAdapter.ViewHolder>() {

    private var itemsList: List<List<ItemEntity>> = ArrayList()
    private var context: Context? = null

    fun updateItems(items: List<ItemEntity>) {
        this.itemsList = convertSingleItemsListIntoClustersList(items)
        notifyDataSetChanged()
    }

    fun setItems(items: List<ItemEntity>) {
        this.itemsList = convertSingleItemsListIntoClustersList(items)
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
        var viewHolder: ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        val twoItemsRowView = inflater
            .inflate(R.layout.two_items_row, parent, false)
        viewHolder = TwoItemsRowViewHolder(twoItemsRowView)

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val twoItemsRowViewHolder = holder as TwoItemsRowViewHolder
        configureTwoItemsRowView(twoItemsRowViewHolder, position)
    }

    private fun configureTwoItemsRowView(holder: TwoItemsRowViewHolder, position: Int) {

        // Set two items views - left and right
        configureLeftAndRightItemsViews(holder, position)
    }

    private fun configureLeftAndRightItemsViews(holder: TwoItemsRowViewHolder, position: Int) {

        var view: View? = null
        var item: ItemEntity? = null

        for (i in 0..1) {

            // Clear variables
            view = null
            item = null

            try {

                // Select view
                view = holder.views[i]

                // Ensure the view is visible (it could be hidden before)
                view.visibility = View.VISIBLE

                // Select an item
                item = itemsList[position][i]

                // Prepare fetched data
                val name = item.name
                var imageLink = item.imageLink

                // Set data within the holder
                view.name.text = name

                // Set onClickListener
                val itemId = item?.id
                view.setOnClickListener{
                    clickListener(itemId)
                }

                // Load thumbnail
                if (!imageLink.isNullOrEmpty()) {
                    Picasso.get().load(imageLink).into(view.thumbnail)
                    view.thumbnail_placeholder_text.visibility = View.INVISIBLE
                } else {
                    view.thumbnail.setImageDrawable(null)
                    view.thumbnail_placeholder_text.visibility = View.VISIBLE
                }

            } catch(e: Exception) {
                Log.e("Exception", e.message);
            }

            // Hide second (right-hand side) view, if there is no item to be displayed there
            if (i == 1 && item == null) {
                view?.visibility = View.INVISIBLE
            }
        }
    }

    abstract class ViewHolder (view: View) : RecyclerView.ViewHolder(view)

    open inner class TwoItemsRowViewHolder (view: View) : ViewHolder(view) {
        val views = ArrayList<View>()

        init {
            views.add(view.left_item)
            views.add(view.right_item)
        }
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