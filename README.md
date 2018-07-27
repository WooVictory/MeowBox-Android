# MeowBox - Android

<img src="image/meow_box.png" width="70">


반려묘를 위한 수제 필수용품과 간식을 정기 배송하는 서비스 **MeowBox** 입니다.

## # 개요

수제 필수 용품(장난감, 스크래쳐 등등)과 간식과 같은 반려묘에게 꼭 필요한 것들을 정기 배송 해줌으로써 묘주의 구매력을 높이고 새로운 아이템을 찾아야 하는 번거로움을 해결하였고, 수제 간식 같은 건강식을 통해 반려묘의 건강을 관리 할 수 있습니다. 

매달 **컨셉**이 담긴 박스입니다.
반려묘를 키우는 사람을 대상으로 맞춤형 박스를 제공합니다.
1. 초보자 박스
2. 월별 컨셉 박스
3. 생일 박스



## # 워크 플로우

![](/image/meow_box_workflow.png)

## # 주요 기능

* 메인 화면

	* 사용자는 로그인 및 회원가입을 하지 않고도 앱을 둘러보고 사용할 수 있습니다.
	* 주문하기와 마이 페이지에 접근하기 위해서는 로그인을 해야 합니다.
	* **Navigation Bar**를 이용하여 사용자가 어디서든 다른 화면으로 이동할 수 있도록 하였습니다. 
	* **onPageScrolled**() 함수의 position과 **positionOffset**값을 이용하여 Viewpager에 들어가는 item의 **Padding** 값을 조절하여 카드 형식으로 화면을 넘겨 볼 수 있도록 구성하였습니다. 

```kotlin

    var items : ArrayList<CardData>
    items = ArrayList();
    items.add(CardData(R.drawable.home_main_one_img, 1))
    items.add(CardData(R.drawable.home_main_two_img,0))
    items.add(CardData(R.drawable.home_main_three_img,0))
    items.add(CardData(R.drawable.home_main_four_img,0))
    items.add(CardData(R.drawable.home_main_five_img,2))

    main_viewpager.setPadding(0,0,200,0)
    var madapter = CardViewAdapter(layoutInflater, items)
    main_viewpager.setCurrentItem(0)
    main_viewpager.adapter = madapter

    main_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {


            }

            // 이 함수를 통해서 선택된 position 값에 따른 네비게이션 바의 색깔과 툴바의 색을 변경할 수 있다.
            // 그리고 1,2에서의 상세보기와 3,4에서의 상세보기 버튼을 다른 기능을 할 수 있도록 구현할 수 있다.
            override fun onPageSelected(position: Int) {
           

            }

            // 화면의 일부만 보이게 하기 위해서 ViewPager의 함수인 onpageScrolled에서 postion과 Offset을 수정했습니다. 
            // 오른쪽 부분이 계속 보이다가 마지막 페이지에서는 왼쪽 페이지가 보입니다.
            // 결국에는 보여지는 화면의 position의 따라서 마지막과 마지막 전의 페이지만 padding 값을 조절하면 됩니다. 
            // items의 마지막 (items.size-1)은 왼쪽의 padding을 줘서 보이도록 하고 
            // 이 함수가 페이지가 스크롤 되는 동안에도 계속해서 호출이 되기 때문에 items의 마지막 전 (items.size-2) 페이지는
             
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                when(position){
                    (items.size-2)->{
                        main_viewpager.setPadding((200 * positionOffset).toInt(),0,200 - (200*positionOffset).toInt(),0)
                    }
                    (items.size-1)->{
                        main_viewpager.setPadding(200,0,0,0)
                    }

                }

            }

        })

```
* 메인 화면 - 추가 화면
	* **Sliding Up Panel Layout**을 사용하여 아래에서 View를 끌어올릴 수 있도록 구현하였습니다. 

```kotlin

	// 클릭 이벤트가 아닌 터치 제스처를 통해서 끌어올리면 통신과 뷰가 올라올 수 있도록 구현하였습니다. 
    bottom_up_relative_layout.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when(event!!.action){
                    MotionEvent.ACTION_UP,MotionEvent.ACTION_MOVE->{

                        getInsta() // 고양이 인스타 크롤링 정보를 받아오는 통신 함수
                        getCatCount() // 등록된 고양이의 수를 받아오는 통신 함수
                        main_sliding_scroll.fullScroll(ScrollView.FOCUS_UP)
                    }
                    MotionEvent.ACTION_DOWN->{

                        main_sliding_scroll.fullScroll(ScrollView.FOCUS_UP)
                    }

                }
                return true
            }


        })
```

<img src="image/meowbox_home.png"> <img src="image/meowbox_bottom_up.png">


* 미유 박스 이야기 화면
	* 미유 박스의 목표와 미유 박스가 무엇인지, 미유 박스는 어떤 구성품으로 구성되어있는지를 설명해줍니다.
	* 그리고 미유 박스를 주문할 수 있도록 유도합니다. 

* 생일 축하해 화면
	* 반려묘의 생일을 축하하는 이야기가 담겨 있는 화면입니다.
	* 자체 제작한 웹툰이 연재되어 있습니다. 

* 집사들의 후기 화면
	* 6월, 7월, 생일 총 3개 카테고리의 후기를 볼 수 있으며 카드 형식으로 Viewpager를 구성하였습니다. 
	
* 주문하기 화면







## # Develop Environment

* Language - **Kotlin**
* Minimum SDK Version - 21
* Target SDK Version - 27
* Optimization Device - **Galaxy s8+**


## # Library

1. **Layout**
* 'com.sothree.slidinguppanel:library:3.4.0'
* 'com.android.support:cardview-v7:27.1.1'
* 'de.hdodenhof:circleimageview:2.2.0'
* 'com.android.support:recyclerview-v7:27.1.1'
* 'com.thoughtbot:expandablerecyclerview:1.3'
* 'com.zarinpal:cardviewpager:0.5.3'

2. **HTTP REST API**
* 'com.squareup.retrofit2:retrofit:2.4.0'
* 'com.squareup.retrofit2:converter-gson:2.1.0'

3. **Material design**
* 'com.android.support:design:27.1.1'

4. **Animation**
* 'com.airbnb.android:lottie:2.1.0'

