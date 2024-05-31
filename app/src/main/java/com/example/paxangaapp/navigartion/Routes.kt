package com.example.paxangaapp.navigartion

sealed class Routes(val routes: String) {

    object SplashScreen : Routes("SplashScreen")
    object MatchScreen : Routes("MatchScreen")
    object Onboarding : Routes("Onboarding")
    object ClassificationScreen : Routes("ClassificationScreen")
    object PlayerClasScreen : Routes("PlayerClasScreen")
    object NewPlayer : Routes("NewPlayer")
    object PlayerInfoScreen : Routes("PlayerInfoScreen")
    object TeamInfoScreen : Routes("TeamInfoScreen")
    object TabRowMatchScreen : Routes("TabRowMatchScreen")
    object SeeMatches : Routes("SeeMatches")
    object NewTeam : Routes("NewTeam")
    object NoLeagueScreen : Routes("NoLeagueScreen")
    object MatchModifier : Routes("MatchModifier")
    object MatchPlayedModifier : Routes("MatchPlayedModifier")
    object PlayerMatchStats : Routes("PlayerMatchStats")
    object FirstLogin : Routes("FirstLogin")
    object NewAdmin : Routes("NewAdmin")


}