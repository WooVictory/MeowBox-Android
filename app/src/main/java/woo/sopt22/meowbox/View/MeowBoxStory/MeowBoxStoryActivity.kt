package woo.sopt22.meowbox.View.MeowBoxStory

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_meow_box_story.*
import kotlinx.android.synthetic.main.app_bar_meow_box_story.*
import kotlinx.android.synthetic.main.content_meow_box_story.*
import woo.sopt22.meowbox.R
import woo.sopt22.meowbox.Util.CustomDialog.CatCustomDialog
import woo.sopt22.meowbox.Util.CustomDialog.LoginToMyPageCustomDialog
import woo.sopt22.meowbox.Util.SharedPreference
import woo.sopt22.meowbox.Util.ToastMaker
import woo.sopt22.meowbox.View.Home.MainActivity
import woo.sopt22.meowbox.View.Login.LoginActivity
import woo.sopt22.meowbox.View.MeowBoxBirthDay.MeowBoxtBirthDayStoryActivity
import woo.sopt22.meowbox.View.MeowBoxReview.MeowBoxReviewActivity
import woo.sopt22.meowbox.View.MyPage.MyPageActivity
import woo.sopt22.meowbox.View.Order.LoginCustomDialog
import woo.sopt22.meowbox.View.Order.OrderFragmentWithCatInfo.OrderWithCatInfoActivity

class MeowBoxStoryActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!) {
            story_to_order_btn -> {
                if (SharedPreference.instance!!.getPrefStringData("token")!!.isEmpty()) {
                    val dialog = LoginCustomDialog(this)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                } else {
                    if (SharedPreference.instance!!.getPrefStringData("cat_idx")!! == "-1") {
                        val dialog = CatCustomDialog(this)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()
                    } else {
                        val intent = Intent(this, OrderWithCatInfoActivity::class.java)
                        intent.putExtra("cat_idx", SharedPreference.instance!!.getPrefStringData("cat_idx")!!)
                        startActivity(intent)
                    }
                }
            }
            story_to_order_btn2 -> {
                if (SharedPreference.instance!!.getPrefStringData("token")!!.isEmpty()) {
                    val dialog = LoginCustomDialog(this)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                } else {
                    if (SharedPreference.instance!!.getPrefStringData("cat_idx")!! == "-1") {
                        val dialog = CatCustomDialog(this)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()
                    } else {
                        val intent = Intent(this, OrderWithCatInfoActivity::class.java)
                        intent.putExtra("cat_idx", SharedPreference.instance!!.getPrefStringData("cat_idx")!!)
                        startActivity(intent)
                    }
                }
            }

        }
    }

    fun init() {
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.BLACK
            window.navigationBarColor = Color.BLACK
        }

        story_to_order_btn.setOnClickListener(this)
        story_to_order_btn2.setOnClickListener(this)
        SharedPreference.instance!!.load(this)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meow_box_story)
        setSupportActionBar(toolbar)

        init()


        var headerView: View = story_nav_view.getHeaderView(0)
        var userName: TextView = headerView.findViewById<TextView>(R.id.header_name)
        var userImage: ImageView = headerView.findViewById(R.id.imageView)

        Glide.with(this).load(SharedPreference.instance!!.getPrefStringData("image_profile")!!).into(userImage)

        var menu: Menu = story_nav_view.menu
        var menu_item: MenuItem = menu.findItem(R.id.loginBtn)
        var blank_menu_item: MenuItem = menu.findItem(R.id.blankBtn)
        var blank_menu_item2: MenuItem = menu.findItem(R.id.blankBtn2)
        var story_menu_item: MenuItem = menu.findItem(R.id.stroyBtn)
        story_menu_item.setEnabled(false)
        blank_menu_item.setEnabled(false)
        blank_menu_item2.setEnabled(false)


        if (SharedPreference.instance!!.getPrefStringData("image_profile") == null) {
            userImage.setImageResource(R.drawable.side_bar_profile_img)
        } else {
            Glide.with(this).load(SharedPreference.instance!!.getPrefStringData("image_profile")!!).into(userImage)
        }

        if (SharedPreference.instance!!.getPrefStringData("name")!!.isEmpty()) {
            userName.text = "OO님!"
            menu_item.setTitle("로그인")
        } else {
            userName.text = SharedPreference.instance!!.getPrefStringData("name") + " 님"
            menu_item.setTitle("")
            menu_item.setEnabled(false)
        }


        val toggle = ActionBarDrawerToggle(
                this, story_drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)

        toggle.setDrawerIndicatorEnabled(false)
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.side_bar_btn_black, applicationContext!!.getTheme())

        val bitmap = (drawable as BitmapDrawable).bitmap
        val newdrawable = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 30, 30, true))

        toggle.setHomeAsUpIndicator(drawable)
        toggle.setToolbarNavigationClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (story_drawer_layout.isDrawerVisible(GravityCompat.START)) {
                    story_drawer_layout.closeDrawer(GravityCompat.START)
                } else {
                    story_drawer_layout.openDrawer(GravityCompat.START)
                }
            }
        })

        story_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        story_nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (story_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            story_drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.loginBtn -> {
                if (SharedPreference.instance!!.getPrefStringData("token")!!.isEmpty()) {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    ToastMaker.makeLongToast(this, "마이페이지에서 로그아웃 해주세요.")
                }
            }
            R.id.homeBtn -> {
                finish()
                var intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)

            }
            R.id.stroyBtn -> {
                //startActivity(Intent(this, MeowBoxStoryActivity::class.java))
            }
            R.id.orderBtn -> {
                if (SharedPreference.instance!!.getPrefStringData("token")!!.isEmpty()) {
                    val dialog = LoginCustomDialog(this)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                } else {
                    if (SharedPreference.instance!!.getPrefStringData("cat_idx")!! == "-1") {
                        val dialog = CatCustomDialog(this)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.show()
                    } else {
                        val intent = Intent(this, OrderWithCatInfoActivity::class.java)
                        intent.putExtra("cat_idx", SharedPreference.instance!!.getPrefStringData("cat_idx")!!)
                        startActivity(intent)
                    }
                }
            }
            R.id.reviewBtn -> {
                var intent = Intent(this, MeowBoxReviewActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.myPageBtn -> {
                if (SharedPreference.instance!!.getPrefStringData("token")!!.isEmpty()) {
                    val dialog = LoginToMyPageCustomDialog(this)
                    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.show()
                } else {
                    startActivity(Intent(this, MyPageActivity::class.java))
                }
            }
            R.id.birthDayBtn -> {
                startActivity(Intent(this, MeowBoxtBirthDayStoryActivity::class.java))
            }

        }

        story_drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
