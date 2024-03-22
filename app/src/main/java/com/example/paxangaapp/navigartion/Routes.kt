package com.example.paxangaapp.navigartion

sealed class Routes(val routes: String) {

    object SplashScreen : Routes("SplashScreen")

    object MatchScreen : Routes("MatchScreen")
    object Onboarding : Routes("Onboarding")


}