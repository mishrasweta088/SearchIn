package com.cg.project.searchin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


//import com.project.searchin.R

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    //firebase auth
    lateinit var firebaseAuth: FirebaseAuth

    //recycler view
    var recyclerView: RecyclerView? = null

    //user adaptor nd list
    var adapterUsers : AdapterUsers? =null
    var usersList: List<ModelUsers>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)

        //init recycler view
        recyclerView = view.findViewById(R.id.user_recyclerView)

        //set it's properties
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.setLayoutManager(LinearLayoutManager(activity))

        //init user list
        usersList= ArrayList<ModelUsers>()

        //get all user
        getAllUsers()

        //init
        firebaseAuth = FirebaseAuth.getInstance()
        return view
    }

    private fun getAllUsers(){
        //get current user
        var fUsers : FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()
        // get path of data base named "UserProfile" containing users info
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("UserProfile")

        //get all data from path
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                usersList.clear()
                for (ds in p0.getChildren()){
                    val modelUsers : ModelUsers? = ds.getValue(ModelUsers::class.java)

                    //get all user except currently signed in user
                    if(!modelUsers.getUid().equals(fUsers!!.getUid())){
                        usersList.add(modelUsers)
                    }
                    //adaptor
                    var adaptorUsers = usersList?.let { AdapterUsers(getActivity(), it) }

                    //set adaptor to recycler view
                    recyclerView!!.adapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun searchUser(query: String) {
        //get current user
        var fUsers : FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()
        // get path of data base named "UserProfile" containing users info
        val ref : DatabaseReference = FirebaseDatabase.getInstance().getReference("UserProfile")

        //get all data from path
        ref.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                usersList.clear()
                for (ds in p0.getChildren()){
                    val modelUsers : ModelUsers? = ds.getValue(ModelUsers::class.java)

                    //get all user except currently signed in user
                    if(!modelUsers.getUid().equals(fUsers!!.getUid())){
                        usersList.add(modelUsers)
                    }
                    //adaptor
                    var adaptorUsers = usersList?.let { AdapterUsers(getActivity(), it) }

                    //set adaptor to recycler view
                    recyclerView!!.adapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun checkUserStatus(){
        //get current user
        var user: FirebaseUser? = firebaseAuth.getCurrentUser()
        if(user != null){
            // user is signed in stay here
            //set email of logged user
            // mProfileTv.setText(user.getEmail())
        }else{
            //user not signed in, go to main activity
            val intent = Intent(getActivity(), MainActivity::class.java)
            getActivity()!!.finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true) // to show menu option in fragments
        super.onCreate(savedInstanceState)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //inflate menu
        inflater.inflate(R.menu.menu_main, menu)

        //search view
        val item: MenuItem = menu.findItem(R.id.action_search)
        val searchView : SearchView = MenuItemCompat.getActionView(item) as SearchView

        //search listener
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //called when user press search button from keyboard
                //if search query is not empty then  search
                if (!TextUtils.isEmpty(query!!.trim())){
                    //search text contains text, search it
                    searchUser(query)
                }else{
                    //search text empty, get all user
                    getAllUsers()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //called when user press search button from keyboard
                return false
            }

        })

        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // get option id
        var id = item.getItemId()
        if(id == R.id.action_logout){
            firebaseAuth.signOut()
            checkUserStatus()
        }

//        if(id==R.id.action_add_post){
//            var intent: Intent = Intent(this@Home,AddPostActivity::class.java)
//            startActivity(intent)
//
//        }

        return super.onOptionsItemSelected(item)
    }


}
