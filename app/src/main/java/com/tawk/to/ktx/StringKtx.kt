package com.tawk.to.ktx

import android.text.Editable
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern.compile

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
