package com.example.siatep.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.siatep.databinding.FragmentPengumumanBinding
import com.example.siatep.preferences.UserPreferences
import com.example.siatep.preferences.dataStore


class PengumumanFragment : Fragment() {

    private var _binding: FragmentPengumumanBinding? = null

    private lateinit var userPreferences: UserPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(PengumumanViewModel::class.java)

        _binding = FragmentPengumumanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userPreferences = UserPreferences.getInstance(requireContext().dataStore)

        lifecycleScope.launchWhenStarted {
            userPreferences.getSession().collect { user ->
                binding.namaSiswa.text = user.name
                binding.kelas.text = user.id_kelas.toString()
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}