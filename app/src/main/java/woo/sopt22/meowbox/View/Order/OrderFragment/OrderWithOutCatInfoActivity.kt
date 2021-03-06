package woo.sopt22.meowbox.View.Order.OrderFragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_order_first.*
import kotlinx.android.synthetic.main.app_bar_order_first.*
import woo.sopt22.meowbox.R
import woo.sopt22.meowbox.View.Home.MainActivity
import woo.sopt22.meowbox.View.MeowBoxStory.MeowBoxStoryActivity
import woo.sopt22.meowbox.View.MyPage.MyPageActivity
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Menu
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.content_order_first.*
import woo.sopt22.meowbox.Util.CustomDialog.CatCustomDialog
import woo.sopt22.meowbox.Util.SharedPreference
import woo.sopt22.meowbox.Util.ToastMaker
import woo.sopt22.meowbox.View.Login.LoginActivity
import woo.sopt22.meowbox.View.MeowBoxReview.MeowBoxReviewActivity
import woo.sopt22.meowbox.View.Order.LoginCustomDialog


class OrderWithOutCatInfoActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    override fun onClick(v: View?) {
        when(v!!){
            order_next_btn->{
                //startActivity(Intent(this, OrderSecondActivity::class.java))
                val dialog = LoginCustomDialog(this)
                dialog.show()
                dialog.dismiss()
            }
        }
    }


    companion object {
        var mContext: Context?=null
    }

    fun init(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            window.statusBarColor = Color.BLACK
            window.navigationBarColor = Color.BLACK
        }

        SharedPreference.instance!!.load(this)

        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)

    }
    lateinit var order_next_btn : RelativeLayout
    lateinit var container : FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_first)
        setSupportActionBar(toolbar)
        mContext = this

        init()
        replaceFragment(OrderFirstFragment())


        var headerView : View = order_first_nav_view.getHeaderView(0)
        var userName : TextView = headerView.findViewById<TextView>(R.id.header_name)
        var userImage : ImageView = headerView.findViewById(R.id.imageView)

        if(SharedPreference.instance!!.getPrefStringData("image_profile") == null){
            Log.v("용범 onResume","123")
            Glide.with(this).load(R.drawable.side_bar_profile_img).into(userImage)
        } else{
            Log.v("용범 onResume","456")
            userImage.setImageURI(Uri.parse(SharedPreference.instance!!.getPrefStringData("image_profile")))
            Glide.with(this).load(SharedPreference.instance!!.getPrefStringData("image_profile")!!).into(userImage)
        }


        container = order_framge as FrameLayout

        // 네비게이션 버튼 비활성화
        var menu : Menu = order_first_nav_view.menu
        var menu_item : MenuItem = menu.findItem(R.id.loginBtn)
        var blank_menu_item : MenuItem = menu.findItem(R.id.blankBtn)
        var blank_menu_item2 : MenuItem = menu.findItem(R.id.blankBtn2)
        var order_btn : MenuItem = menu.findItem(R.id.orderBtn)
        order_btn.setEnabled(false)
        blank_menu_item.setEnabled(false)
        blank_menu_item2.setEnabled(false)

        if(SharedPreference.instance!!.getPrefStringData("name")!!.isEmpty()){
            userName.text = "OO님!"
            menu_item.setTitle("로그인")
        } else {
            userName.text = SharedPreference.instance!!.getPrefStringData("name") + " 님"
            menu_item.setTitle("")
            menu_item.setEnabled(false)
        }


        val toggle = ActionBarDrawerToggle(
                this, order_first_drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        toggle.setDrawerIndicatorEnabled(false)
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.side_bar_btn_black, applicationContext!!.getTheme())

        val bitmap = (drawable as BitmapDrawable).bitmap
        val newdrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 30, 30, true))

        toggle.setHomeAsUpIndicator(drawable)
        toggle.setToolbarNavigationClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (order_first_drawer_layout.isDrawerVisible(GravityCompat.START)) {
                    order_first_drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    order_first_drawer_layout.openDrawer(GravityCompat.START)
                }
            }
        })

        order_first_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        order_first_nav_view.setNavigationItemSelectedListener(this)
    }


    fun replaceFragment(fragment : Fragment){
        val fm = supportFragmentManager
        val transaction = fm.beginTransaction()
        transaction.replace(R.id.order_framge, fragment)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (order_first_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            order_first_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.loginBtn -> {
                if(SharedPreference.instance!!.getPrefStringData("token")!!.isEmpty()){
                    startActivity(Intent(this, LoginActivity::class.java))
                } else{
                    ToastMaker.makeLongToast(this, "마이페이지에서 로그아웃 해주세요.")
                }
            }
            R.id.blankBtn->{
                item.isChecked = false
            }
            R.id.homeBtn -> {
                startActivity(Intent(mContext, MainActivity::class.java))
            }
            R.id.stroyBtn -> {
                startActivity(Intent(this, MeowBoxStoryActivity::class.java))
            }
            R.id.orderBtn -> {
                if(SharedPreference.instance!!.getPrefStringData("token")!!.isEmpty()){
                    val dialog = LoginCustomDialog(this)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                } else{
                    if(SharedPreference.instance!!.getPrefStringData("cat_idx")!!.toInt() == -1){
                        val dialog = CatCustomDialog(this)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()
                    } else {
                        val intent = Intent(this, OrderWithOutCatInfoActivity::class.java)
                        intent.putExtra("cat_idx", SharedPreference.instance!!.getPrefStringData("cat_idx")!!)
                        startActivity(intent)
                    }
                }

            }
            R.id.reviewBtn -> {
                startActivity(Intent(this, MeowBoxReviewActivity::class.java))
            }
            R.id.myPageBtn->{
                if(SharedPreference.instance!!.getPrefStringData("token")!!.isEmpty()){
                    ToastMaker.makeLongToast(this,"로그인 해주세요.")
                } else{
                    startActivity(Intent(this, MyPageActivity::class.java))
                }
            }
        }

        order_first_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
