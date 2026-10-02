package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.JobItem
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM jobs ORDER BY updateTimestamp DESC")
    fun getAllJobsFlow(): Flow<List<JobItem>>

    @Query("SELECT * FROM jobs WHERE category = :category ORDER BY updateTimestamp DESC")
    fun getJobsByCategoryFlow(category: String): Flow<List<JobItem>>

    @Query("SELECT * FROM jobs WHERE isFeatured = 1 ORDER BY updateTimestamp DESC")
    fun getFeaturedJobsFlow(): Flow<List<JobItem>>

    @Query("SELECT * FROM jobs WHERE isBookmarked = 1 ORDER BY updateTimestamp DESC")
    fun getBookmarkedJobsFlow(): Flow<List<JobItem>>

    @Query("SELECT * FROM jobs WHERE id = :id LIMIT 1")
    fun getJobByIdFlow(id: String): Flow<JobItem?>

    @Query("SELECT * FROM jobs WHERE title LIKE '%' || :query || '%' OR hindiTitle LIKE '%' || :query || '%' OR organization LIKE '%' || :query || '%' OR qualification LIKE '%' || :query || '%'")
    fun searchJobsFlow(query: String): Flow<List<JobItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobs(jobs: List<JobItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobItem)

    @Query("UPDATE jobs SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: String, isBookmarked: Boolean)

    @Query("SELECT COUNT(*) FROM jobs")
    suspend fun getJobCount(): Int

    @Query("DELETE FROM jobs")
    suspend fun deleteAll()
}
