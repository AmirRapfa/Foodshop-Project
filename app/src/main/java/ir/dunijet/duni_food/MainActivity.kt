package ir.dunijet.duni_food

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.dunijet.duni_food.databinding.ActivityMainBinding
import ir.dunijet.duni_food.databinding.DialogAddItemBinding
import ir.dunijet.duni_food.databinding.DialogRemoveItemBinding
import ir.dunijet.duni_food.databinding.DialogUpdateItemBinding
const val LOG_MAINACTIVITY = "activitymain"
class MainActivity() : AppCompatActivity(), FoodAdapter.FoodEvents, Parcelable {
    lateinit var binding: ActivityMainBinding
    lateinit var myadapterr: FoodAdapter

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.v(LOG_MAINACTIVITY, "oncreate seda zade shode ast")

        val foodlist = arrayListOf(
            Food(
                "Hamburger",
                "15",
                "3",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food1.jpg",
                20,
                4.5f
            ),
            Food(
                "Grilled fish",
                "20",
                "2.1",
                "Tehran, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food2.jpg",
                10,
                4f
            ),
            Food(
                "Lasania",
                "40",
                "1.4",
                "Isfahan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food3.jpg",
                30,
                2f
            ),
            Food(
                "pizza",
                "10",
                "2.5",
                "Zahedan, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food4.jpg",
                80,
                1.5f
            ),
            Food(
                "Sushi",
                "20",
                "3.2",
                "Mashhad, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food5.jpg",
                200,
                3f
            ),
            Food(
                "Roasted Fish",
                "40",
                "3.7",
                "Jolfa, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food6.jpg",
                50,
                3.5f
            ),
            Food(
                "Fried chicken",
                "70",
                "3.5",
                "NewYork, USA",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food7.jpg",
                70,
                2.5f
            ),
            Food(
                "Vegetable salad",
                "12",
                "3.6",
                "Berlin, Germany",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food8.jpg",
                40,
                4.5f
            ),
            Food(
                "Grilled chicken",
                "10",
                "3.7",
                "Beijing, China",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food9.jpg",
                15,
                5f
            ),
            Food(
                "Baryooni",
                "16",
                "10",
                "Ilam, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food10.jpg",
                28,
                4.5f
            ),
            Food(
                "Ghorme Sabzi",
                "11.5",
                "7.5",
                "Karaj, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food11.jpg",
                27,
                5f
            ),
            Food(
                "Rice",
                "12.5",
                "2.4",
                "Shiraz, Iran",
                "https://dunijet.ir/YaghootAndroidFiles/DuniFoodSimple/food12.jpg",
                35,
                2.5f
            ),
        )
        Log.v(LOG_MAINACTIVITY, "tedad item haye foodlist : ${foodlist.size}")

        myadapterr = FoodAdapter(foodlist.clone() as ArrayList<Food>, this)

        binding.recyclermain.adapter = myadapterr

        binding.recyclermain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        binding.btnAddNewfood.setOnClickListener {

            Log.v(LOG_MAINACTIVITY, "klicked on btn addnewfood")
            val dialog = AlertDialog.Builder(this).create()
            val view = DialogAddItemBinding.inflate(layoutInflater)
            dialog.setView(view.root)
            dialog.setCancelable(true)
            dialog.show()

            view.dialogBtnDone.setOnClickListener {

                if (view.dialogEdtNameFood.length() > 0 &&
                    view.dialogEdtFoodCity.length() > 0 &&
                    view.dialogEdtDistanceFood.length() > 0 &&
                    view.dialogEdtFoodPrice.length() > 0
                ) {

                    val txtdialogname = view.dialogEdtNameFood.text.toString()
                    val txtdialogcity = view.dialogEdtFoodCity.text.toString()
                    val txtdialogprice = view.dialogEdtFoodPrice.text.toString()
                    val txtdialogdistance = view.dialogEdtDistanceFood.text.toString()
                    val txtratingnumber: Int = (1..50).random()
                    val ratingstar: Float = (1..5).random().toFloat()

                    val randomurls = (0 until 12).random()
                    val urlpic = foodlist[randomurls].Urlimage

                    val newfood = Food(
                        txtdialogname,
                        txtdialogprice,
                        txtdialogdistance,
                        txtdialogcity,
                        urlpic,
                        txtratingnumber,
                        ratingstar
                    )

                    myadapterr.addfood(newfood)
                    dialog.dismiss()
                    binding.recyclermain.scrollToPosition(0)

                } else {
                    Toast.makeText(this, "لطفا مقادیر را وارد کنید ...(:", Toast.LENGTH_SHORT)
                        .show()
                }


            }

        }


