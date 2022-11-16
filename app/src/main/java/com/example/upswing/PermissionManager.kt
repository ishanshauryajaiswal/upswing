package com.example.upswing

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object PermissionManager {

    fun checkAllPermissions(listOfPermissions: List<String>, context: Context): Boolean{
        listOfPermissions.forEach {
            if(PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(context, it))
                return false
        }
        return true
    }

    fun askPermission(listOfPermissions: List<String>, context: Context){

    }
}