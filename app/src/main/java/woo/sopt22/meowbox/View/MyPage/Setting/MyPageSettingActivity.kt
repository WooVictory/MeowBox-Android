package woo.sopt22.meowbox.View.MyPage.Setting

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_my_page_setting.*
import woo.sopt22.meowbox.R
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout


class MyPageSettingActivity : AppCompatActivity(), View.OnClickListener{
    override fun onClick(p0: View?) {
        when(p0) {
            my_page_setting_x_btn -> {
                finish()
            }

            my_page_terms_btn->{
                startActivity(Intent(this, TermsActivity::class.java))
            }
        }
    }

    // var tv1 = my_page_setting_disjoin as TextView
    lateinit var myPageSettingXBtn : ImageView
    lateinit var myPageSettingTerms : RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_setting)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            window.statusBarColor = Color.BLACK

        val content = SpannableString("여기")
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        my_page_setting_disjoin.setText(content)

        myPageSettingXBtn = my_page_setting_x_btn as ImageView
        my_page_setting_x_btn.setOnClickListener(this)

        myPageSettingTerms = my_page_terms_btn as RelativeLayout
        my_page_terms_btn.setOnClickListener(this)


    }
}
