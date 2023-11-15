package com.example.superjjj.util;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.superjjj.calculate;
import com.example.superjjj.conver;
import com.example.superjjj.matrix;
import androidx.fragment.app.Fragment;


public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new calculate();  // 对应 fragment_calculate
            case 1:
                return new conver();    // 对应 fragment_conver
            case 2:
                return new matrix();    // 对应 fragment_matrix
            default:
                return new calculate(); // 默认情况
        }
    }

    @Override
    public int getItemCount() {
        return 3; // 总共有三个 Fragment
    }
}
