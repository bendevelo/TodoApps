package com.s.todo.Adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Video.Thumbnails
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isGone
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerControlView
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.RecyclerView
import com.s.todo.R
import com.s.todo.databinding.ListImageBinding
import com.s.todo.model.task
import java.net.URI

class AdapterPhotoList() : RecyclerView.Adapter<AdapterPhotoList.CustomHolder>() {

    private val photolist = mutableListOf<task>()
    private lateinit var mContext: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomHolder {
        mContext = parent.context
        val inflater = LayoutInflater.from(mContext)
        val binding = ListImageBinding.inflate(inflater, parent, false)
        return CustomHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomHolder, position: Int) {
        holder.bind(photolist[position])

    }

    override fun getItemCount(): Int = photolist.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<task>) {
        photolist.clear()
        photolist.addAll(list)
        notifyDataSetChanged()
    }

    inner class CustomHolder(private val binding: ListImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @OptIn(UnstableApi::class)
        fun bind(photolist: task) {
            binding.apply {
                photolist.also {

                    if (photolist.type == "img") {

                        binding.desc.isGone = true

                        val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(
                            mContext.getContentResolver(),
                            Uri.parse(photolist.file)
                        )

                        binding.image.setImageBitmap(bitmap)

                        binding.image.setOnClickListener({

                            val dialog = Dialog(mContext)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setCancelable(true)
                            dialog.setContentView(R.layout.imagezoom)
                            Log.d("fileuri", photolist.file)

                            val img = dialog.findViewById(R.id.images) as ImageView

                            img.setImageBitmap(bitmap)

                            dialog.show()
                        })

                    } else {

                        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                        val cursor = mContext.contentResolver.query(Uri.parse(photolist.file), filePathColumn, null, null, null)
                        cursor?.moveToFirst()

                        val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                        val picturePath = cursor?.getString(columnIndex!!)
                        cursor?.close()

                        val bitmap = ThumbnailUtils.createVideoThumbnail(picturePath.toString(), MediaStore.Video.Thumbnails.MICRO_KIND)
                        binding.image.setImageBitmap(bitmap)
                        binding.image.setOnClickListener({
                            val player = ExoPlayer.Builder(mContext).build()

                            val dialog = Dialog(mContext)
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            dialog.setCancelable(true)
                            dialog.setContentView(R.layout.videozoom)
                            val img = dialog.findViewById(R.id.exo) as PlayerView
                            img.player = player

                            val mediaItem = MediaItem.fromUri(Uri.parse(photolist.file))
                            player.setMediaItem(mediaItem)
                            player.prepare()
                            player.play()

                            Log.d("fileuri", photolist.file)

                            dialog.show()
                        })

                    }

                }
            }
        }
    }

}