        binding.edtSearch.addTextChangedListener {

            if (it!!.isNotEmpty()) {
                val clonelist = foodlist.clone() as ArrayList<Food>
                val filteredlist = clonelist.filter { item ->
                    item.TextSubJect.contains(it)
                }

                myadapterr.setdata(ArrayList(filteredlist))

            } else {

                myadapterr.setdata(foodlist.clone() as ArrayList<Food>)

            }

        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainActivity> {
        override fun createFromParcel(parcel: Parcel): MainActivity {
            return MainActivity(parcel)
        }

        override fun newArray(size: Int): Array<MainActivity?> {
            return arrayOfNulls(size)
        }
    }

    override fun onfoodClicked(food: Food, position: Int) {
        Log.v(LOG_MAINACTIVITY, "clicked on ${food.TextSubJect}")
        val daiilog = AlertDialog.Builder(this).create()
        val viewww = DialogUpdateItemBinding.inflate(layoutInflater)
        daiilog.setView(viewww.root)
        daiilog.setCancelable(true)
        daiilog.show()

        viewww.dialogEdtNameFood.setText(food.TextSubJect)
        viewww.dialogEdtFoodCity.setText(food.Textcity)
        viewww.dialogEdtDistanceFood.setText(food.Textdistance)
        viewww.dialogEdtFoodPrice.setText(food.TextPrice)


        viewww.dialogupdateBtnCancle.setOnClickListener {
            daiilog.dismiss()
        }



        viewww.dialogupdateBtnDone.setOnClickListener {

            if (viewww.dialogEdtNameFood.length() > 0 &&
                viewww.dialogEdtFoodCity.length() > 0 &&
                viewww.dialogEdtDistanceFood.length() > 0 &&
                viewww.dialogEdtFoodPrice.length() > 0
            ) {

                val txtdialogname = viewww.dialogEdtNameFood.text.toString()
                val txtdialogcity = viewww.dialogEdtFoodCity.text.toString()
                val txtdialogprice = viewww.dialogEdtFoodPrice.text.toString()
                val txtdialogdistance = viewww.dialogEdtDistanceFood.text.toString()

                val newfood = Food(
                    txtdialogname,
                    txtdialogprice,
                    txtdialogdistance,
                    txtdialogcity,
                    food.Urlimage,
                    food.Numofrating,
                    food.Rating
                )

                myadapterr.updatefood(newfood, position)
                daiilog.dismiss()

            } else {
                Toast.makeText(this, "لطفا مقادیر را وارد کنید...", Toast.LENGTH_SHORT).show()
            }

        }


    }

    override fun onfoodLongclicked(food: Food, pos: Int) {

        val dalog = AlertDialog.Builder(this).create()
        val vieww = DialogRemoveItemBinding.inflate(layoutInflater)
        dalog.setView(vieww.root)
        dalog.setCancelable(true)
        dalog.show()

        vieww.btnDialogDeleteCancel.setOnClickListener {
            dalog.dismiss()
        }


        vieww.btnDialogDeleteShure.setOnClickListener {
            dalog.dismiss()
            myadapterr.removefood(food, pos)

        }

    }
}

