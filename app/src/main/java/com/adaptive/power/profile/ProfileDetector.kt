
package com.adaptive.power.profile
import android.content.Context
class ProfileDetector(private val ctx: Context) {
    enum class Profile { IDLE, LIGHT, WEB, VIDEO, GAMING, HEAVY }
    fun detectNow(): Profile = Profile.IDLE
}
