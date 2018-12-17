package com.example.owner.memo2

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Memo :RealmObject(){
    @PrimaryKey
    var id:Long=0
    var title:String=""
    var detail:String=""
}