package com.shakespace.advanceandroid.biometric

import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import kotlinx.android.synthetic.main.fragment_biometric.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import android.hardware.biometrics.BiometricPrompt as NonXPrompt
import androidx.biometric.BiometricManager as QBiometric
import androidx.biometric.BiometricPrompt as QPrompt


class BiometricFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_biometric, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val biometricManager: QBiometric = QBiometric.from(requireContext())
        when (biometricManager.canAuthenticate()) {
            QBiometric.BIOMETRIC_SUCCESS -> Log.d(TAG, "应用可以进行生物识别技术进行身份验证。")
            QBiometric.BIOMETRIC_ERROR_NO_HARDWARE -> Log.e(TAG, "该设备上没有搭载可用的生物特征功能。")
            QBiometric.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Log.e(TAG, "生物识别功能当前不可用。")
            QBiometric.BIOMETRIC_ERROR_NONE_ENROLLED -> Log.e(TAG, "用户没有录入生物识别数据。")
        }

        /**
         * int DEVICE_CREDENTIAL = 1 << 15; 设置后类似setDeviceCredentialAllowed(true)
         * 这个设置为true的话 不需要设置
         * 如果不使用，就要添加cancel button  cancel button  Negative text must not be set if device credential authentication is allowed.
         */
        tv_biometric.setOnClickListener {
            val promptInfo: QPrompt.PromptInfo = QPrompt.PromptInfo.Builder()
                .setTitle("Biometric") //设置大标题
                .setSubtitle("SubTitle") // 设置标题下的提示
                //Negative text must not be set if device credential authentication is allowed.
//                .setDeviceCredentialAllowed(true)
                .setNegativeButtonText("Cancel") //设置取消按钮
                .setAllowedAuthenticators(QBiometric.Authenticators.BIOMETRIC_WEAK)
                .build()

//需要提供的参数callback

//需要提供的参数callback
            val biometricPrompt = QPrompt(requireActivity(),
                ContextCompat.getMainExecutor(requireContext()),
                object : QPrompt.AuthenticationCallback() {
                    //各种异常的回调
                    override fun onAuthenticationError(
                        errorCode: Int,
                        @NonNull errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        Log.e(this.TAG, "onAuthenticationError:  ")
                    }

                    //认证成功的回调
                    override fun onAuthenticationSucceeded(
                        @NonNull result: QPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)
                        Log.e(this.TAG, "onAuthenticationSucceeded:  ")
                    }

                    //认证失败的回调
                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Log.e(this.TAG, "onAuthenticationFailed:  ")
                    }
                })

            // 显示认证对话框
            biometricPrompt.authenticate(promptInfo)
        }


        tv_oldbiometric.setOnClickListener {

            val prompt = NonXPrompt.Builder(requireContext())
//                .setConfirmationRequired(false)
//                .setAllowedAuthenticators(android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG)
//                .setDeviceCredentialAllowed(true)
                .setTitle("NonX Biometric")
                .setSubtitle("NonX")
                .setDescription("authenticate")
                .setNegativeButton(
                    "cancel",
                    ContextCompat.getMainExecutor(requireContext()),
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            Log.e(this.TAG, "onClick: click cancel ")
                        }
                    })
                .build()

            val cipher =
                Cipher.getInstance("AES", "BC")

//            val secretKey: SecretKey = SecretKeySpec("1234567890ABCDEF".toByteArray(), "AES")
//            val iv = IvParameterSpec("1234567890ABCDEF".toByteArray())
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)

//            val instance = KeyGenerator.getInstance(
//                "AES"
//            )
//            val key = instance.generateKey();
//            cipher.init(Cipher.ENCRYPT_MODE, key);


            prompt.authenticate(CancellationSignal(),
                ContextCompat.getMainExecutor(requireContext()),
                object : android.hardware.biometrics.BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: android.hardware.biometrics.BiometricPrompt.AuthenticationResult?) {
                        super.onAuthenticationSucceeded(result)
                        Log.e(this.TAG, "hardware onAuthenticationSucceeded:  ")
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Log.e(this.TAG, "hardware onAuthenticationFailed:  ")
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                        super.onAuthenticationError(errorCode, errString)
                        Log.e(this.TAG, "hardware onAuthenticationError:  ")
                    }
                })

//            prompt.authenticate(android.hardware.biometrics.BiometricPrompt.CryptoObject(cipher),
//                CancellationSignal(),
//                ContextCompat.getMainExecutor(requireContext()),
//                object : android.hardware.biometrics.BiometricPrompt.AuthenticationCallback() {
//                    override fun onAuthenticationSucceeded(result: android.hardware.biometrics.BiometricPrompt.AuthenticationResult?) {
//                        super.onAuthenticationSucceeded(result)
//                        Log.e(this.TAG, "hardware onAuthenticationSucceeded:  ")
//                    }
//
//                    override fun onAuthenticationFailed() {
//                        super.onAuthenticationFailed()
//                        Log.e(this.TAG, "hardware onAuthenticationFailed:  ")
//                    }
//
//                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
//                        super.onAuthenticationError(errorCode, errString)
//                        Log.e(this.TAG, "hardware onAuthenticationError:  ")
//                    }
//                }
//            )
        }

    }

}