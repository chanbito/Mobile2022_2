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
import com.example.mobile2022_2.adapter.ListAdapter;
import com.example.mobile2022_2.databinding.FragmentFirstBinding;
import com.example.mobile2022_2.databinding.FragmentListBinding;

import java.util.List;


public class ListFragment extends Fragment {
    private final String TAG = "ListFragment";

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
        List<Lista> param = repo.getListas();
        Log.e(TAG,"Liastas: " + param.size());
        ListAdapter ad = new ListAdapter(param);

        rc.setAdapter(ad);
        LinearLayoutManager llm  = new LinearLayoutManager(this.getContext());
        rc.setLayoutManager(llm);

        binding.NovoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //https://developer.android.com/guide/navigation/navigation-pass-data
                Bundle bundle = new Bundle();
                //bundle.putString("amount", amount);
                NavHostFragment.findNavController(ListFragment.this)
                        .navigate(R.id.action_ListFragment_to_ItemFragment, bundle);
            }
        });
    }
}
