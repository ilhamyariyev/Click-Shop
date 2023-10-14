package com.info.clickshop.presentation.ui.account

import androidx.navigation.fragment.findNavController
import com.info.clickshop.databinding.FragmentAccountBinding
import com.info.clickshop.common.base.BaseFragment


class AccountFragment : BaseFragment<FragmentAccountBinding>(FragmentAccountBinding::inflate) {
    override fun onViewCreateFinish() {
        binding.buttonProfile.setOnClickListener {
            findNavController().navigate(AccountFragmentDirections.toprofile())
        }

    }

    override fun observeEvents() {

    }

}