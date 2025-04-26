package com.rfgalahad.testcatatan

sealed class Screen(val route: String) {
    object ListCatatan : Screen("list_catatan")
    object BuatCatatan : Screen("buat_catatan")
    object UbahCatatan : Screen("ubah_catatan/{catatanId}") {
        fun createRoute(catatanId: Int) = "ubah_catatan/$catatanId"
    }
}