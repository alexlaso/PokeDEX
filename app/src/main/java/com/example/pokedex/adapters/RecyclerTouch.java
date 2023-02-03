package com.example.pokedex.adapters;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerTouch implements RecyclerView.OnItemTouchListener{
    GestureDetector lector;

    private OnItemClickListener clickListener;
    public interface OnItemClickListener{
        void onItemClick(View v, int posicion);
        void onLongItemClick(View v, int posicion);
    }

    public RecyclerTouch(Context context, RecyclerView recyclerView, OnItemClickListener listener){
        clickListener = listener;

        lector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e){
                return true;
            }
            @Override
            public void onLongPress(MotionEvent e){
                View child = recyclerView.findChildViewUnder(e.getX(),e.getY());

                if (child != null && clickListener != null){
                    clickListener.onLongItemClick(child,recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View child = rv.findChildViewUnder(e.getX(),e.getY());
        if (child != null && clickListener != null && lector.onTouchEvent(e)){
            clickListener.onItemClick(child,rv.getChildAdapterPosition(child));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }
}