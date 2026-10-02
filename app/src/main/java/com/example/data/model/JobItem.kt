package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Data entity representing a Job or Educational Notification in Job Haryana.
 * Designed to seamlessly bind to future automatic update systems/APIs.
 */
@Entity(tableName = "jobs")
data class JobItem(
    @PrimaryKey
    val id: String,
    val title: String,
    val hindiTitle: String,
    val organization: String,
    val category: String, // LATEST_JOBS, HARYANA_JOBS, CENTRAL_JOBS, ADMISSION, ADMIT_CARD, RESULT, ANSWER_KEY
    val totalVacancies: String,
    val lastDate: String,
    val qualification: String,
    val ageLimit: String = "18-42 Years",
    val applicationFee: String = "Gen: ₹100, SC/BC/EWS: ₹25",
    val location: String = "Haryana",
    val salary: String = "As per Govt Norms",
    val briefDescription: String = "",
    val hindiDescription: String = "",
    val selectionProcess: String = "Written Exam, Document Verification",
    val startDate: String = "",
    val examDate: String = "To be notified",
    val officialNotificationUrl: String = "https://hssc.gov.in",
    val applyOnlineUrl: String = "https://onetimeregn.haryana.gov.in",
    val officialWebsiteUrl: String = "https://haryana.gov.in",
    val isFeatured: Boolean = false,
    val isBookmarked: Boolean = false,
    val publishDate: String = "Oct 2026",
    val updateTimestamp: Long = System.currentTimeMillis(),
    val badgeType: String = "NEW", // NEW, URGENT, EXTENDED, POPULAR
    val tags: String = "12th,Graduate,CET"
)

enum class NavCategory(val id: String, val titleEn: String, val titleHi: String) {
    HOME("HOME", "Home", "होम"),
    LATEST_JOBS("LATEST_JOBS", "Latest Jobs", "नवीनतम नौकरियां"),
    HARYANA_JOBS("HARYANA_JOBS", "Haryana Jobs", "हरियाणा जॉब्स"),
    CENTRAL_JOBS("CENTRAL_JOBS", "Central Govt Jobs", "केंद्र सरकार जॉब्स"),
    ADMISSION("ADMISSION", "Admission", "एडमिशन"),
    ADMIT_CARD("ADMIT_CARD", "Admit Card", "एडमिट कार्ड"),
    RESULT("RESULT", "Result", "रिजल्ट"),
    ANSWER_KEY("ANSWER_KEY", "Answer Key", "उत्तर कुंजी"),
    BOOKMARKS("BOOKMARKS", "Saved Jobs", "सेव की गई नौकरियां")
}
