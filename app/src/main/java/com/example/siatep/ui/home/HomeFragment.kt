package com.example.siatep.ui.home

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.siatep.MainActivity
import com.example.siatep.databinding.FragmentHomeBinding
import com.example.siatep.preferences.UserPreferences
import com.example.siatep.preferences.dataStore
import com.example.siatep.viewmodel.AbsenModelFactory
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var userPreferences: UserPreferences

    private lateinit var homeAdapter: HomeAdapter
    private val viewModel by viewModels<HomeViewModel>{
        AbsenModelFactory.getInstance(this.requireContext())
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
            if (isGranted){
                showCamera()
            }
            else{

            }
        }

    private val scanLauncher =
        registerForActivityResult(ScanContract()){ result: ScanIntentResult ->
            run {
                if (result.contents == null) {
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    setResult(result.contents)
                }
            }
        }

    private fun setResult(string: String) {
        viewModel.getSession().observe(viewLifecycleOwner){user->
            if (user.isLogin){
                if (user.id_kelas==string.toInt()){
                    viewModel.postAbsen("Hadir",LocalDate.now().toString(),user.id,string.toInt())
                    viewModel.getAbsen(user.token)
                    viewModel.absen.observe(viewLifecycleOwner){response->
                        if (response.message==true){
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setTitle("Alert!")
                            builder.setMessage("Absen Berhasil")
                            builder.setPositiveButton("Yes") { _, _ ->
                                binding.rvHome.setHasFixedSize(true)
                                binding.rvHome.layoutManager = LinearLayoutManager(context)
                                homeAdapter = HomeAdapter()
                                binding.rvHome.adapter = homeAdapter
                                homeAdapter.submitList(response.data)
                                homeAdapter.notifyDataSetChanged()
                                val intent = Intent(requireContext(), MainActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }
                            builder.show()
                            builder.create()
                        }
                    }
                }else{
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Alert!")
                    builder.setMessage("Absen gagal karena salah kelas")
                    builder.setPositiveButton("Yes") { _, _ ->
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                    builder.show()
                    builder.create()
                    Toast.makeText(context, "Salah kelas", Toast.LENGTH_SHORT).show()
                }

            }
        }

//        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR Code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)

        scanLauncher.launch(options)
    }

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        userPreferences = UserPreferences.getInstance(requireContext().dataStore)

        lifecycleScope.launchWhenStarted {
            userPreferences.getSession().collect { user ->
                binding.namaSiswa.text = user.name
                binding.kelas.text = user.id_kelas.toString()
            }
        }

        viewModel.getSession().observe(viewLifecycleOwner){user->
            if (user.isLogin){
                viewModel.getAbsen(user.token)
                viewModel.absen.observe(viewLifecycleOwner){response->
                    if (response.message==true){
                        binding.rvHome.setHasFixedSize(true)
                        binding.rvHome.layoutManager = LinearLayoutManager(context)
                        homeAdapter = HomeAdapter()
                        binding.rvHome.adapter = homeAdapter
                        homeAdapter.submitList(response.data)
                        homeAdapter.notifyDataSetChanged()

                    }
                }


            }
        }
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnScan.setOnClickListener {
            checkPermissionCamera(requireContext())
        }
        return root
    }

    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            showCamera()
        }else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
            Toast.makeText(context,"CAMERA permission required", Toast.LENGTH_SHORT)
        }else{
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}