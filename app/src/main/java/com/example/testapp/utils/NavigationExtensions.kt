package com.example.testapp.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.testapp.R

const val defaultContainerId = R.id.container

fun FragmentManager.backTo(fragment: Class<*>?) {
    if (fragment == null) {
        popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    } else {
        popBackStack(fragment.canonicalName, 0)
    }
}

fun FragmentManager.navigateTo(
    fragment: Class<out Fragment>,
    args: Bundle? = null,
    setupFragmentTransaction: ((FragmentTransaction) -> Unit)? = null,
    containerId: Int = defaultContainerId
) {
    val fragmentTransaction = beginTransaction()
    setupFragmentTransaction?.invoke(fragmentTransaction)
    fragmentTransaction
        .replace(containerId, fragment, args)
        .addToBackStack(fragment.canonicalName)
        .setReorderingAllowed(true)
        .commit()
}

fun FragmentManager.navigateTo(
    fragment: Fragment,
    setupFragmentTransaction: ((FragmentTransaction) -> Unit)? = null,
    containerId: Int = defaultContainerId
) {
    val fragmentTransaction = beginTransaction()
    setupFragmentTransaction?.invoke(fragmentTransaction)
    fragmentTransaction
        .replace(containerId, fragment)
        .addToBackStack(fragment.javaClass.canonicalName)
        .setReorderingAllowed(true)
        .commit()
}

fun FragmentManager.replace(
    fragment: Class<out Fragment>,
    args: Bundle? = null,
    setupFragmentTransaction: ((FragmentTransaction) -> Unit)? = null,
    containerId: Int = defaultContainerId
) {
    if (backStackEntryCount > 0) {
        popBackStack()
        val fragmentTransaction = beginTransaction()
        setupFragmentTransaction?.invoke(fragmentTransaction)
        fragmentTransaction
            .replace(containerId, fragment, args)
            .addToBackStack(fragment.canonicalName)
            .setReorderingAllowed(true)
            .commit()
    } else {
        val fragmentTransaction = beginTransaction()
        setupFragmentTransaction?.invoke(fragmentTransaction)
        fragmentTransaction
            .replace(containerId, fragment, args)
            .setReorderingAllowed(true)
            .commit()
    }
}

fun FragmentManager.newRootScreen(
    fragment: Class<out Fragment>,
    args: Bundle? = null,
    setupFragmentTransaction: ((FragmentTransaction) -> Unit)? = null,
    containerId: Int = defaultContainerId
) {
    backTo(null)
    replace(fragment, args, setupFragmentTransaction, containerId)
}