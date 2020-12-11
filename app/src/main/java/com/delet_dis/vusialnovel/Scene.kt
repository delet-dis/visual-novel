package com.delet_dis.vusialnovel

import android.media.Image

public class Scene {
    private var firstChoice: String
        get() {
            return this.firstChoice
        }
        set(value: String) {
            this.firstChoice = value
        }
    private var secondChoice: String
        get() {
            return this.secondChoice
        }
        set(value: String) {
            this.secondChoice = value
        }

    private var image: Image
        get() {
            return this.image
        }
        set(value: Image) {
            this.image = value
        }


}