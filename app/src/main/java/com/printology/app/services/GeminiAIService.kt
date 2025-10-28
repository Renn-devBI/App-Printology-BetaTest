package com.printology.app.services

import android.content.Context
import com.printology.app.R
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class GeminiAIService(private val context: Context) {

    private val client = OkHttpClient()
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    // Configuration from resources
    private val apiKey = context.getString(R.string.google_ai_api_key)
    private val apiVersion = "v1beta"

    private val models = arrayOf(
        "gemini-2.0-flash-exp",
        "gemini-1.5-flash",
        "gemini-1.5-flash-8b",
        "gemini-1.5-pro",
        "gemini-1.0-pro"
    )

    private val basePrompt = """
🎨 IDENTITAS & TUJUAN:
Anda adalah AI Assistant resmi untuk PRINTOLOGY, layanan cetak dan fotokopi profesional dengan website lengkap.
Dikembangkan oleh Kelompok 1 Kewirausahaan & UMKM Institut Bisnis dan Informatika Kosgoro 57.
Tugas Anda adalah membantu pelanggan dengan ramah seputar semua fungsi website Printology termasuk cara pembayaran dan bantuan pelanggan.

👨‍💻 TIM PENGEMBANG & DEVOLOPER:
• Rendy Widjaya (05202540050)
• Muhammad Rafiantama (05202540089)
• Afdal Albani Aserih (05202540084)
• Ridwan Abdul Rozaq (05202540075)

📱 MEDIA SOSIAL:
• Instagram: @printology.id
• Facebook: @Printology.id
• TikTok: @printology

🏫 LATAR BELAKANG:
Printology dikembangkan sebagai proyek kewirausahaan mahasiswa IBIK 57 untuk mendukung layanan percetakan digital dan konvensional dengan website modern.

🎯 LAYANAN UTAMA:
• Fotokopi HVS 70gr: Rp500/lembar
• Print Hitam Putih: Rp1.000/lembar
• Print Berwarna: Rp2.000/lembar
• Cetak Foto 2R: Rp2.000/lembar
• Cetak Foto 3R: Rp3.000/lembar
• Cetak Foto 4R: Rp5.000/lembar
• Sticker Vinyl Custom: Rp4.000–Rp10.000
• Laminating Kartu: Rp2.000/kartu

🏢 INFORMASI BISNIS:
📍 Alamat: Jl. Raya Lenteng Agung No. 123, Jakarta LA
📞 Telepon: +62 822-6009-8942
📧 Email: rinarza8@gmail.com
⏰ Jam Operasional: Senin–Sabtu, 08.00–20.00 WIB
🌐 Website: https://printology.my.id

💳 CARA PEMBAYARAN:
• Bayar di Tempat (COD): Pembayaran saat barang diantar
• Konfirmasi pembayaran via email atau telepon untuk memproses pesanan

🛒 FUNGSI WEBSITE:
• Keranjang Belanja Online: Tambah produk ke keranjang
• Checkout Otomatis: Form pengiriman dan pembayaran
• Konfirmasi Email: Otomatis kirim detail pesanan
• Chat AI Real-time: Bantuan instan 24/7
• Form Kontak: Kirim pesan dan inquiry
• Jam Real-time: Waktu Indonesia Barat
• Pemesanan Online: Order langsung dari website

🎉 PROMO & PENAWARAN:
• Diskon 10% untuk pemesanan di atas 50 lembar
• Gratis konsultasi desain untuk produk kustom
• Promo spesial via sosial media

📞 BANTUAN PELANGGAN:
• Chat AI di website untuk pertanyaan cepat
• Form kontak untuk inquiry detail
• Telepon/WhatsApp untuk konfirmasi urgent
• Email untuk follow-up pesanan
• Sosial media untuk update promo

⚠️ BATASAN:
- Jangan membahas hal di luar konteks layanan Printology.
- Jangan memberikan detail pribadi developer selain yang disebutkan.
- Jangan menjelaskan proses teknis internal sistem.
- Tidak perlu menjawab topik tentang kampus kecuali untuk konteks kewirausahaan proyek ini.

🧭 PEDOMAN RESPON:
1. Fokus pada semua fungsi website: layanan cetak, pembayaran, bantuan, pemesanan online.
2. Jelaskan cara menggunakan website, checkout, pembayaran, dan bantuan pelanggan.
3. Boleh memberikan saran ringan seputar file, desain, atau format yang cocok untuk dicetak.
4. Gunakan bahasa Indonesia yang sopan, ramah, dan mudah dipahami.
5. Jawaban maksimal 4–5 kalimat agar informatif namun tetap ringkas.
6. Jika pertanyaan di luar konteks, arahkan dengan sopan ke topik layanan Printology.
7. Berikan info pembayaran dan bantuan ketika relevan.

💬 RESPONS STANDAR UNTUK PERTANYAAN DI LUAR TOPIK:
"Maaf, saya hanya bisa membantu seputar layanan percetakan Printology. Apakah Anda ingin mengetahui cara pembayaran, bantuan pemesanan, atau info layanan cetak tertentu?"
    """.trimIndent()

    data class AIResult(
        val success: Boolean,
        val response: String? = null,
        val error: String? = null
    )

    suspend fun generateResponse(userMessage: String): AIResult = suspendCoroutine { continuation ->
        if (!isApiKeyValid()) {
            continuation.resume(AIResult(false, error = "Google AI API Key tidak valid"))
            return@suspendCoroutine
        }

        generateWithFallback(userMessage, 0) { result ->
            continuation.resume(result)
        }
    }

    private fun generateWithFallback(userMessage: String, modelIndex: Int, callback: (AIResult) -> Unit) {
        if (modelIndex >= models.size) {
            callback(AIResult(false, error = "Semua model AI gagal. Periksa koneksi internet."))
            return
        }

        val currentModel = models[modelIndex]
        val url = "https://generativelanguage.googleapis.com/$apiVersion/models/$currentModel:generateContent?key=$apiKey"

        val payload = JSONObject().apply {
            put("contents", JSONArray().put(JSONObject().apply {
                put("role", "user")
                put("parts", JSONArray().put(JSONObject().apply {
                    put("text", "$basePrompt\n\nPelanggan: $userMessage\nAssistant:")
                }))
            }))
            put("generationConfig", JSONObject().apply {
                put("temperature", 0.7)
                put("maxOutputTokens", 150)
                put("topP", 0.85)
                put("topK", 20)
            })
            put("safetySettings", JSONArray().apply {
                put(JSONObject().apply {
                    put("category", "HARM_CATEGORY_HARASSMENT")
                    put("threshold", "BLOCK_NONE")
                })
                put(JSONObject().apply {
                    put("category", "HARM_CATEGORY_HATE_SPEECH")
                    put("threshold", "BLOCK_NONE")
                })
                put(JSONObject().apply {
                    put("category", "HARM_CATEGORY_SEXUALLY_EXPLICIT")
                    put("threshold", "BLOCK_NONE")
                })
                put(JSONObject().apply {
                    put("category", "HARM_CATEGORY_DANGEROUS_CONTENT")
                    put("threshold", "BLOCK_NONE")
                })
            })
        }

        val requestBody = payload.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Try next model
                generateWithFallback(userMessage, modelIndex + 1, callback)
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { resp ->
                    try {
                        when (resp.code) {
                            200 -> {
                                val responseBody = resp.body?.string()
                                if (responseBody != null) {
                                    val jsonResponse = JSONObject(responseBody)
                                    val aiResponse = jsonResponse
                                        .optJSONArray("candidates")
                                        ?.optJSONObject(0)
                                        ?.optJSONObject("content")
                                        ?.optJSONArray("parts")
                                        ?.optJSONObject(0)
                                        ?.optString("text", "")

                                    if (!aiResponse.isNullOrEmpty()) {
                                        callback(AIResult(true, aiResponse.trim()))
                                    } else {
                                        generateWithFallback(userMessage, modelIndex + 1, callback)
                                    }
                                } else {
                                    generateWithFallback(userMessage, modelIndex + 1, callback)
                                }
                            }
                            429 -> {
                                // Rate limited, try next model
                                generateWithFallback(userMessage, modelIndex + 1, callback)
                            }
                            503 -> {
                                // Model overloaded, try next model
                                generateWithFallback(userMessage, modelIndex + 1, callback)
                            }
                            else -> {
                                generateWithFallback(userMessage, modelIndex + 1, callback)
                            }
                        }
                    } catch (e: Exception) {
                        generateWithFallback(userMessage, modelIndex + 1, callback)
                    }
                }
            }
        })
    }

    private fun isApiKeyValid(): Boolean {
        return !apiKey.isNullOrEmpty() &&
               !apiKey.contains("your_google_ai_api_key_here") &&
               apiKey.startsWith("AIza")
    }

    fun testConnection(callback: (Boolean) -> Unit) {
        val url = "https://generativelanguage.googleapis.com/$apiVersion/models?key=$apiKey"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(false)
            }

            override fun onResponse(call: Call, response: Response) {
                callback(response.isSuccessful)
            }
        })
    }
}
