package com.survivalcoding.gangnam2kiandroidstudy.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.VisualTransformation.Companion.None
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppColors
import com.survivalcoding.gangnam2kiandroidstudy.ui.theme.AppTextStyles

@Composable
fun InputField(
    label: String,
    text: String,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = None,
    onValueChange: (String) -> Unit = {},
) {
    Column(
        modifier = modifier.size(315.dp, 81.dp)
    ) {
        Text(
            text = label,
            style = AppTextStyles.smallTextRegular,
            color = AppColors.black,
        )

        Spacer(modifier = Modifier.height(5.dp))

        OutlinedTextField(
            modifier = Modifier.size(310.dp, 55.dp),
            value = text,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(10.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    style = AppTextStyles.smallerTextRegular,
                    color = AppColors.gray2,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.primary100,
                unfocusedBorderColor = AppColors.gray4,
                focusedTextColor = AppColors.black,
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
        )
    }
}
@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    Column {
        InputField("Label", "")
        Spacer(modifier = Modifier.height(16.dp))
        InputField("Label", "")
        Spacer(modifier = Modifier.height(16.dp))
        InputField("Label", "placeholder")
    }
}
