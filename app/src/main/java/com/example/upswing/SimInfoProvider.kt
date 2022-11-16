package com.example.upswing

import android.annotation.SuppressLint
import android.content.Context
import android.telephony.SubscriptionInfo
import android.telephony.SubscriptionManager

object SimInfoProvider{



    @SuppressLint("MissingPermission")
    fun getActiveSimCount(context: Context): Int{
        val subscriptionManager = context.getSystemService(SubscriptionManager::class.java) as SubscriptionManager
        return subscriptionManager.activeSubscriptionInfoCount
    }

    @SuppressLint("MissingPermission")
    fun getSimInfo(context: Context): List<SubscriptionInfo>? {
        val subscriptionManager = context.getSystemService(SubscriptionManager::class.java) as SubscriptionManager
        return subscriptionManager.activeSubscriptionInfoList
    }

}
