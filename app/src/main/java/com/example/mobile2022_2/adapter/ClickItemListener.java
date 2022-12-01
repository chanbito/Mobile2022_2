package com.example.mobile2022_2.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import androidx.navigation.fragment.NavHostFragment;

import com.example.mobile2022_2.Models.Item;
import com.example.mobile2022_2.Models.Lista;
import com.example.mobile2022_2.R;

import java.util.Calendar;

public class ClickItemListener implements View.OnClickListener {
    private Lista Lista_param;
    private Fragment frag_origem;

    public ClickItemListener(Lista lista_param, Fragment fragment){
        Lista_param = lista_param;
        frag_origem = fragment;

    }
    @Override
    public void onClick(View view) {
        //https://developer.android.com/guide/navigation/navigation-pass-data
        Bundle bundle = new Bundle();
        bundle.putParcelable("Lista", Lista_param);
        NavHostFragment.findNavController(frag_origem)
                .navigate(R.id.action_ListFragment_to_ItemFragment, bundle);
    }
}
