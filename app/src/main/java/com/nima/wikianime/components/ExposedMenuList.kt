package com.nima.wikianime.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedMenuList(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onExpandChanged: () -> Unit,
    onDismiss: () -> Unit,
    options: List<String?>,
    selectedOptionText: String,
    label: String,
    onClick: (String) -> Unit
) {
    ExposedDropdownMenuBox(expanded = expanded,
        onExpandedChange = {onExpandChanged()},
        modifier = modifier
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onDismiss() },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption!!) },
                    onClick = {
                        onClick(selectionOption!!)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}