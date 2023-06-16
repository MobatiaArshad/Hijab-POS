package com.a71cities.hijab.ppm.ui.home

import androidx.lifecycle.ViewModel
import com.a71cities.hijab.ppm.ui.home.model.HomeData

class HomeViewModel : ViewModel() {

    //Payment Type 0->cash, 1->GPay, 2->PhonePe

    fun getHomeData(): ArrayList<HomeData> = arrayListOf(
        HomeData(
            "#23ASDD, #JB33BC, #ABC3BC",
            "Abhaya, Hijab, Cap",
            "1",
            "4200",
            false,
            1
        ),
        HomeData(
            "#ASDF33",
            "Head cap",
            "2",
            "250",
            false,
            0
        ),
        HomeData(
            "#24RRSWS",
            "Muzualla",
            "2",
            "1600",
            false,
            1
        ),
        HomeData(
            "#23ASDD",
            "shawl",
            "1",
            "3000",
            true,
            2
        ),
        HomeData(
            "#23ASDD",
            "Abhaya",
            "1",
            "1600",
            false,
            2
        ),
        HomeData(
            "#23ASDD",
            "Hijab",
            "1",
            "1600",
            false,
            0
        ),
        HomeData(
            "#23ASDD",
            "Hijab",
            "1",
            "370",
            false,
            0
        ),
        HomeData(
            "#23ASDD",
            "Abhaya",
            "1",
            "1600",
            true,
            1
        ),
    )
}