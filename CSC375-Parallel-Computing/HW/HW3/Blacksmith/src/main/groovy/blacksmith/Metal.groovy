package blacksmith

enum Metal{
    STEEL(0.75),
    ADAMANTIUM(1),
    VIBRANIUM(1.25)

    final double thermalConst

    Metal(double thermalConst) {
        this.thermalConst = thermalConst
    }
}
