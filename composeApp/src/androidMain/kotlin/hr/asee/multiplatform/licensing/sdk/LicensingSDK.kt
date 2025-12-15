package hr.asee.multiplatform.licensing.sdk

import android.app.Application
import hr.asee.multiplatform.licensing.sdk.app.initLogger
import hr.asee.multiplatform.licensing.sdk.di.initKoin
import org.koin.android.ext.koin.androidContext

class LicensingSDK : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
        initKoin {
            androidContext(this@LicensingSDK)
        }
    }
}
