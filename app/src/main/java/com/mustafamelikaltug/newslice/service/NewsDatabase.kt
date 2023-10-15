package com.mustafamelikaltug.newslice.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mustafamelikaltug.newslice.model.Article

@Database(entities = [Article::class], version = 1)
abstract class NewsDatabase :RoomDatabase() {

    abstract fun countryDao() : NewsDAO

    //Singleton mantığı

    //her  yerden ulaşabilmek için companion objecti kullanıyoruz. Static bir şekilde
    // değişken oluşturup sınıfın kapsamı dışında her yerden ulaşmaya olanak tanıyor.

    companion object {
        //Volatile -> volatile olarak tanımlanan property ya da değişken
        //  propertyi farklı threadlere de görünür hale getiriyor.
        @Volatile private var instance : NewsDatabase? = null

        //invoke-> ateşlemek, başlatmak manasına gelen invoke instance var mı yok mu onu kontrol eder.
        // Eğer yoksa oluşturur varsa oluşturulmuşu döndürür.

        //synchronized-> diğer threadlerin gelip bu database kullanmasına müsaade etmemek için kullanılır.
        //Aynı anda tek bir thread işlem yapabilir. Bunun amacı olası bir karmaşıklıktan
        // conflicten kurtulmaktır. Ancak aktif olarak
        // database'i kullanan threadin işi bitince diğer threadin kullanılmasına müsaade eder.

        //lock-> Seknonizenin kitlenip kitlenmeyeceğini kontrol eden değişken
        operator fun invoke(context: Context) = instance?: synchronized(lock = Any()){
            instance ?: makeDatabase(context).also {
                instance = it  //instance'ı database'e eşitle.
            }
        }

        // Application un kendi contextini kullanamk burada güvenlik için eğer activity veya fragmentin
        //contextini kullanırsak destroy olduklarında problem yaşanabilir.
        private fun makeDatabase(context : Context) = Room.databaseBuilder(
            context.applicationContext,NewsDatabase::class.java,"countrydatabase"
        ).build()

    }



}