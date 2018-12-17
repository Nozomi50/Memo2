package com.example.owner.memo2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class MemoAdapter(data: OrderedRealmCollection<Memo>?) : RealmBaseAdapter<Memo>(data) {

    inner class ViewHolder(cell:View){
        val title=cell.findViewById<TextView>(android.R.id.text1)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val viewHolder:ViewHolder

        when(convertView){
            null->{
                val inflater=LayoutInflater.from(parent?.context)
                view=inflater.inflate(android.R.layout.simple_list_item_1,parent,false)
                viewHolder=ViewHolder(view)
                view.tag=viewHolder
            }
            else->{
                view=convertView
                viewHolder=view.tag as ViewHolder
            }
        }

        adapterData?.run {
            val memo=get(position)
            viewHolder.title.text=memo.title
        }
        return view
    }
}