package com.marknjunge.musicapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_album_song.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = BaseRecyclerViewAdapter<Song>(R.layout.item_album_song) { song ->
            val drawable = ContextCompat.getDrawable(this@MainActivity, song.cover)
            imgSongCover.setImageDrawable(drawable)
            tvSongTitle.text = song.title
            tvSongArtists.text = song.artists
            tvSongDuration.text = song.duration
        }

        rvSongs.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvSongs.adapter = adapter

        adapter.setItems(getSongs(), false)

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                when (motionLayout.currentState) {
                    motionLayout.startState -> tvAlbumTitle.gravity = Gravity.CENTER
                    motionLayout.endState -> tvAlbumTitle.gravity = Gravity.START
                }
            }
        })

        motionLayout.viewTreeObserver.addOnScrollChangedListener {
            // There isn't any accurate way to listen to progress changes
            if (motionLayout.progress > 0.5) {
                tvAlbumTitle.gravity = Gravity.START
            } else {
                tvAlbumTitle.gravity = Gravity.CENTER
            }
        }
    }

    private fun getSongs() = listOf(
        Song("Mars Base", "Epic Mountain", "8:32", R.drawable.mars_base),
        Song("Trust Kurzgesagt", "Epic Mountain", "6:05", R.drawable.trust_kurzgesagt),
        Song("Consciousness", "Epic Mountain", "7:46", R.drawable.consciousness),
        Song("All The Bombs", "Epic Mountain", "6:06", R.drawable.all_the_bombs),
        Song("Strange Stars", "Epic Mountain", "6:40", R.drawable.strange_stars),
        Song("Vaccines", "Epic Mountain", "9:48", R.drawable.vaccines),
        Song("Does Meat Kill", "Epic Mountain", "7:30", R.drawable.does_meat_kill)
    )
}
