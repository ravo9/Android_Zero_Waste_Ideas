package dreamcatcher.zerowasteideas.features.detailedView

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import dreamcatcher.zerowasteideas.R
import dreamcatcher.zerowasteideas.data.database.items.ItemEntity
import dreamcatcher.zerowasteideas.features.basic.BasicFragment
import kotlinx.android.synthetic.main.new_item_view.*

// View for adding a new item
class NewIdeaViewFragment : BasicFragment() {

    private lateinit var viewModel: NewIdeaViewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Initialize ViewModel
        viewModel = ViewModelProviders.of(this).get(NewIdeaViewViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.new_item_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Fetch detailed data from Data Repository
        subscribeForItem()

        // Setup Cross Button
        val closingOnClickListener = View.OnClickListener{
            closeFragment()
        }
        btn_cross.setOnClickListener(closingOnClickListener)

        // Setup closing on the grey fields' click
        spacing_top.setOnClickListener(closingOnClickListener)
        spacing_bottom.setOnClickListener(closingOnClickListener)

        // Setup keyboard closing on white space click
        main_content.setOnClickListener {
            hideKeyboard()
        }

        add_idea_button.setOnClickListener{

            // Analytics event logging
            val context = context
            if (context != null) {

                if (isAllNecessaryInformationProvided()) {

                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title_input.text.toString())
                    bundle.putString(FirebaseAnalytics.Param.CONTENT, description_input.text.toString())
                    bundle.putString(FirebaseAnalytics.Param.ORIGIN, email_input.text.toString())
                    bundle.putString(FirebaseAnalytics.Param.SOURCE, author_input.text.toString())
                    FirebaseAnalytics.getInstance(context).logEvent(getString(R.string.analytics_event_new_idea), bundle)

                    // TODO Network problems handling.

                    displayConfirmationDialogbox(it)
                } else {
                    displayErrorDialogbox(it)
                }
            }
        }
    }

    private fun closeFragment() {
        hideKeyboard()
        activity?.onBackPressed()
    }

    private fun displayConfirmationDialogbox(view: View) {

        // TODO Hard-coded string
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.than_you_for_new_idea))
            .setMessage(getString(R.string.it_will_be_published_soon))
            .setPositiveButton(getString(R.string.ok)) { dialog: DialogInterface, id: Int ->
                closeFragment()
            }
            .create()
            .show()
    }

    private fun displayErrorDialogbox(view: View) {

        // TODO Hard-coded string
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.oops_somethings_missing))
            .setMessage(getString(R.string.it_seems_that_some_necessary))
            .setPositiveButton(getString(R.string.ok)) { dialog: DialogInterface, id: Int ->
                //
            }
            .create()
            .show()
    }

    private fun isAllNecessaryInformationProvided(): Boolean {
        return !(title_input.text.isEmpty() ||
                description_input.text.isEmpty() ||
                email_input.text.isEmpty() ||
                author_input.text.isEmpty())
    }

    private fun hideKeyboard() {
        val view = activity?.currentFocus
        view?.let { view ->
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.let { it.hideSoftInputFromWindow(view.windowToken, 0) }
        }
    }

    private fun subscribeForItem() {
        val itemId = this.arguments?.getString("itemId")
        if (!itemId.isNullOrEmpty()) {
            /*viewModel.getSingleSavedItemById(itemId)?.observe(this, Observer<ItemEntity> {
                setupDetailedView(it)
            })*/
        }
    }

    private fun showLoadingView(loadingState: Boolean) {
        if (loadingState) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}