package com.ausichenko.github.data.network.models

import com.google.gson.annotations.SerializedName

data class GitOwner(
        var login: String,
        var id: Long,
        @SerializedName("node_id")
        var nodeId: String,
        @SerializedName("avatar_url")
        var avatarUrl: String,
        @SerializedName("avatar_id")
        var avatarId: String,
        var url: String,
        @SerializedName("received_events_url")
        var receivedEventsUrl: String,
        var type: String
)