package com.example.stree20.data.remote.response.channelresponse

data class Channel(
    val created: Int,
    val creator: String,
    val id: String,
    val is_archived: Boolean,
    val is_channel: Boolean,
    val is_ext_shared: Boolean,
    val is_general: Boolean,
    val is_group: Boolean,
    val is_im: Boolean,
    val is_member: Boolean,
    val is_mpim: Boolean,
    val is_org_shared: Boolean,
    val is_pending_ext_shared: Boolean,
    val is_private: Boolean,
    val is_shared: Boolean,
    val name: String,
    val name_normalized: String,
    val num_members: Int,
    val pending_shared: List<Any>,
    val previous_names: List<Any>,
    val purpose: Purpose,
    val topic: Topic,
    val unlinked: Int
)