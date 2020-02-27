package amichealpalmer.kotlin.filmfocus.activities

//import android.R

import amichealpalmer.kotlin.filmfocus.R
import amichealpalmer.kotlin.filmfocus.data.Film
import amichealpalmer.kotlin.filmfocus.data.FilmThumbnail
import amichealpalmer.kotlin.filmfocus.fragments.BrowseFragment
import amichealpalmer.kotlin.filmfocus.fragments.WatchlistFragment
import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.view.*


// todo: see trello

class MainActivity : BaseActivity() {


    val TAG = "MainActivity"
    val testFilm = Film("", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
    //private val search = Search(this) //

    private lateinit var mDrawer: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(drawer_layout.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        mDrawer = findViewById(R.id.drawer_layout)
        val nvDrawer = findViewById<NavigationView>(R.id.nvView)
        setupDrawerContent(nvDrawer)

        Log.d(TAG, "Set content view done")

        fab.setOnClickListener { view ->
            // todo action button
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // test watchlist create
        //Log.d(TAG, ".onCreate: testing load of watchlist")
        //createTestWatchlist()

        Log.d(TAG, ".onCreate finished")
    }


    private fun setupDrawerContent(navigationView: NavigationView) {
        Log.d(TAG, ".setupDrawerContent: setting nav view itemselectedlistener")
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    fun selectDrawerItem(menuItem: MenuItem) { // Create a new fragment and specify the fragment to show based on nav item clicked
        var fragment: Fragment? = null
        val fragmentClass: Class<*>
        fragmentClass = when (menuItem.itemId) {
            R.id.nav_first_fragment -> BrowseFragment::class.java
            R.id.nav_second_fragment -> WatchlistFragment::class.java
            // R.id.nav_third_fragment -> ThirdFragment::class.java
            else -> BrowseFragment::class.java
        }
        try {
            fragment = fragmentClass.newInstance() as Fragment // not gonna work?
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // Insert the fragment by replacing any existing fragment
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.main_frame_layout_fragment_holder, fragment!!).commit()
        // Highlight the selected item has been done by NavigationView
        menuItem.isChecked = true
        // Set action bar title
        title = menuItem.title
        // Close the navigation drawer
        mDrawer.closeDrawers()
    }


    // todo: convert watchlist to fragment then test watchlist w/ this function
    fun createTestWatchlist() {
        var resultList = ArrayList<FilmThumbnail>()


        // Add some 'results' to the list
        resultList.add(FilmThumbnail("Blade Runner", "", "tt0083658", "", "https://upload.wikimedia.org/wikipedia/en/thumb/9/9f/Blade_Runner_(1982_poster).png/220px-Blade_Runner_(1982_poster).png"))
        resultList.add(FilmThumbnail("Predator", "", "tt0093773", "", "https://upload.wikimedia.org/wikipedia/en/9/95/Predator_Movie.jpg"))
        resultList.add(FilmThumbnail("The Thing", "", "tt0084787", "", "https://upload.wikimedia.org/wikipedia/en/a/a6/The_Thing_(1982)_theatrical_poster.jpg"))
        resultList.add(FilmThumbnail("The Fly", "", "tt0091064", "", "https://upload.wikimedia.org/wikipedia/en/a/aa/Fly_poster.jpg"))

        // Pass the 'results' to the watchlist activity
        intent = Intent(this, WatchlistActivity::class.java)
        intent.putParcelableArrayListExtra("thumbs", resultList)
        startActivity(intent)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.d(TAG, ".onCreateOptionsMenu called")
        menuInflater.inflate(R.menu.options_menu, menu)

        // Associating searchable configuration with the SearchView
        val componentName = ComponentName(this, SearchActivity::class.java)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager

        // Configuring the SearchView
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.isIconifiedByDefault = false
        searchView.requestFocus()
//        (menu.findItem(R.id.search).actionView as SearchView).apply {
//            setSearchableInfo(searchManager.getSearchableInfo(componentName))
//
//            requestFocus()
//        }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Log.d(TAG, ".onOptionsItemSelected triggered")
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            //R.id.action_settings -> true
            android.R.id.home -> {
                Log.d(TAG, ".onOptionsItemSelected: drawer button tapped")
                mDrawer.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

//
//    fun inflateFilmInformation(film: Film){
//        Log.d(TAG, ".inflateFilmInformation starts")
//        //setContentView(R.layout.activity_film_details)
//        //FilmDetailsActivity(film)
//        val intent = Intent(this, FilmDetailsActivity::class.java)
//        intent.putExtra(FILM_DETAILS_TRANSFER, film)
//        startActivity(intent)
//        Log.d(TAG, ".inflateFilmInformation called")
//    }

