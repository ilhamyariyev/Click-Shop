package com.info.clickshop.presentation.ui.auth

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.info.clickshop.R
import com.info.clickshop.common.util.ValidationHelper
import com.info.clickshop.common.util.setupBottomSheetDialog
import com.info.clickshop.common.util.showErrorMotionToastMessage
import com.info.clickshop.common.util.showSuccessMotionToastMessage
import com.info.clickshop.databinding.FragmentLoginBinding
import com.info.clickshop.domain.state.AuthUiState
import com.info.clickshop.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    @Inject
    lateinit var helper: ValidationHelper
    private val viewModel: LoginMMVM by viewModels()

    override fun onViewCreateFinish() {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        with(binding){
            textViewSignUp.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.toRegister())
            }
            button.setOnClickListener {
                validateData()
            }
            textViewForgotPassword.setOnClickListener {
                setupBottomSheetDialog { email ->
                    viewModel.resetPassword(email)
                }
            }
        }
    }

    override fun observeEvents() {
        with(viewModel) {
            with(binding) {
                authResult.observe(viewLifecycleOwner) {
                    when (it) {
                        is AuthUiState.Loading -> {
                            button.startAnimation()
                        }

                        is AuthUiState.Success -> {
                            button.revertAnimation()
                            requireActivity().showSuccessMotionToastMessage(
                                "Welcome!",
                                "Hi , Happy Shopping",
                                MotionToastStyle.SUCCESS
                            )
                            findNavController().navigate(LoginFragmentDirections.tohome())
                        }

                        is AuthUiState.Error -> {
                            button.revertAnimation()
                            requireActivity().showErrorMotionToastMessage(
                                "Oops Sorry!",
                                "Login was not successful",
                                MotionToastStyle.ERROR
                            )
                        }
                    }
                }

                resetPassword.observe(viewLifecycleOwner) {
                    when (it) {
                        is AuthUiState.Loading -> {

                        }

                        is AuthUiState.Success -> {
                            requireActivity().showSuccessMotionToastMessage(
                                "Link sent",
                                "You can update the password",
                                MotionToastStyle.SUCCESS
                            )
                        }

                        is AuthUiState.Error -> {
                            requireActivity().showErrorMotionToastMessage(
                                "Oops Sorry!",
                                "Email was not sent successfully",
                                MotionToastStyle.ERROR
                            )
                        }
                    }
                }
            }
        }
    }

    private fun validateData(){
        with(binding){
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            if (helper.validateData(editTextEmail,editTextPassword,TextInputLayoutEmail,TextInputLayoutPassword)){
                viewModel.loginUser(email,password)
            }
        }
    }
}

