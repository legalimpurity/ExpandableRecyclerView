package com.legalimpurity.expandablerecyclerview.demo;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by rajatkhanna on 27/12/16.
 */

public class Eventhandlers {
        public void fabHit(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
}
