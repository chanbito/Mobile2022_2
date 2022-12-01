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
import android.widget.TextView;

import androidx.navigation.fragment.NavHostFragment;

import com.example.mobile2022_2.Models.Item;
import com.example.mobile2022_2.Models.Lista;
import com.example.mobile2022_2.R;
import com.example.mobile2022_2.Repository.ItemRepository;
import com.example.mobile2022_2.Repository.ListaRepository;
import com.example.mobile2022_2.Repository.ProdutoRepository;
import com.example.mobile2022_2.adapter.ItemsAdapter;
import com.example.mobile2022_2.adapter.ListAdapter;
import com.example.mobile2022_2.databinding.FragmentItemsBinding;
import com.example.mobile2022_2.databinding.FragmentListBinding;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {
    private final String TAG = "ItemFragment";
    public Lista Selecionado;

    private FragmentItemsBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentItemsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ProdutoRepository rep = ProdutoRepository.getInstance(this.getContext());
        ItemRepository repo = ItemRepository.getInstance(this.getContext());

        int ID_LISTA = -1;
        String  nome_lista = "Novo";

        try {
            Lista l = getArguments().getParcelable("Lista");
            Log.e(TAG, l.getDesc());
            ID_LISTA = l.getId();
            nome_lista = l.getDesc();
        }catch (Exception e){

        }

        RecyclerView rc = view.findViewById(R.id.RCItem);

        List<Item> param;
        if(ID_LISTA >= 0){
            param = repo.getItembyList(ID_LISTA);
            TextView tv1 =view.findViewById(R.id.titulotextView);
            Log.e(TAG,"Liastas: " + (tv1 == null));
            tv1.setText(nome_lista);
        }else{
            param = new ArrayList<>();
        }

        Log.e(TAG,"Liastas: " + param.size());


        ItemsAdapter ad = new ItemsAdapter(param);
        rc.setAdapter(ad);
        LinearLayoutManager llm  = new LinearLayoutManager(this.getContext());
        rc.setLayoutManager(llm);

        /*binding.NovoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ItemFragment.this)
                        .navigate(R.id.action_ListFragment_to_ItemFragment);
            }
        });*/
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
