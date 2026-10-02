package com.example.data.network

import com.example.data.model.JobItem
import com.example.data.model.NavCategory
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetlifyJobResponse(
    @Json(name = "status") val status: String? = null,
    @Json(name = "message") val message: String? = null,
    @Json(name = "lastUpdated") val lastUpdated: String? = null,
    @Json(name = "jobs") val jobs: List<NetlifyJobDto>? = null
)

@JsonClass(generateAdapter = true)
data class NetlifyJobDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "hindiTitle") val hindiTitle: String? = null,
    @Json(name = "organization") val organization: String? = null,
    @Json(name = "category") val category: String? = null,
    @Json(name = "totalVacancies") val totalVacancies: String? = null,
    @Json(name = "lastDate") val lastDate: String? = null,
    @Json(name = "qualification") val qualification: String? = null,
    @Json(name = "ageLimit") val ageLimit: String? = null,
    @Json(name = "applicationFee") val applicationFee: String? = null,
    @Json(name = "location") val location: String? = null,
    @Json(name = "salary") val salary: String? = null,
    @Json(name = "briefDescription") val briefDescription: String? = null,
    @Json(name = "hindiDescription") val hindiDescription: String? = null,
    @Json(name = "selectionProcess") val selectionProcess: String? = null,
    @Json(name = "startDate") val startDate: String? = null,
    @Json(name = "examDate") val examDate: String? = null,
    @Json(name = "officialNotificationUrl") val officialNotificationUrl: String? = null,
    @Json(name = "applyOnlineUrl") val applyOnlineUrl: String? = null,
    @Json(name = "officialWebsiteUrl") val officialWebsiteUrl: String? = null,
    @Json(name = "isFeatured") val isFeatured: Boolean? = null,
    @Json(name = "badgeType") val badgeType: String? = null,
    @Json(name = "tags") val tags: String? = null
) {
    fun toJobItem(): JobItem {
        val safeId = id?.ifBlank { null } ?: "job_${System.currentTimeMillis()}_${(100..999).random()}"
        return JobItem(
            id = safeId,
            title = title?.ifBlank { "Government Recruitment Notification" } ?: "Recruitment Notice",
            hindiTitle = hindiTitle ?: "",
            organization = organization ?: "Govt Department",
            category = category?.uppercase()?.let { cat ->
                when {
                    cat.contains("HARYANA") -> NavCategory.HARYANA_JOBS.id
                    cat.contains("CENTRAL") -> NavCategory.CENTRAL_JOBS.id
                    cat.contains("ADMIT") -> NavCategory.ADMIT_CARD.id
                    cat.contains("RESULT") -> NavCategory.RESULT.id
                    cat.contains("ADMISSION") -> NavCategory.ADMISSION.id
                    cat.contains("ANSWER") -> NavCategory.ANSWER_KEY.id
                    else -> NavCategory.LATEST_JOBS.id
                }
            } ?: NavCategory.HARYANA_JOBS.id,
            totalVacancies = totalVacancies ?: "Multiple Posts",
            lastDate = lastDate ?: "Check Notification",
            qualification = qualification ?: "10th / 12th / Graduate",
            ageLimit = ageLimit ?: "18-42 Years",
            applicationFee = applicationFee ?: "Refer to official notification",
            location = location ?: "Haryana",
            salary = salary ?: "As per rules",
            briefDescription = briefDescription ?: "",
            hindiDescription = hindiDescription ?: "",
            selectionProcess = selectionProcess ?: "Written Exam, Document Verification",
            startDate = startDate ?: "",
            examDate = examDate ?: "To be announced",
            officialNotificationUrl = officialNotificationUrl ?: "https://hssc.gov.in",
            applyOnlineUrl = applyOnlineUrl ?: "https://onetimeregn.haryana.gov.in",
            officialWebsiteUrl = officialWebsiteUrl ?: "https://haryana.gov.in",
            isFeatured = isFeatured ?: false,
            isBookmarked = false,
            publishDate = "Oct 2026",
            updateTimestamp = System.currentTimeMillis(),
            badgeType = badgeType ?: "NEW",
            tags = tags ?: "12th,Graduate,Govt Job"
        )
    }
}
