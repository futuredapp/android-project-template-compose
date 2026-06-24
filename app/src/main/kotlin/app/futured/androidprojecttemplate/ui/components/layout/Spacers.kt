package app.futured.androidprojecttemplate.ui.components.layout

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * Renders a spacer in Column-based layouts.
 */
@Composable
fun ColumnScope.Spacer(size: Dp, modifier: Modifier = Modifier) = Spacer(modifier = modifier.height(size))

/**
 * Renders a spacer in Row-based layouts.
 */
@Composable
fun RowScope.Spacer(size: Dp, modifier: Modifier = Modifier) = Spacer(modifier = modifier.width(size))
