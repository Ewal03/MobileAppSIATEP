package com.example.siatep.ui.home
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
//            binding.tvTanggal.text = SimpleDateFormat("dd").parse(itemData.tglAbsen).toString()
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