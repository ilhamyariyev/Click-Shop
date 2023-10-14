
package com.info.clickshop.presentation.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.info.clickshop.R
import com.info.clickshop.common.base.BaseFragment
import com.info.clickshop.databinding.FragmentProfileBinding
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    override fun onViewCreateFinish() {
        binding.cardView.setOnClickListener {
            with(binding){
                uploadImage(imageViewProfile)
            }

        }

    }

    private fun uploadImage(imageViewProfile: CircleImageView?) {
        val intent = Intent()
        intent.action= Intent.ACTION_GET_CONTENT
        intent.type ="image/*"
        startActivityForResult(intent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1){
            binding.imageViewProfile.setImageURI(data?.data)
        }
    }

    override fun observeEvents() {

    }

}