package com.shakespace.advanceandroid.chapter01newfeature

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import kotlinx.android.synthetic.main.fragment_permission.*
import java.util.concurrent.Executor


/**
 * A simple [Fragment] subclass.
 */
class PermissionFragment : Fragment() {

    lateinit var executor: Executor
    /**
     * Executor 两种方式创建 （官方文档上kotlin是空实现，）
     * 1.  private val handler: Handler = Handler()
     * private val executor = Executor { command -> handler.post(command) }
     *
     * 2.  executor = ContextCompat.getMainExecutor(requireContext())
     */
//    private val handler: Handler = Handler()
//    private val executor = Executor { command -> handler.post(command) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        executor = ContextCompat.getMainExecutor(requireContext())
        return inflater.inflate(R.layout.fragment_permission, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv_apply.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.e(this.TAG, "onActivityCreated: get the permision ")
            } else {
                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            }
        }

        tv_biometric.setOnClickListener {
            val biometricManager = BiometricManager.from(requireContext())
            val canAuth = when (biometricManager.canAuthenticate()) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    Log.e(this.TAG, "onActivityCreated: App can authenticate using biometrics.")
                    true
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    Log.e(
                        this.TAG,
                        "onActivityCreated: No biometric features available on this device."
                    )
                    false
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    Log.e(
                        this.TAG,
                        "onActivityCreated: Biometric features are currently unavailable."
                    )
                    false
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    Log.e(
                        this.TAG,
                        "onActivityCreated: The user hasn't associated any biometric credentials with their account."
                    )
                    false
                }
                else -> false
            }

            if (canAuth) {
                showBiometricPrompt()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            0 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(tv_apply, "permission granted ", Snackbar.LENGTH_SHORT).show()
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        /**
                         * 当用户选择了拒绝且勾选不再询问，会调用这里
                         */
                        Snackbar.make(tv_apply, "不给也没关系 ", Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(tv_apply, "permission denied ", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            else -> ""
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showBiometricPrompt() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Cancel") // 取消和切换密码不能共存
//            .setDeviceCredentialAllowed(true) //  允许切换到设备密码
            .build()


        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    Snackbar.make(
                        tv_apply,
                        "Authentication error: $errString",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    val authenticatedCryptoObject: BiometricPrompt.CryptoObject? =
                        result.cryptoObject
                    // User has verified the signature, cipher, or message
                    // authentication code (MAC) associated with the crypto object,
                    // so you can use it in your app's crypto-driven workflows.
                    Snackbar.make(
                        tv_apply,
                        "Authentication success: $result",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    super.onAuthenticationSucceeded(result)
                }

                override fun onAuthenticationFailed() {
                    Snackbar.make(tv_apply, "Authentication failed", Snackbar.LENGTH_SHORT).show()
                    super.onAuthenticationFailed()
                }


            })

        // Displays the "log in" prompt.
        biometricPrompt.authenticate(promptInfo)
    }

}
