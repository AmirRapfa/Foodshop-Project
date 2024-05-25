package ir.dunijet.duni_food

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FoodAdapter(private val data: ArrayList<Food>, private val foodevent: FoodEvents) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {


    inner class FoodViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        val imgfood = itemView.findViewById<ImageView>(R.id.item_img_food)
        val txtsubject = itemView.findViewById<TextView>(R.id.item_txt_subject)
        val txtcity = itemView.findViewById<TextView>(R.id.item_txt_city)
        val txtprice = itemView.findViewById<TextView>(R.id.item_txt_price)
        val txtdistance = itemView.findViewById<TextView>(R.id.item_txt_distance)
        val ratingbaar = itemView.findViewById<RatingBar>(R.id.item_rating)
        val ratingnumber = itemView.findViewById<TextView>(R.id.item_rait)


        fun binddata(position: Int) {

            txtsubject.text = data[position].TextSubJect
            txtcity.text = data[position].Textcity
            txtprice.text = "$ " + data[position].TextPrice + "  vip"
            txtdistance.text = data[position].Textdistance + " miles For you"
            ratingbaar.rating = data[position].Rating
            ratingnumber.text = "(" + data[position].Numofrating.toString() + " Ratings)"

            Glide.with(context)
                .load(data[position].Urlimage)

                .into(imgfood)


            itemView.setOnClickListener {
                foodevent.onfoodClicked(data[adapterPosition], adapterPosition)
            }


            itemView.setOnLongClickListener {
                foodevent.onfoodLongclicked(data[adapterPosition], adapterPosition)

                true
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val amir = LayoutInflater.from(parent.context).inflate(R.layout.food_items, parent, false)
        return FoodViewHolder(amir, parent.context)

    }
    override fun getItemCount(): Int
    {
        return data.size
    }
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.binddata(position)


    }


    fun addfood(newfood: Food) {
        data.add(0, newfood)
        notifyItemInserted(0)

    }
    fun removefood(oldfood: Food, oldposition: Int) {
        data.remove(oldfood)
        notifyItemRemoved(oldposition)
    }
    fun updatefood(newfood: Food, position: Int) {

        data.set(position, newfood)
        notifyItemChanged(position)

    }
    fun setdata(newlist :ArrayList<Food>){
        data.clear()
        data.addAll(newlist)
        notifyDataSetChanged()
    }


    interface FoodEvents {
        fun onfoodClicked(food: Food, position: Int)

        fun onfoodLongclicked(food: Food, pos: Int)

    }

}