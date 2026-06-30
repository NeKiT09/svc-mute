package org.zawarka.svcMuteCore.utils

import java.util.*
import java.util.function.Predicate

fun Collection<String>.startWith(arg: String): List<String> {
    return this.stream().map { args: String ->
        if (args.lowercase().startsWith(arg.lowercase())) return@map args
        ""
    }.filter(Predicate.not(Predicate { obj: String -> obj.isEmpty() })).toList()
}

