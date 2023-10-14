package com.info.clickshop.presentation.ui.auth

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.info.clickshop.common.util.ValidationHelper
import com.info.clickshop.common.util.showErrorMotionToastMessage
import com.info.clickshop.common.util.showSuccessMotionToastMessage
import com.info.clickshop.databinding.FragmentRegisterBinding
import com.info.clickshop.domain.state.AuthUiState
import com.info.clickshop.common.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToastStyle
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    @Inject
    lateinit var helper: ValidationHelper

    private val viewModel: RegisterMVVM by viewModels()

    override fun onViewCreateFinish() {
        with(binding){
            textViewSignUp.setOnClickListener {
                findNavController().navigate(RegisterFragmentDirections.toLogin())
            }
            button.setOnClickListener {
                validateData()
            }
        }
    }

    override fun observeEvents() {
        with(viewModel) {
            authResult.observe(viewLifecycleOwner) {
                with(binding) {
                    when (it) {
                        is AuthUiState.Success -> {
                            button.revertAnimation()
                            requireActivity().showSuccessMotionToastMessage(
                                "Congratulation!",
                                "You have created account succesfully",
                                MotionToastStyle.SUCCESS
                            )
                            findNavController().navigate(RegisterFragmentDirections.toLogin())
                        }

                        is AuthUiState.Error -> {
                            requireActivity().showErrorMotionToastMessage(
                                "Oops Sorry!",
                                "Registration was not successful",
                                MotionToastStyle.ERROR
                            )
                            button.revertAnimation()
                        }

                        is AuthUiState.Loading -> {
                            button.startAnimation()
                        }
                    }
                }
            }
        }
    }

    private fun validateData(){
        with(binding){
            val name = editTextFullName.text.toString().trim()
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            if (helper.validateData(editTextEmail,editTextPassword,TextInputLayoutEmail,TextInputLayoutPassword)&&
                    helper.validateRegisterData(editTextPassword,editTextPasswordAgain,TextInputLayoutPasswordAgain)
            ){
                viewModel.createAccountWithEmailAndPassword(email,password, name)
                viewModel.saveName(name)
                viewModel.saveEmail(email)
                viewModel.savePassword(password)
            }
        }
    }

}