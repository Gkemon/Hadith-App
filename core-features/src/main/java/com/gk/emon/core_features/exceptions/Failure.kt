package com.gk.emon.core_features.exceptions

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    abstract class FeatureFailure : Failure()
}