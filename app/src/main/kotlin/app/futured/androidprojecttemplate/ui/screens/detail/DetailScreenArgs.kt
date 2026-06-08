package app.futured.androidprojecttemplate.ui.screens.detail

import kotlinx.serialization.Serializable

@Serializable
data class DetailScreenArgs(val title: String, val subtitle: String? = null, val value: String? = null)
