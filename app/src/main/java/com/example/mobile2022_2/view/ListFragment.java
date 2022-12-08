package com.example.mobile2022_2.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.fragment.NavHostFragment;

import com.example.mobile2022_2.Models.Lista;
import com.example.mobile2022_2.R;
import com.example.mobile2022_2.Repository.ListaRepository;
import com.example.mobile2022_2.adapter.ClickItemListener;
import com.example.mobile2022_2.adapter.ListAdapter;
import com.example.mobile2022_2.databinding.FragmentFirstBinding;
import com.example.mobile2022_2.databinding.FragmentListBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ListFragment extends Fragment {
    private final String TAG = "ListFragment";
    public Lista Selecionado;

    private FragmentListBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListaRepository repo = ListaRepository.getInstance(this.getContext());

        RecyclerView rc = view.findViewById(R.id.RCLista);

        List<Lista> param = repo.getListasAtivas();

        Log.e(TAG,"Liastas: " + param.size());
        ListAdapter ad = new ListAdapter(param, ListFragment.this);

        rc.setAdapter(ad);
        LinearLayoutManager llm  = new LinearLayoutManager(this.getContext());
        rc.setLayoutManager(llm);

        binding.NovoButton.setOnClickListener(
                new ClickItemListener(
                        null,
                        ListFragment.this) //new Lista(-1,"Nova", Calendar.getInstance().getTime(),true,null

             );
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
