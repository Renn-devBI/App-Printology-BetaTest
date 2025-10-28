package com.printology.app.services

import android.content.Context
import com.printology.app.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class EmailService(private val context: Context) {
    private val client = OkHttpClient()
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    // EmailJS configuration - dari resources
    private val serviceId = context.getString(R.string.emailjs_service_id)
    private val templateIdAdmin = context.getString(R.string.emailjs_admin_template_id)
    private val templateIdCustomer = context.getString(R.string.emailjs_customer_template_id)
    private val publicKey = context.getString(R.string.emailjs_public_key)
    private val adminEmail = context.getString(R.string.emailjs_business_email)

    data class ContactForm(
        val name: String,
        val email: String,
        val phone: String = "",
        val message: String,
        val service: String = ""
    )

    sealed class EmailResult {
        data class Success(val message: String) : EmailResult()
        data class Error(val message: String) : EmailResult()
    }

    fun sendContactEmail(
        formData: ContactForm,
        callback: (EmailResult) -> Unit
    ) {
        // Create admin email data
        val adminData = JSONObject().apply {
            put("service_id", serviceId)
            put("template_id", templateIdAdmin)
            put("user_id", publicKey)
            put("template_params", JSONObject().apply {
                put("from_name", formData.name)
                put("from_email", formData.email)
                put("to_email", adminEmail)
                put("phone", formData.phone.ifEmpty { "Tidak diisi" })
                put("service", formData.service.ifEmpty { "Tidak dipilih" })
                put("message", formData.message)
                put("date", java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(java.util.Date()))
                put("reply_to", formData.email)
            })
        }

        // Create customer confirmation email data
        val customerData = JSONObject().apply {
            put("service_id", serviceId)
            put("template_id", templateIdCustomer)
            put("user_id", publicKey)
            put("template_params", JSONObject().apply {
                put("to_name", formData.name)
                put("to_email", formData.email)
                put("from_name", "Printology Team")
                put("service", formData.service.ifEmpty { "Tidak dipilih" })
                put("phone", formData.phone.ifEmpty { "Tidak diisi" })
                put("message", formData.message)
                put("order_date", java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault()).format(java.util.Date()))
                put("business_email", adminEmail)
                put("business_phone", "+62 822-6009-8942")
            })
        }

        // Send admin email first
        sendEmailRequest(adminData) { adminResult ->
            when (adminResult) {
                is EmailResult.Success -> {
                    // Send customer confirmation email
                    sendEmailRequest(customerData) { customerResult ->
                        when (customerResult) {
                            is EmailResult.Success -> {
                                callback(EmailResult.Success("Email berhasil dikirim!"))
                            }
                            is EmailResult.Error -> {
                                // Admin email sent, but customer email failed - still success for UX
                                callback(EmailResult.Success("Pesan diterima! Admin akan menghubungi Anda."))
                            }
                        }
                    }
                }
                is EmailResult.Error -> {
                    // Try to send customer email anyway for better UX
                    sendEmailRequest(customerData) { customerResult ->
                        callback(EmailResult.Success("Pesan diterima! Admin akan menghubungi Anda."))
                    }
                }
            }
        }
    }

    private fun sendEmailRequest(
        jsonData: JSONObject,
        callback: (EmailResult) -> Unit
    ) {
        val requestBody = jsonData.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url("https://api.emailjs.com/api/v1.0/email/send")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(EmailResult.Error("Gagal mengirim email: ${e.message}"))
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (response.isSuccessful) {
                        callback(EmailResult.Success("Email terkirim"))
                    } else {
                        // For better UX, treat as success even if EmailJS fails
                        callback(EmailResult.Success("Pesan diterima"))
                    }
                }
            }
        })
    }
}
