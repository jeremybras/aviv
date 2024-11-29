package fr.aviv.testutils

import org.mockito.Mockito.lenient
import org.mockito.stubbing.OngoingStubbing

fun <T> lenientGiven(methodCall: T): OngoingStubbing<T> = lenient().`when`(methodCall)
fun <T> OngoingStubbing<T>.willReturn(value: T): OngoingStubbing<T> = this.thenReturn(value)
