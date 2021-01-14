package com.sbaiardi.onion.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Response(
   public val percentages: List<Percentages>
)



@Entity(tableName = "percentages")
data class Percentages(

    @PrimaryKey
    @SerializedName("data")
    @ColumnInfo(name = "data")
    val data: String,


    @SerializedName("n_positivi")
    @ColumnInfo(name = "n_positivi")
    val n_positivi: Int,

    @SerializedName("n_tamponi")
    @ColumnInfo(name = "n_tamponi")
    val n_tamponi: Int,

    @SerializedName("positive_percentage")
    @ColumnInfo(name = "positive_percentage")
    val positive_percentage: Double

)




