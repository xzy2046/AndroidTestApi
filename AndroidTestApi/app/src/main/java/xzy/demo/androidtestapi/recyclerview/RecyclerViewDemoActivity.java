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

package xzy.demo.androidtestapi.recyclerview;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import xzy.demo.androidtestapi.R;

public class RecyclerViewDemoActivity extends ActionBarActivity {

    private RecyclerView mRecyclerView;

    private SimpleStringAdapter mAdapter;


    private ArrayList<String> mValues;

    private String[] citys = {"南京", "苏州", "无锡", "南通", "常州", "盐城", "徐州", "淮安", "宿迁", "连云港", "扬州",
            "镇江", "泰州", "宜兴"};

    RecyclerView.ItemAnimator mCachedAnimator = null;

    private Button mAdd;

    private Button mDelete;

    private Button mMove;

    private Button mSwap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);

        //1.get a recyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
//        mCachedAnimator = new DefaultItemAnimator();
        mCachedAnimator = mRecyclerView.getItemAnimator();
        mCachedAnimator.setSupportsChangeAnimations(true);
//        mCachedAnimator.setAddDuration(5000);
//        mCachedAnimator.setChangeDuration(5000);
        //2. set LayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //3. create a RecyclerView.Adapter
        mValues = new ArrayList<String>();
        Collections.addAll(mValues, citys);
        mAdapter = new SimpleStringAdapter(this, mValues);

        //4. set adapter
        mRecyclerView.setAdapter(mAdapter);

        //5.set animator and divider
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setItemAnimator(mCachedAnimator); //采用默认的Animator
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView,
                    MotionEvent motionEvent) {
                Log.i("xzy", "onInterceptTouchEvent");
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                Log.i("xzy", "onTouchEvent");
            }

        });

        //6. set On Item Click Listener
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Toast.makeText(RecyclerViewDemoActivity.this,
                                        "position is: " + position, Toast.LENGTH_SHORT).show();
                            }
                        }));

        //init Buttons
        mAdd = (Button) findViewById(R.id.add);
        mDelete = (Button) findViewById(R.id.remove);
        mMove = (Button) findViewById(R.id.move);
        mSwap = (Button) findViewById(R.id.swap);
        mAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAdapter.addValueAt(1, "China");
                mAdapter.notifyItemInserted(0);  //产生动画,不会完全重刷
//                mAdapter.notifyDataSetChanged(); //全部刷新，没有动画
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAdapter.removeValueAt(2);
                mAdapter.notifyItemRemoved(2);
            }
        });

        mMove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAdapter.notifyItemMoved(2, 5);
            }
        });

        mSwap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mAdapter.notifyItemRangeChanged(0, 10);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_view_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
