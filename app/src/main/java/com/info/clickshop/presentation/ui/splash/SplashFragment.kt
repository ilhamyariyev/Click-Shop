package com.info.clickshop.presentation.ui.splash

import android.view.animation.AnimationUtils
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.info.clickshop.R
import com.info.clickshop.databinding.FragmentSplashBinding
import com.info.clickshop.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel : SplashMVVM by viewModels()

    override fun onViewCreateFinish() {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.blue)
        setAnimation()
        finishActivity()
    }

    override fun observeEvents() {
        viewModel.auth.observe(viewLifecycleOwner){
            if (it){
                findNavController().navigate(SplashFragmentDirections.toLogin())
            }else{
                findNavController().navigate(SplashFragmentDirections.tohome())
            }
        }
    }



    private fun setAnimation(){
        val slideDownAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        binding.splashText2.startAnimation(slideDownAnimation)

        val fadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        binding.splashImage.startAnimation(fadeInAnimation)

        val slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        binding.splashText1.startAnimation(slideUpAnimation)
    }

    fun finishActivity(){
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            requireActivity().finish()
        }
    }
}