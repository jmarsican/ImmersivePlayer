package com.javiermarsicano.immersiveplayer

import android.net.Uri
import android.os.Bundle
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.javiermarsicano.immersiveplayer.databinding.ActivityMainBinding

const val videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4"

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private var player: SimpleExoPlayer? = null
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerView = binding.playerView
    }

    override fun onResume() {
        super.onResume()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        if (player == null) {
            player = SimpleExoPlayer.Builder(this).build()
        }

        val uri = Uri.parse(videoUrl)
        val mediaSource = buildMediaSource(uri)
        player?.setMediaSource(mediaSource)
        player?.prepare()

        playerView.player = player
        playerView.onResume()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        val mediaItem = MediaItem.Builder()
            .setUri(uri)
            .build()
        val dataSourceFactory = DefaultDataSourceFactory(this, "immersive-player")
        // Create a media source using the supplied URI
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(mediaItem)
    }

    private fun releasePlayer() {
        playerView.onPause()
        player?.release()
        player = null
    }
}