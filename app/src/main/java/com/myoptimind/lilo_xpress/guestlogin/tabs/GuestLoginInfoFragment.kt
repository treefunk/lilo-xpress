package com.myoptimind.lilo_xpress.guestlogin.tabs

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginViewModel
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import com.myoptimind.lilo_xpress.shared.displayAlert
import com.myoptimind.lilo_xpress.shared.displayGenericFormError
import com.myoptimind.lilo_xpress.shared.handleData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_guest_login_info.*
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "GuestLoginFragment"
private const val REQUEST_IMAGE_CAPTURE = 1
private const val ARGS_INITIAL = "args_initial"

@AndroidEntryPoint
class GuestLoginInfoFragment : TabChildFragment<GuestLoginTab>() {

    lateinit var currentPhotoPath: String
    val viewModel: GuestLoginViewModel by activityViewModels()

    companion object {
        fun newInstance(initial: Boolean = false): GuestLoginInfoFragment {
            val fragment =
                GuestLoginInfoFragment()

            if(initial){
                val args = Bundle()
                args.putBoolean(ARGS_INITIAL,true)
                fragment.arguments = args
            }

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_login_info,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        if (bundle != null && bundle.containsKey(ARGS_INITIAL)) {
            viewModel.resetData()
        }



        card_take_a_photo.setOnClickListener {
            dispatchTakePictureIntent()
        }

        viewModel.agencies.observe(viewLifecycleOwner, Observer { result ->

            result.handleData(requireContext(),
                et_agency,
                onSelectItem = { index ->
                    viewModel.agencyIndex.value = index.toString()
                },
                onFetchFail = { _ ->
                    if(findNavController().currentDestination?.id == R.id.guestLoginFragment){
                        findNavController().navigate(R.id.action_guestLoginFragment_to_selectUserFragment)
                    }
                }
            )
        })


        et_attached_agency.isEnabled = false
        viewModel.attachedAgencies.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    if(result != null){
                        et_attached_agency.isEnabled = true
                        result.handleData(requireContext(),
                            et_attached_agency,
                            onSelectItem = { index -> viewModel.attachedAgencyIndex.value = index.toString() }
                        )
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT)
                        .show()
                }
                Result.Loading -> {
                    et_attached_agency.isEnabled = false
                }
            }

        })

        viewModel.fullName.observe(viewLifecycleOwner, Observer { fullName ->
            fullName?.let {
                if(fullName.isNotEmpty()){
                    et_full_name.setText(fullName)
                }
            }
        })

        viewModel.agency.observe(viewLifecycleOwner, Observer { agency ->
            et_agency.setText(agency,false)
        })
        viewModel.attachedAgency.observe(viewLifecycleOwner, Observer { attachedAgency ->
            et_attached_agency.setText(attachedAgency,false)
        })

        viewModel.mobileNo.observe(viewLifecycleOwner, Observer { contact ->
            et_contact.setText(contact)
        })

        viewModel.emailAddress.observe(viewLifecycleOwner, Observer { email ->
            et_email_address.setText(email)
        })

        viewModel.confirmReceipt.observe(viewLifecycleOwner, Observer { confirmReceipt ->
            cb_confirm_receipt.isChecked = confirmReceipt
        })

        viewModel.uploadedPhoto.observe(viewLifecycleOwner, Observer { file ->

            if(file != null){
                Glide.with(requireContext())
                    .load(file)
                    .centerCrop()
                    .into(iv_take_photo)
            }else{
                Glide.with(requireContext())
                    .load(R.drawable.takephoto_nowhite)
                    .into(iv_take_photo)
            }


        })

        iv_guest_info_next.setOnClickListener {
            val email = et_email_address.text.toString()
/*            if(email.isNotBlank() &&
                    !validateEmail(email)){
                requireContext().displayAlert("","Invalid E-mail Address Format.")
                return@setOnClickListener
            }*/

            if(viewModel.saveStep1(
                et_full_name.text.toString(),
                    et_contact.text.toString(),
                et_email_address.text.toString(),
                cb_confirm_receipt.isChecked
            )){
                guestTabChanger.changeTab(GuestLoginTab.PURPOSE)
            }else{
                requireContext().displayGenericFormError()
            }
        }

    }

    private fun validateEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return email.matches(emailPattern)

    }


    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireContext().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.myoptimind.lilo_xpress.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent,
                        REQUEST_IMAGE_CAPTURE
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Timber.v("success upload")
            val uploadedPhoto = File(currentPhotoPath)
/*            Glide.with(requireContext())
                .load(uploadedPhoto)
                .centerCrop()
                .into(iv_take_photo)*/
            viewModel.uploadedPhoto.value = uploadedPhoto
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = timeStamp + ".jpg"
        val storageDir = File(requireContext().filesDir,"uploaded")
        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }else{
            // clear images if there's any
            storageDir.listFiles {
                    file -> file.delete()
            }
        }
        val image = File(
            storageDir,
            imageFileName
        )
        currentPhotoPath = image.absolutePath
        return image
    }

}