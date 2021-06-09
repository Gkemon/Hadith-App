package com.gk.emon.hadith.appFailure

import com.gk.emon.core_features.exceptions.Failure

class AppFeatureFailures {
    object CollectionListNotAvailable : Failure.FeatureFailure()
    object BookListNotAvailable : Failure.FeatureFailure()
    object HadithListNotAvailable : Failure.FeatureFailure()
    object HadithNotAvailable : Failure.FeatureFailure()
    object HadithBookListAvailable : Failure.FeatureFailure()

}