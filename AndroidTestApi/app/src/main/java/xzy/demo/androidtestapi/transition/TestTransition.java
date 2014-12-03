/*
 * Copyright (C) 2014 zhengyang.xu xuzhengyang.cn@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package xzy.demo.androidtestapi.transition;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import xzy.demo.androidtestapi.R;

/**
 * 请使用API 21
 */
public class TestTransition extends Activity {

    //scenes to transition
    private Scene mScene1, mScene2, mScene3;

    private RelativeLayout mBaseLayout;

    private TransitionManager mCustomTransitionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_transition_layout);

        //得到容器ViewGroup
        mBaseLayout = (RelativeLayout) findViewById(R.id.base);

        //创建三个场景
        mScene1 = new Scene(mBaseLayout, mBaseLayout.findViewById(R.id.container));
        mScene2 = Scene.getSceneForLayout(mBaseLayout, R.layout.scene_2, this);
        mScene3 = Scene.getSceneForLayout(mBaseLayout, R.layout.scene_3, this);

        //create transition, set properties
        TransitionInflater inflater = TransitionInflater.from(this);
        mCustomTransitionManager = inflater.inflateTransitionManager(R.transition.transition_mgr,
                mBaseLayout);


    }

    public void changeScene(View v) {

        if (v.getId() == R.id.anim_1) {
            //简单的写法，直接用TransitionManager,使用默认动画效果
            TransitionManager.go(mScene1);
        } else if (v.getId() == R.id.anim_2) {
            TransitionManager.go(mScene2);
        } else if (v.getId() == R.id.anim_3) {
            mCustomTransitionManager.transitionTo(mScene3);
        } else if (v.getId() == R.id.anim_4) {
            //在下一帧进行动画，可以继续设置属性等
            TransitionManager.beginDelayedTransition(mBaseLayout);

            //可以继续设置View的属性，不会影响动画
            ImageView btn4 = (ImageView) mBaseLayout.findViewById(R.id.btn4);
            ViewGroup.LayoutParams params = btn4.getLayoutParams();
            if (params.width > dip2px(this, 51)) {
                params.width = dip2px(this, 51);
                params.height = dip2px(this, 50);
            } else {
                params.width = dip2px(this, 100);
                params.height = dip2px(this, 100);
            }
            btn4.setLayoutParams(params);
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
