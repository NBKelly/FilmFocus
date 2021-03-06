package amichealpalmer.kotlin.filmfocus.adapters


import amichealpalmer.kotlin.filmfocus.data.FilmThumbnail
import amichealpalmer.kotlin.filmfocus.R
//import amichealpalmer.kotlin.filmfocus.activities.BrowseActivity
//import amichealpalmer.kotlin.filmfocus.activities.WatchlistActivity
import amichealpalmer.kotlin.filmfocus.fragments.FilmDetailsFragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
//import android.support.v7.widget.CardView
//import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class BrowseRecyclerAdapter(
        private val context: Context,
        private var resultList: ArrayList<FilmThumbnail> // The list of films currently being displayed in the browser
) : RecyclerView.Adapter<BrowseRecyclerAdapter.HelperViewHolder>() {

    private val TAG = "BrowseRecyclerAdapter"
    var position = 0
    var helperViewHolder: BrowseRecyclerAdapter.HelperViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelperViewHolder {
        Log.d(TAG, ".onCreateViewHolder called")
        val view: View
        val mInflater = LayoutInflater.from(context)
        view = mInflater.inflate(R.layout.browse_films_item, parent, false)

        helperViewHolder = HelperViewHolder(view)
        return helperViewHolder!!
    }

    fun getAdapterPosition(): Int{
        return helperViewHolder?.adapterPosition ?: 0
    }

    fun updateList(resultList: List<FilmThumbnail>) {
        this.resultList.addAll(resultList)
        notifyDataSetChanged()
    }

    fun clearList(){
        resultList.clear()
    }

    override fun onBindViewHolder(holder: HelperViewHolder, position: Int) {
        //Log.d(TAG, ".onBindViewHolder called. Title of film is: ${resultList[position].title}")
        if (resultList.size > 0) {
            Picasso.get().load(resultList[position].posterURL).error(R.drawable.placeholder_imageloading)
                    .placeholder(R.drawable.placeholder_imageloading).into(holder.poster)

            holder.itemView.setOnLongClickListener {
                this.position = (holder.adapterPosition)
                false
            }
        }
    }

    override fun onViewRecycled(holder: HelperViewHolder) {
        // may not be required
        holder.itemView.setOnLongClickListener(null)
        super.onViewRecycled(holder)

    }

    fun getItem(position: Int): FilmThumbnail {
        return resultList[position]
    }


    override fun getItemCount(): Int {
        return resultList.size
    }

    inner class HelperViewHolder(view: View)
        : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
        val poster: ImageView = view.findViewById(R.id.film_poster_id)
        val cardView: CardView = view.findViewById(R.id.film_item_cardview_id)

        init {
            cardView.setOnClickListener {

                // Using fragment
                val fragment = FilmDetailsFragment()
                val bundle = Bundle()
                bundle.putString("imdbID", resultList[adapterPosition].imdbID)
                fragment.arguments = bundle
                val manager = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                manager.setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                manager.addToBackStack(null)
                manager.replace(R.id.main_frame_layout_fragment_holder, fragment).commit()
            }

            cardView.setOnCreateContextMenuListener(this)

        }

        override fun onCreateContextMenu(menu: ContextMenu?, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
            Log.d(TAG, ".onCreateContextMenu called.")
            val inflater = MenuInflater(context)
            inflater.inflate(R.menu.browse_film_context_menu, menu)

        }

    }

}