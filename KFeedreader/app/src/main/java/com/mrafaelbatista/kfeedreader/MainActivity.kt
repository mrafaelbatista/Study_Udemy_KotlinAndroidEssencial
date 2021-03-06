package com.mrafaelbatista.kfeedreader

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.pkmmte.pkrss.Article
import com.pkmmte.pkrss.Callback
import com.pkmmte.pkrss.PkRSS

class MainActivity : AppCompatActivity(), Callback {

    lateinit var listView: RecyclerView
    lateinit var adapter: RecyclerView.Adapter<ItemAdapter.ItemViewHolder>

    //Este é um recurso do Kotlin arrayListOf
    val listItens = arrayListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = LinearLayoutManager(this)
        listView = findViewById<RecyclerView>(R.id.rv) as RecyclerView
        listView.layoutManager = layout

        adapter = ItemAdapter(listItens, this)
        listView.adapter = adapter

        PkRSS.with(this).load("https://rss.tecmundo.com.br/feed").callback(this).async()
    }

    override fun onLoadFailed() {

    }

    override fun onPreload() {  }

    override fun onLoaded(newArticles: MutableList<Article>?) {


        listItens.clear()
        //O mapeamento de uma lista para outra é um recurso do Kotlin
        newArticles?.mapTo(listItens) {
            Item(it.title, it.author, it.date, it.source, it.enclosure.url)
        }

        //adapter = ItemAdapter(listItens, this)
        adapter.notifyDataSetChanged()
    }

    //o data class também é um recurso do Kotlin
    data class Item(val titulo: String, val autor: String, val data: Long, val link: Uri, val imagem: String)
}
