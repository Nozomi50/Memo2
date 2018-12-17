package com.example.owner.memo2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_memo_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class MemoEditActivity : AppCompatActivity() {
    private lateinit var realm:Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo_edit)
        realm= Realm.getDefaultInstance()

        val memoId=intent?.getLongExtra("memo_id",-1L)
        if(memoId!=-1L){
            val memo=realm.where<Memo>().equalTo("id",memoId).findFirst()
            titleEdit.setText(memo?.title)
            detailEdit.setText(memo?.detail)
        }

        if (memoId!=-1L){
            val memo=realm.where(Memo::class.java).equalTo("id",memoId).findFirst()
            titleEdit.setText(memo?.title)
            detailEdit.setText(memo?.detail)
            delete.visibility=View.VISIBLE
        }else{
            delete.visibility=View.INVISIBLE
        }

        save.setOnClickListener {
            when(memoId) {
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<Memo>().max("id")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val Memo = realm.createObject<Memo>(nextId)
                        Memo.title = titleEdit.text.toString()
                        Memo.detail = detailEdit.text.toString()
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else->{
                    realm.executeTransaction {
                        val memo=realm.where<Memo>().equalTo("id",memoId).findFirst()
                        memo?.title=titleEdit.text.toString()
                        memo?.detail=detailEdit.text.toString()
                    }
                    alert ("修正しました"){
                        yesButton { finish() }
                    }.show()
                }
            }
        }

        delete.setOnClickListener {
            realm.executeTransaction {
                realm.where<Memo>().equalTo("id",memoId)?.findFirst()?.deleteFromRealm()
            }
            alert("削除しました"){
                yesButton { finish() }
            }.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }


}
