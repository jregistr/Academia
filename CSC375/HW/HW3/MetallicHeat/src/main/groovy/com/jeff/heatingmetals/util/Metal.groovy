package com.jeff.heatingmetals.util

enum Metal{
    STEEL(100),
    ADAMANTIUM(150),
    VIBRANIUM(230)

    final double thermalConst

    Metal(double thermalConst) {
        this.thermalConst = thermalConst
    }
}


