package com.example.siatep.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.siatep.OnBoardingActivity
import com.example.siatep.databinding.FragmentProfileBinding
import com.example.siatep.preferences.UserPreferences
import com.example.siatep.preferences.dataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private lateinit var userPreferences: UserPreferences
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        userPreferences = UserPreferences.getInstance(requireContext().dataStore)

        lifecycleScope.launchWhenStarted {
            userPreferences.getSession().collect { user ->
                binding.namaSiswa.text = user.name
                binding.kelas.text = user.id_kelas.toString()
            }
        }
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Logout")
            builder.setMessage("Are you sure want to Logout?")
            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    userPreferences.logout()
                }
                val intent = Intent(requireContext(), OnBoardingActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
            builder.create()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}