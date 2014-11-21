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

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author zhengyang.xu
 *         14-11-20 上午10:59
 * @version 0.1
 *
 *          1.继承RecyclerView.Adapter
 *          2.生成一个ValueHolder,实现需要implements的方法
 */
public class SimpleStringAdapter extends RecyclerView.Adapter<SimpleStringAdapter.ValueHolder> {

    private int mBackground;

    private ArrayList<String> mValues;

    public static class ValueHolder extends RecyclerView.ViewHolder {

        public String mBoundString;

        public TextView mTextView;

        public ValueHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText();
        }
    }

    public SimpleStringAdapter(Context context, ArrayList<String> strings) {
        TypedValue val = new TypedValue();
        if (context.getTheme() != null) {
            context.getTheme().resolveAttribute(
                    android.R.attr.selectableItemBackground, val, true);
        }
        mBackground = val.resourceId;
        mValues = strings;
//        mValues = new ArrayList<String>();
//        Collections.addAll(mValues, strings);
    }


    /**
     * 创建一个ValueHolder, 此处是直接New出来的，也可通过布局
     * 可以更具Position 绑定不同的View,这点比AbsListView强
     */
    @Override
    public ValueHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        final ValueHolder h = new ValueHolder(new TextView(viewGroup.getContext()));
        h.mTextView.setMinimumHeight(128);
        h.mTextView.setPadding(20, 0, 20, 0);
        h.mTextView.setFocusable(true);
        h.mTextView.setBackgroundResource(mBackground);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 5;
        lp.topMargin = 20;
        lp.bottomMargin = 15;
        h.mTextView.setLayoutParams(lp);

        return h;
    }

    @Override
    public void onBindViewHolder(ValueHolder holder, int position) {
        Log.i("xzy", "onBindViewHolder");
        holder.mBoundString = mValues.get(position);
        holder.mTextView.setText(position + ":" + mValues.get(position));
        holder.mTextView.setMinHeight((200 + mValues.get(position).length() * 10));
        holder.mTextView.setBackgroundColor(getBackgroundColor(position));
    }

    private int getBackgroundColor(int position) {
        switch (position % 4) {
            case 0:
                return Color.WHITE;
            case 1:
                return Color.RED;
            case 2:
                return Color.LTGRAY;
            case 3:
                return Color.BLUE;
        }
        return Color.TRANSPARENT;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public String getValueAt(int position) {
        return mValues.get(position);
    }

    public void addValueAt(int position, String string) {
        mValues.add(position, string);
    }

    public void removeValueAt(int position) {
        mValues.remove(position);
    }


}
