package com.example.data.repository

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.JobDao
import com.example.data.model.JobItem
import com.example.data.model.NavCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class JobRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val dao: JobDao = db.jobDao()

    val allJobs: Flow<List<JobItem>> = dao.getAllJobsFlow()
    val featuredJobs: Flow<List<JobItem>> = dao.getFeaturedJobsFlow()
    val bookmarkedJobs: Flow<List<JobItem>> = dao.getBookmarkedJobsFlow()

    fun getJobsByCategory(category: String): Flow<List<JobItem>> = dao.getJobsByCategoryFlow(category)

    fun searchJobs(query: String): Flow<List<JobItem>> = dao.searchJobsFlow(query)

    fun getJobById(id: String): Flow<JobItem?> = dao.getJobByIdFlow(id)

    suspend fun toggleBookmark(id: String, currentStatus: Boolean) {
        withContext(Dispatchers.IO) {
            dao.updateBookmark(id, !currentStatus)
        }
    }

    /**
     * Initializes pre-seeded data if local database is empty.
     * Ready for automatic sync from remote job-update APIs/scrapers.
     */
    suspend fun checkAndSeedInitialData() {
        withContext(Dispatchers.IO) {
            val count = dao.getJobCount()
            if (count == 0) {
                dao.insertJobs(getInitialSeedData())
            }
        }
    }

    /**
     * Simulated or actual network job-update sync.
     * When remote API/webhook is configured, this fetches the latest entries
     * and upserts into Room database.
     */
    suspend fun syncLatestUpdates(): Int {
        return withContext(Dispatchers.IO) {
            // Future automated sync endpoint integration point
            // For now, refresh timestamp and guarantee all active listings are populated
            val currentCount = dao.getJobCount()
            if (currentCount == 0) {
                dao.insertJobs(getInitialSeedData())
            }
            // Return number of available items
            dao.getJobCount()
        }
    }

    private fun getInitialSeedData(): List<JobItem> {
        return listOf(
            // --- HARYANA GOVERNMENT JOBS & LATEST JOBS ---
            JobItem(
                id = "hssc-cet-c-2026",
                title = "HSSC CET Group C Phase 2 Recruitment 2026",
                hindiTitle = "एचएसएससी सीईटी ग्रुप सी मेन्स भर्ती 2026",
                organization = "HSSC (Haryana Staff Selection Commission)",
                category = NavCategory.HARYANA_JOBS.id,
                totalVacancies = "7,575 Posts",
                lastDate = "28 Oct 2026",
                qualification = "12th Pass / Graduate + CET Qualified",
                ageLimit = "18 to 42 Years (Age relaxation as per Haryana Govt rules)",
                applicationFee = "General: ₹100 | SC / BCA / BCB / EWS: ₹25 | Female (Haryana): ₹50 | PwD: Nil",
                location = "All Districts, Haryana",
                salary = "₹19,900 - ₹63,200 (Level 2 to Level 6)",
                briefDescription = "Haryana Staff Selection Commission (HSSC) invites online applications for 7,575 Group C posts including Clerk, Assistant, Steno, and Field Inspectors across Haryana departments.",
                hindiDescription = "हरियाणा कर्मचारी चयन आयोग (HSSC) द्वारा ग्रुप सी के 7,575 पदों (क्लर्क, सहायक, स्टेनो, और फील्ड इंस्पेक्टर) के लिए भर्ती का आधिकारिक नोटिफिकेशन जारी किया गया है। केवल सीईटी स्कोर कार्ड धारक आवेदन के पात्र हैं।",
                selectionProcess = "1. CET Score Screening\n2. Written Examination (97.5% weightage)\n3. Socio-Economic Criteria (2.5%)\n4. Document Verification & Medical Exam",
                startDate = "01 Oct 2026",
                examDate = "Dec 2026 (Tentative)",
                officialNotificationUrl = "https://hssc.gov.in",
                applyOnlineUrl = "https://onetimeregn.haryana.gov.in",
                officialWebsiteUrl = "https://hssc.gov.in",
                isFeatured = true,
                badgeType = "URGENT",
                tags = "12th,Graduate,CET,HSSC"
            ),
            JobItem(
                id = "haryana-police-constable-2026",
                title = "Haryana Police Constable (Male & Female) Recruitment 2026",
                hindiTitle = "हरियाणा पुलिस कांस्टेबल (पुरुष व महिला) भर्ती 2026",
                organization = "Haryana Police Department",
                category = NavCategory.HARYANA_JOBS.id,
                totalVacancies = "6,000 Posts (5,000 Male + 1,000 Female)",
                lastDate = "05 Nov 2026",
                qualification = "10+2 (Intermediate) with Hindi/Sanskrit up to Matric",
                ageLimit = "18 to 25 Years (+3 Years general relaxation for current cycle)",
                applicationFee = "No Application Fee (निःशुल्क)",
                location = "Haryana (State-wide Cadre)",
                salary = "₹21,700 - ₹69,100 (Pay Scale Level-3)",
                briefDescription = "Direct recruitment for 6,000 posts of Police Constable in Haryana Police. Physical Measurement Test (PMT) and Physical Screening Test (PST) will be conducted first.",
                hindiDescription = "हरियाणा पुलिस विभाग में 6,000 कांस्टेबल पदों पर सीधी भर्ती। 5000 पद पुरुष व 1000 पद महिला सिपाहियों हेतु। कोई आवेदन शुल्क नहीं लिया जाएगा।",
                selectionProcess = "1. Physical Screening Test (PST - 2.5km run)\n2. Physical Measurement Test (PMT)\n3. Knowledge Test / Written Exam\n4. Document Verification",
                startDate = "20 Sep 2026",
                examDate = "Nov-Dec 2026",
                officialNotificationUrl = "https://hssc.gov.in",
                applyOnlineUrl = "https://hssc.gov.in",
                officialWebsiteUrl = "https://haryanapolice.gov.in",
                isFeatured = true,
                badgeType = "NEW",
                tags = "12th,Police,Physical,Govt Job"
            ),
            JobItem(
                id = "hkrn-various-contract-2026",
                title = "Haryana Kaushal Rozgar Nigam (HKRN) Various Posts 2026",
                hindiTitle = "हरियाणा कौशल रोजगार निगम (HKRN) विभिन्न संविदा पद 2026",
                organization = "HKRN Haryana",
                category = NavCategory.HARYANA_JOBS.id,
                totalVacancies = "3,200+ Posts",
                lastDate = "15 Oct 2026",
                qualification = "10th / 12th / ITI / Diploma / Degree as per post",
                ageLimit = "18 to 42 Years",
                applicationFee = "₹236 (Including GST)",
                location = "All 22 Districts of Haryana",
                salary = "₹17,000 - ₹26,000 (Based on DC Rate & Nigam Level)",
                briefDescription = "Contractual recruitment for Data Entry Operators, Peon, Sweeper, Security Guard, Driver, and Office Assistant in state government boards, corporations and universities.",
                hindiDescription = "हरियाणा कौशल रोजगार निगम (HKRN) पोर्टल पर राज्य के विभिन्न विभागों में डाटा एंट्री ऑपरेटर, चपरासी, चालक एवं सहायक पदों पर भर्ती। पीपीपी (परिवार पहचान पत्र) अनिवार्य।",
                selectionProcess = "Merit based on PPP Family Income + Age + Skill Qualification + Socio-economic status",
                startDate = "25 Sep 2026",
                examDate = "Direct Merit List Allocation",
                officialNotificationUrl = "https://hkrnl.itiharyana.gov.in",
                applyOnlineUrl = "https://hkrnl.itiharyana.gov.in",
                officialWebsiteUrl = "https://hkrnl.itiharyana.gov.in",
                isFeatured = true,
                badgeType = "EXTENDED",
                tags = "10th,12th,ITI,HKRN"
            ),
            JobItem(
                id = "hpsc-assistant-prof-2026",
                title = "HPSC Assistant Professor (College Cadre) Recruitment 2026",
                hindiTitle = "एचपीएससी सहायक प्रोफेसर (कॉलेज कैडर) भर्ती 2026",
                organization = "HPSC (Haryana Public Service Commission)",
                category = NavCategory.HARYANA_JOBS.id,
                totalVacancies = "2,424 Posts",
                lastDate = "10 Nov 2026",
                qualification = "Master's Degree with 55% + UGC-NET / SLET / Ph.D",
                ageLimit = "21 to 42 Years",
                applicationFee = "General (Male): ₹1000 | Female & Reserved: ₹250",
                location = "Government Colleges across Haryana",
                salary = "₹57,700 - ₹1,82,400 (Academic Level 10)",
                briefDescription = "HPSC invites applications from eligible candidates for 2,424 Assistant Professor posts in subjects like Hindi, English, History, Mathematics, Chemistry, and Commerce.",
                hindiDescription = "हरियाणा लोक सेवा आयोग द्वारा सरकारी कॉलेजों में 2,424 असिस्टेंट प्रोफेसर पदों पर भर्ती हेतु विज्ञापन। विषयवार रिक्तियों की विस्तृत जानकारी उपलब्ध।",
                selectionProcess = "1. Screening Test (100 Marks)\n2. Subject Knowledge Test (150 Marks)\n3. Interview / Viva-Voce (12.5% weightage)",
                startDate = "06 Oct 2026",
                examDate = "Jan 2027",
                officialNotificationUrl = "https://hpsc.gov.in",
                applyOnlineUrl = "https://hpsc.gov.in",
                officialWebsiteUrl = "https://hpsc.gov.in",
                isFeatured = false,
                badgeType = "NEW",
                tags = "Post Graduate,NET,HPSC,Professor"
            ),
            JobItem(
                id = "hssc-cet-d-waitlist-2026",
                title = "Haryana CET Group D New Registrations & Waiting List 2026",
                hindiTitle = "हरियाणा ग्रुप डी नई भर्ती व प्रतीक्षा सूची 2026",
                organization = "HSSC Haryana",
                category = NavCategory.HARYANA_JOBS.id,
                totalVacancies = "4,200 Posts",
                lastDate = "31 Oct 2026",
                qualification = "10th (Matriculation) with Hindi/Sanskrit",
                ageLimit = "18 to 42 Years",
                applicationFee = "Gen: ₹100 | Haryana Reserved: ₹25",
                location = "Haryana",
                salary = "₹16,900 - ₹53,500 (Level DL)",
                briefDescription = "Notice for Group D Beldar, Mali, Peon, Field Worker posts. Candidate preferences allocation and waiting list recommendations open.",
                hindiDescription = "ग्रुप डी बेलदार, माली, चपरासी पदों हेतु आवंटन एवं नई पंजीकरण प्रक्रिया। 10वीं पास युवा कर सकते हैं आवेदन।",
                selectionProcess = "Common Eligibility Test (CET) Score Merit List + Department Preference",
                startDate = "12 Oct 2026",
                examDate = "Dec 2026",
                officialNotificationUrl = "https://hssc.gov.in",
                applyOnlineUrl = "https://onetimeregn.haryana.gov.in",
                officialWebsiteUrl = "https://hssc.gov.in",
                isFeatured = false,
                badgeType = "POPULAR",
                tags = "10th,Group D,CET"
            ),

            // --- CENTRAL GOVERNMENT JOBS ---
            JobItem(
                id = "ssc-gd-constable-2026",
                title = "SSC GD Constable (CAPFs, SSF, Assam Rifles) 2026",
                hindiTitle = "एसएससी जीडी कांस्टेबल भर्ती 2026 (39,481 पद)",
                organization = "SSC (Staff Selection Commission)",
                category = NavCategory.CENTRAL_JOBS.id,
                totalVacancies = "39,481 Posts",
                lastDate = "14 Oct 2026",
                qualification = "10th Pass (Matriculation) from recognized Board",
                ageLimit = "18 to 23 Years (OBC +3 yrs, SC/ST +5 yrs)",
                applicationFee = "General / OBC: ₹100 | SC / ST / ESM / Women: Nil",
                location = "All India (BSF, CISF, CRPF, SSB, ITBP)",
                salary = "₹21,700 - ₹69,100 (Level-3)",
                briefDescription = "Staff Selection Commission conducts national recruitment for GD Constable in Border Security Force, Central Industrial Security Force, CRPF, and Indo-Tibetan Border Police.",
                hindiDescription = "कर्मचारी चयन आयोग (SSC) द्वारा 39,481 जीडी कांस्टेबल पदों पर राष्ट्रव्यापी भर्ती। 10वीं पास पुरुष व महिला अभ्यर्थी आवेदन कर सकते हैं।",
                selectionProcess = "1. Computer Based Exam (CBE)\n2. Physical Efficiency Test (PET)\n3. Physical Standard Test (PST)\n4. Detailed Medical Examination (DME)",
                startDate = "05 Sep 2026",
                examDate = "Jan - Feb 2027",
                officialNotificationUrl = "https://ssc.gov.in",
                applyOnlineUrl = "https://ssc.gov.in",
                officialWebsiteUrl = "https://ssc.gov.in",
                isFeatured = true,
                badgeType = "POPULAR",
                tags = "10th,SSC,Defense,Central Govt"
            ),
            JobItem(
                id = "rrb-ntpc-recruitment-2026",
                title = "Railway RRB NTPC (Graduate & Undergraduate) 2026",
                hindiTitle = "रेलवे आरआरबी एनटीपीसी भर्ती 2026 (11,558 पद)",
                organization = "Railway Recruitment Board (RRB)",
                category = NavCategory.CENTRAL_JOBS.id,
                totalVacancies = "11,558 Posts",
                lastDate = "20 Oct 2026",
                qualification = "12th Pass (Undergraduate) or Any Bachelor's Degree (Graduate)",
                ageLimit = "18 to 33 Years (UG) / 18 to 36 Years (Graduate)",
                applicationFee = "General/OBC: ₹500 (₹400 refundable on CBT) | SC/ST/Female: ₹250 (Refundable)",
                location = "All Railway Zones (Northern Railway, etc.)",
                salary = "₹19,900 - ₹35,400 (Level 2 to Level 5)",
                briefDescription = "Vacancies for Goods Train Manager, Station Master, Senior Clerk cum Typist, Junior Clerk, Accounts Clerk, and Commercial Apprentice across Indian Railways.",
                hindiDescription = "भारतीय रेलवे भर्ती बोर्ड द्वारा 11,558 पदों (स्टेशन मास्टर, गुड्स ट्रेन मैनेजर, क्लर्क) पर भर्ती। उत्तर रेलवे व अन्य जोनों हेतु ऑनलाइन आवेदन शुरू।",
                selectionProcess = "1. 1st Stage CBT (Screening)\n2. 2nd Stage CBT (Merit)\n3. Typing Skill Test / CBAT (as applicable)\n4. Document Verification",
                startDate = "14 Sep 2026",
                examDate = "Dec 2026 - Jan 2027",
                officialNotificationUrl = "https://rrbcdg.gov.in",
                applyOnlineUrl = "https://rrbapply.gov.in",
                officialWebsiteUrl = "https://indianrailways.gov.in",
                isFeatured = true,
                badgeType = "NEW",
                tags = "12th,Graduate,Railway,Central Govt"
            ),
            JobItem(
                id = "ibps-po-clerk-2026",
                title = "IBPS PO / Clerk Recruitment (Public Sector Banks) 2026",
                hindiTitle = "आईबीपीएस बैंक पीओ व क्लर्क भर्ती 2026",
                organization = "IBPS (Institute of Banking Personnel Selection)",
                category = NavCategory.CENTRAL_JOBS.id,
                totalVacancies = "9,995 Posts",
                lastDate = "18 Oct 2026",
                qualification = "Bachelor's Degree in any discipline",
                ageLimit = "20 to 30 Years",
                applicationFee = "General / OBC: ₹850 | SC / ST / PWD: ₹175",
                location = "All India (Punjab National Bank, Canara Bank, Bank of Baroda)",
                salary = "₹36,000 - ₹54,000 + Allowances",
                briefDescription = "Participating public sector nationalized banks announce vacancies for Probationary Officers and Customer Service Associates.",
                hindiDescription = "राष्ट्रीयकृत बैंकों में प्रोबेशनरी ऑफिसर एवं क्लर्क के 9,995 पदों पर भर्ती। स्नातक पास उम्मीदवार आवेदन कर सकते हैं।",
                selectionProcess = "1. Preliminary Exam\n2. Main Examination\n3. Interview (For PO only)\n4. Final Allotment",
                startDate = "18 Sep 2026",
                examDate = "Nov 2026",
                officialNotificationUrl = "https://ibps.in",
                applyOnlineUrl = "https://ibps.in",
                officialWebsiteUrl = "https://ibps.in",
                isFeatured = false,
                badgeType = "NEW",
                tags = "Graduate,Banking,Central Govt"
            ),

            // --- ADMIT CARDS ---
            JobItem(
                id = "admit-card-hssc-mains-2026",
                title = "HSSC CET Group C Mains Exam Admit Card 2026",
                hindiTitle = "एचएसएससी ग्रुप सी मेन्स परीक्षा एडमिट कार्ड 2026",
                organization = "HSSC Haryana",
                category = NavCategory.ADMIT_CARD.id,
                totalVacancies = "Direct Hall Ticket",
                lastDate = "Exam Date: 22 Oct 2026",
                qualification = "Shortlisted CET Candidates",
                ageLimit = "As per application",
                applicationFee = "No Fee to Download Admit Card",
                location = "Exam Centers in Haryana",
                salary = "Hall Ticket Download Notice",
                briefDescription = "Download Call Letter & Exam Center details for HSSC Group C Mains written exam. Candidates must carry colored printout and valid photo ID.",
                hindiDescription = "एचएसएससी ग्रुप सी मुख्य लिखित परीक्षा के एडमिट कार्ड जारी। अपना रजिस्ट्रेशन नंबर व जन्मतिथि डालकर प्रवेश पत्र डाउनलोड करें।",
                selectionProcess = "Direct Download through Candidate Login",
                startDate = "Available Now",
                examDate = "22-24 Oct 2026",
                officialNotificationUrl = "https://hssc.gov.in",
                applyOnlineUrl = "https://onetimeregn.haryana.gov.in",
                officialWebsiteUrl = "https://hssc.gov.in",
                isFeatured = true,
                badgeType = "URGENT",
                tags = "Admit Card,HSSC,CET"
            ),
            JobItem(
                id = "admit-card-police-pst-2026",
                title = "Haryana Police Physical Test (PST / PMT) Admit Card 2026",
                hindiTitle = "हरियाणा पुलिस फिजिकल टेस्ट (PST/PMT) एडमिट कार्ड 2026",
                organization = "Haryana Police / HSSC",
                category = NavCategory.ADMIT_CARD.id,
                totalVacancies = "6,000 Posts Selection",
                lastDate = "PST Run: 29 Oct 2026",
                qualification = "Eligible Applicants",
                ageLimit = "18 to 25 Years",
                applicationFee = "Free Download",
                location = "Panchkula, Madhuban, Rohtak Grounds",
                salary = "Physical Screening Schedule",
                briefDescription = "Physical Screening Test schedule and batch timing call letters released for male and female constable recruitment.",
                hindiDescription = "हरियाणा पुलिस कांस्टेबल फिजिकल स्क्रीनिंग टेस्ट (दौड़ एवं नाप-तौल) का एडमिट कार्ड जारी। पंचकूला व मधुबन केंद्रों पर होगी दौड़।",
                selectionProcess = "Barcoded Hall Ticket Check + RFID Chip Race Timing",
                startDate = "Available Now",
                examDate = "29 Oct to 12 Nov 2026",
                officialNotificationUrl = "https://hssc.gov.in",
                applyOnlineUrl = "https://hssc.gov.in",
                officialWebsiteUrl = "https://haryanapolice.gov.in",
                isFeatured = false,
                badgeType = "NEW",
                tags = "Admit Card,Police,Physical"
            ),
            JobItem(
                id = "admit-card-ssc-cgl-2026",
                title = "SSC CGL Tier 2 Examination Admit Card & City Slip 2026",
                hindiTitle = "एसएससी सीजीएल टियर 2 एडमिट कार्ड व एग्जाम सिटी स्लिप 2026",
                organization = "Staff Selection Commission (NWR / NR)",
                category = NavCategory.ADMIT_CARD.id,
                totalVacancies = "17,727 Posts",
                lastDate = "Exam: 18 Nov 2026",
                qualification = "Tier 1 Qualified Candidates",
                ageLimit = "18 to 32 Years",
                applicationFee = "No Fee",
                location = "Gurugram, Ambala, Chandigarh, Delhi NCR",
                salary = "Admit Card Notice",
                briefDescription = "Download SSC Combined Graduate Level Tier 2 Hall Ticket. Exam city intimation slip is now active on regional portal.",
                hindiDescription = "एसएससी सीजीएल टियर-2 परीक्षा की एग्जाम सिटी व एडमिट कार्ड जारी। उम्मीदवार अपनी रजिस्ट्रेशन आईडी से स्टेटस चेक करें।",
                selectionProcess = "CBT Tier 2 Examination",
                startDate = "Available Now",
                examDate = "18-20 Nov 2026",
                officialNotificationUrl = "https://sscnwr.org",
                applyOnlineUrl = "https://ssc.gov.in",
                officialWebsiteUrl = "https://ssc.gov.in",
                isFeatured = false,
                badgeType = "NEW",
                tags = "Admit Card,SSC,Graduate"
            ),

            // --- RESULTS ---
            JobItem(
                id = "result-hssc-group-d-final-2026",
                title = "HSSC Group D Final Selection Result & Cutoff Marks 2026",
                hindiTitle = "एचएसएससी ग्रुप डी अंतिम चयन परिणाम व कटऑफ 2026",
                organization = "HSSC Haryana",
                category = NavCategory.RESULT.id,
                totalVacancies = "13,657 Posts",
                lastDate = "Result Declared",
                qualification = "Matriculation Candidates",
                ageLimit = "18 to 42 Years",
                applicationFee = "No Fee",
                location = "Haryana",
                salary = "Merit PDF Available",
                briefDescription = "HSSC has declared the final merit list and category-wise cut-off marks for 13,657 Group D posts. Download PDF and check roll number.",
                hindiDescription = "हरियाणा कर्मचारी चयन आयोग ने ग्रुप डी भर्ती का फाइनल रिजल्ट और वर्गवार कटऑफ अंक घोषित किए। चयनित उम्मीदवारों की सूची जारी।",
                selectionProcess = "Merit Allocation in State Govt Departments",
                startDate = "Result Live",
                examDate = "Exam Concluded",
                officialNotificationUrl = "https://hssc.gov.in",
                applyOnlineUrl = "https://hssc.gov.in",
                officialWebsiteUrl = "https://hssc.gov.in",
                isFeatured = true,
                badgeType = "NEW",
                tags = "Result,Cutoff,HSSC"
            ),
            JobItem(
                id = "result-hbse-class-12-reappear-2026",
                title = "HBSE Haryana Board 10th & 12th Re-Appear / Mercy Chance Result 2026",
                hindiTitle = "हरियाणा बोर्ड 10वीं व 12वीं सप्लीमेंट्री / री-अपीयर रिजल्ट 2026",
                organization = "Board of School Education Haryana (BSEH)",
                category = NavCategory.RESULT.id,
                totalVacancies = "Academic Result",
                lastDate = "Declared Online",
                qualification = "BSEH Students",
                ageLimit = "N/A",
                applicationFee = "Free Result Check",
                location = "Bhiwani, Haryana",
                salary = "Provisional Marksheet",
                briefDescription = "Board of School Education Haryana (BSEH) Bhiwani has announced the September 2026 Senior Secondary and Secondary exam marks.",
                hindiDescription = "हरियाणा विद्यालय शिक्षा बोर्ड (HBSE) भिवानी द्वारा सेकेंडरी एवं सीनियर सेकेंडरी पूरक परीक्षा का परिणाम घोषित।",
                selectionProcess = "Direct Roll Number / Name search on bseh.org.in",
                startDate = "Live Now",
                examDate = "September 2026",
                officialNotificationUrl = "https://bseh.org.in",
                applyOnlineUrl = "https://bseh.org.in",
                officialWebsiteUrl = "https://bseh.org.in",
                isFeatured = false,
                badgeType = "NEW",
                tags = "Result,HBSE,10th,12th"
            ),
            JobItem(
                id = "result-htet-revised-merit-2026",
                title = "HTET Haryana Teacher Eligibility Test Scorecard & Certificate 2026",
                hindiTitle = "एचटेट (HTET) संशोधित परिणाम व पात्रता प्रमाण पत्र 2026",
                organization = "BSEH Bhiwani",
                category = NavCategory.RESULT.id,
                totalVacancies = "PRT, TGT & PGT Levels",
                lastDate = "Download Open",
                qualification = "D.El.Ed / B.Ed / Post Graduate",
                ageLimit = "18 to 42 Years",
                applicationFee = "No Fee",
                location = "Haryana",
                salary = "Lifetime Validity Certificate",
                briefDescription = "Download verified HTET Level 1 (PRT), Level 2 (TGT) and Level 3 (PGT) certificate. Digital copy now available for recruitment verification.",
                hindiDescription = "हरियाणा शिक्षक पात्रता परीक्षा (HTET) का अधिकृत स्कोरकार्ड व पात्रता प्रमाण पत्र जारी। जीवन भर के लिए मान्य।",
                selectionProcess = "Qualifying Certificate for Haryana Govt Teacher Jobs",
                startDate = "Active",
                examDate = "Qualifying Exam",
                officialNotificationUrl = "https://bseh.org.in",
                applyOnlineUrl = "https://bseh.org.in",
                officialWebsiteUrl = "https://bseh.org.in",
                isFeatured = false,
                badgeType = "POPULAR",
                tags = "Result,HTET,Teacher"
            ),

            // --- ADMISSIONS ---
            JobItem(
                id = "admission-dhe-haryana-ug-pg-2026",
                title = "DHE Haryana Higher Education UG & PG College Admission 2026",
                hindiTitle = "हरियाणा उच्चतर शिक्षा कॉलेज यूजी व पीजी एडमिशन 2026",
                organization = "Department of Higher Education (DHE) Haryana",
                category = NavCategory.ADMISSION.id,
                totalVacancies = "1,50,000+ College Seats",
                lastDate = "16 Oct 2026",
                qualification = "12th Pass (UG) / Bachelor's Degree (PG)",
                ageLimit = "No Upper Age Limit",
                applicationFee = "Registration Fee: ₹100",
                location = "All Govt & Aided Colleges across Haryana",
                salary = "BA / B.Com / B.Sc / BCA / BBA / MA / M.Sc",
                briefDescription = "Centralized online admission portal for undergraduate and postgraduate courses in all government, aided, and self-financing degree colleges in Haryana.",
                hindiDescription = "हरियाणा के सभी राजकीय व निजी महाविद्यालयों में स्नातक (UG) व स्नातकोत्तर (PG) कक्षाओं में दाखिले के लिए सेंट्रलाइज्ड ऑनलाइन काउंसलिंग व मेरिट सूची।",
                selectionProcess = "Class 12th / Graduation Percentage Merit List + Reservation Roster",
                startDate = "20 Sep 2026",
                examDate = "Counseling Spot Round: 18-20 Oct",
                officialNotificationUrl = "https://dheadmission.nic.in",
                applyOnlineUrl = "https://dheadmission.nic.in",
                officialWebsiteUrl = "https://highereduhry.ac.in",
                isFeatured = true,
                badgeType = "NEW",
                tags = "Admission,College,12th,Degree"
            ),
            JobItem(
                id = "admission-kuk-bed-entrance-2026",
                title = "Kurukshetra University (KUK) B.Ed / M.Ed Regular Admission 2026",
                hindiTitle = "कुरुक्षेत्र विश्वविद्यालय (KUK) बीएड व एमएड प्रवेश 2026",
                organization = "Kurukshetra University, Kurukshetra",
                category = NavCategory.ADMISSION.id,
                totalVacancies = "Affiliated Colleges Seats",
                lastDate = "24 Oct 2026",
                qualification = "Graduation / Post Graduation with min 50% marks",
                ageLimit = "No Bar",
                applicationFee = "General: ₹1000 | SC/BC of Haryana: ₹250",
                location = "Kurukshetra, Haryana",
                salary = "Two-Year NCTE Approved B.Ed Program",
                briefDescription = "Online admission and counseling for 2-year B.Ed and M.Ed regular courses in university campus and affiliated colleges in Haryana.",
                hindiDescription = "कुरुक्षेत्र विश्वविद्यालय द्वारा मान्यता प्राप्त बीएड कॉलेजों में नियमित सत्र 2026-28 के लिए ऑनलाइन रजिस्ट्रेशन व च्वाइस फिलिंग।",
                selectionProcess = "Academic Merit Basis Counseling",
                startDate = "02 Oct 2026",
                examDate = "Allotment List: 28 Oct 2026",
                officialNotificationUrl = "https://kuk.ac.in",
                applyOnlineUrl = "https://iums.kuk.ac.in",
                officialWebsiteUrl = "https://kuk.ac.in",
                isFeatured = false,
                badgeType = "POPULAR",
                tags = "Admission,KUK,B.Ed,Teacher"
            ),
            JobItem(
                id = "admission-mdu-pg-entrance-2026",
                title = "MDU Rohtak PG & Law Entrance Admissions 2026",
                hindiTitle = "एमडीयू रोहतक पीजी व एलएलबी प्रवेश काउंसलिंग 2026",
                organization = "Maharshi Dayanand University (MDU)",
                category = NavCategory.ADMISSION.id,
                totalVacancies = "Campus & PG Regional Center Seats",
                lastDate = "22 Oct 2026",
                qualification = "Bachelor's Degree in relevant discipline",
                ageLimit = "As per university statute",
                applicationFee = "General: ₹1000 | Reserved: ₹250",
                location = "Rohtak, Haryana",
                salary = "MA / M.Sc / M.Com / MBA / LLB Courses",
                briefDescription = "MDU Rohtak announces spot physical counseling for vacant seats in Master's and Professional degrees for the current academic session.",
                hindiDescription = "महर्षि दयानंद विश्वविद्यालय (रोहतक) द्वारा पीजी पाठ्यक्रमों में रिक्त सीटों हेतु फिजिकल काउंसलिंग का शेड्यूल जारी।",
                selectionProcess = "Entrance Score + Physical Attendance on Campus",
                startDate = "05 Oct 2026",
                examDate = "Spot Counseling: 26 Oct 2026",
                officialNotificationUrl = "https://mdu.ac.in",
                applyOnlineUrl = "https://mdu.ac.in",
                officialWebsiteUrl = "https://mdu.ac.in",
                isFeatured = false,
                badgeType = "NEW",
                tags = "Admission,MDU,Rohtak,University"
            ),

            // --- ANSWER KEY ---
            JobItem(
                id = "answer-key-htet-official-2026",
                title = "HTET Official Question Paper & Answer Key with Objection Link 2026",
                hindiTitle = "एचटेट आधिकारिक उत्तर कुंजी व आपत्ति दर्ज लिंक 2026",
                organization = "Board of School Education Haryana",
                category = NavCategory.ANSWER_KEY.id,
                totalVacancies = "All Levels (PRT, TGT, PGT)",
                lastDate = "Objection Window: 19 Oct 2026",
                qualification = "Exam Attendees",
                ageLimit = "N/A",
                applicationFee = "₹1000 per question challenged (Refundable if valid)",
                location = "Bhiwani / Haryana",
                salary = "Master Question Booklet & Key",
                briefDescription = "Official provisional answer keys for all sets (A, B, C, D) of HTET Level 1, 2, and 3 released. Candidates can raise objections online.",
                hindiDescription = "हरियाणा शिक्षक पात्रता परीक्षा (HTET) के सभी सेटों की उत्तर कुंजी जारी। उत्तर पर आपत्ति दर्ज करने का लिंक सक्रिय।",
                selectionProcess = "Subject Expert Committee Review -> Final Answer Key",
                startDate = "Live Now",
                examDate = "Exam Concluded",
                officialNotificationUrl = "https://bseh.org.in",
                applyOnlineUrl = "https://bseh.org.in",
                officialWebsiteUrl = "https://bseh.org.in",
                isFeatured = true,
                badgeType = "URGENT",
                tags = "Answer Key,HTET,BSEH"
            ),
            JobItem(
                id = "answer-key-ssc-cgl-tier1-2026",
                title = "SSC CGL Tier 1 Tentative Answer Key & Response Sheet 2026",
                hindiTitle = "एसएससी सीजीएल टियर 1 आंसर की व रिस्पॉन्स शीट 2026",
                organization = "Staff Selection Commission (SSC)",
                category = NavCategory.ANSWER_KEY.id,
                totalVacancies = "All Shifts Key",
                lastDate = "Download Window: 17 Oct 2026",
                qualification = "Tier 1 Exam Appeared Candidates",
                ageLimit = "N/A",
                applicationFee = "₹100 per objection challenge",
                location = "All India",
                salary = "Candidate Response Sheet",
                briefDescription = "Staff Selection Commission has uploaded the candidate's Response Sheets along with the Tentative Answer Keys of CGL Examination 2026.",
                hindiDescription = "कर्मचारी चयन आयोग (SSC) द्वारा सीजीएल टियर 1 परीक्षा की आंसर की व रिस्पॉन्स शीट जारी। अपना रोल नंबर व पासवर्ड दर्ज कर चेक करें।",
                selectionProcess = "Online Challenge Verification",
                startDate = "Active",
                examDate = "Sep-Oct 2026",
                officialNotificationUrl = "https://ssc.gov.in",
                applyOnlineUrl = "https://ssc.gov.in",
                officialWebsiteUrl = "https://ssc.gov.in",
                isFeatured = false,
                badgeType = "NEW",
                tags = "Answer Key,SSC,CGL"
            )
        )
    }
}
