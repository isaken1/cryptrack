package br.imd.cryptrack.Utils

import android.content.Context
import android.widget.ImageView
import br.imd.cryptrack.model.Coin
import com.bumptech.glide.Glide
import java.io.File

class Image {

    fun loadImageByInternetUrl(context: Context, coin: Coin, imageView: ImageView) {
        Glide
            .with(context)
            .load(coin.imageUrl)
            .into(imageView)
    }

    fun loadImageByFile(context: Context, coin: Coin, imageView: ImageView){
        var file = File(coin.imagePath)
        Glide.with(context).load(file).into(imageView)
    }
}