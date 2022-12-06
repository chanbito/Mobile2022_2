package com.example.mobile2022_2.view;

import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;


import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mobile2022_2.Models.Produto;
import com.example.mobile2022_2.R;
import com.example.mobile2022_2.Repository.ProdutoRepository;
import com.example.mobile2022_2.databinding.FragmentItemCadastroBinding;

import java.util.ArrayList;


public class ItemcadastroFragment extends Fragment {
    private final String TAG = "ItemcadastroFragment";
    private FragmentItemCadastroBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentItemCadastroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProdutoRepository rep = ProdutoRepository.getInstance(this.getContext());


        //binding.SearchView.setSuggestionsAdapter();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
