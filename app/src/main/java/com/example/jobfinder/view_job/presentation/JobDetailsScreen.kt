package com.example.jobfinder.view_job.presentation

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.HomeRepairService
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jobfinder.R
import com.example.jobfinder.common.presentation.JobFinderAppButton
import com.example.jobfinder.home.common.OrgIcon
import com.example.jobfinder.ui.theme.JobFinderTheme

@Composable
fun JobDetailsScreen() {

}


@Composable
fun JobDescriptionScreenContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                HeaderSection(
                    jobTitle = "UI Designer",
                    companyName = "Google",
                    companyLogo = null,
                    location = "Mountain View, CA"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                JobActionBox(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    icon = Icons.Default.Money,
                    action = "Salary/mnth",
                    description = "$42K - $50K"
                )
                JobActionBox(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    icon = Icons.Default.HomeRepairService,
                    action = "Job Type",
                    description = "Remote"
                )

            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                JobActionBox(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    icon = Icons.Default.Business,
                    action = "Working Model",
                    description = "$42K - $50K"
                )
                JobActionBox(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    icon = Icons.Default.BarChart,
                    action = "Level",
                    description = "Remote"
                )

            }

        }
        JobFinderAppButton(
            modifier = Modifier.padding(16.dp),
            onClick = { /*TODO*/ }, text = "Apply Now"
        )


    }


}

@Composable
fun HeaderSection(
    jobTitle: String,
    companyName: String,
    companyLogo: String?,
    modifier: Modifier = Modifier,
    location: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OrgIcon(
            orgIcon = companyLogo,
            defaultIcon = R.drawable.google
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = jobTitle,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface

        )
        Text(
            text = companyName,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = location,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }

}

@Composable
fun JobActionBox(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    action: String,
    description: String
) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .size(40.dp),
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = action,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }
        }

    }

}


@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewJobDescription() {
    JobFinderTheme {
        JobDescriptionScreenContent()
    }
}