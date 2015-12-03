package com.jeff.heatingmetals.application

import com.jeff.heatingmetals.util.Alloy
import com.jeff.heatingmetals.util.Tile


class Anvil {

    private Tile[][] display;

    private Alloy[][] current;
    private Alloy[][] history;

    Anvil(Tile[][] display, double tL, double bR) {
        this.display = display
        int xLen = display.length
        int yLen = display[0].length
        0.upto(xLen -1){i->
            0.upto(yLen - 1)
        }
    }

    void execute(){

    }

    //private Metal[][] alloy;

    /*Anvil(Tile[][] display, double lT, double rT) {
        this.display = display
        alloy = new Metal[display.length][display[0].length]
        0.upto(display.length-1){i->
            0.upto(display[0].length){j->
                alloy[i][j] = Metal.random()
            }
        }
    }*/
}
