package com.example.siatep.ui.home
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.siatep.DetailActivity
import com.example.siatep.R
import com.example.siatep.data.response.DataItem
import com.example.siatep.databinding.ItemListRiwayatBinding
import java.text.SimpleDateFormat

class HomeAdapter: ListAdapter<DataItem, HomeAdapter.HomeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding = ItemListRiwayatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        val riwayat = getItem(position)
        holder.bind(riwayat)
    }
    inner class HomeViewHolder(val binding: ItemListRiwayatBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(itemData: DataItem) {
            binding.tvNama.text = itemData.name
            binding.tvStatus.text = itemData.status
            binding.tvKelas.text = itemData.kelas
            binding.tvTanggal.text = itemData.createdAt

            val btnCard: CardView = itemView.findViewById(R.id.btn_detail)
            btnCard.setOnClickListener {
                val context = itemView.context
                val intentDetail = Intent(context, DetailActivity::class.java).apply {
                    putExtra("NAME", itemData.name)
                    putExtra("STATUS", itemData.status)
                    putExtra("KELAS", itemData.kelas)
                    putExtra("TGL_ABSEN", itemData.createdAt)
                }
                context.startActivity(intentDetail)
            }

        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>(){
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}