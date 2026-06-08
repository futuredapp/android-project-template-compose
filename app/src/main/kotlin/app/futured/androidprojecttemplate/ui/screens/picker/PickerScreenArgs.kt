package app.futured.androidprojecttemplate.ui.screens.picker

import kotlinx.serialization.Serializable

/** Key under which the picked item is returned to the calling screen via the result store. */
const val PICKED_ITEM_KEY = "picked_item"

@Serializable
enum class PickerType { Fruit, Veggie }

@Serializable
data class PickerScreenArgs(val type: PickerType)
