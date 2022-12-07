package com.example.mobile2022_2.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
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
import com.example.mobile2022_2.Repository.ProdutoRepository;
import com.example.mobile2022_2.adapter.ClickItemListener;
import com.example.mobile2022_2.adapter.ItemsAdapter;
import com.example.mobile2022_2.databinding.FragmentItemsBinding;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {
    private final String TAG = "ItemFragment";
    public static Lista Selecionado;
    public static boolean OcultarAtivo;
    public List<Item> ListaFinal;

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
        OcultarAtivo = false;

        try {
            if(Selecionado == null){
                 Selecionado = getArguments().getParcelable("Lista");
            }
            Log.e(TAG, Selecionado.getDesc());
            ID_LISTA = Selecionado.getId();
            nome_lista = Selecionado.getDesc();
        }catch (Exception e){

        }

        RecyclerView rc = view.findViewById(R.id.RCItem);

        TextView tv1 =view.findViewById(R.id.titulotextView);

        if(ID_LISTA >= 0){
            if(ListaFinal == null){
                ListaFinal = repo.getItembyList(ID_LISTA);
            }
            tv1.setText(nome_lista);
        }else{
            tv1.setText("Nova Lista");
            ListaFinal = new ArrayList<>();
        }

        Log.e(TAG,"Liastas: " + ListaFinal.size());


        ItemsAdapter ad = new ItemsAdapter(ListaFinal);
        rc.setAdapter(ad);
        LinearLayoutManager llm  = new LinearLayoutManager(this.getContext());
        rc.setLayoutManager(llm);

        binding.OcultarMarcadosButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                OcultarAtivo = !OcultarAtivo;
                if(OcultarAtivo)
                    binding.OcultarMarcadosButton.setText(R.string.Mostrar_Marcado);
                else
                    binding.OcultarMarcadosButton.setText(R.string.Ocultar_Marcado);
                ad.filter(OcultarAtivo);

            }
        });

        binding.AddButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ItemFragment.this)
                        .navigate(R.id.action_Item_Fragment_to_itemcadastroFragment);
            }
        });
    }

    private void ActualizeRC(RecyclerView rc) {

        List<Item> ListaFiltrada = new ArrayList<>();
        for (Item item:
                ListaFinal) {
            if(OcultarAtivo){
                if(item.getAtivo() == !OcultarAtivo) {
                    Log.e(TAG,"ItemListchecked add uncheck ");
                    ListaFiltrada.add(item);
                }
            }
            else{
                ListaFiltrada.add(item);
            }
        }

        ItemsAdapter ad = new ItemsAdapter(ListaFiltrada);
        rc.setAdapter(ad);

        Log.e(TAG,"ListaFinal: " + ListaFinal.size());
        Log.e(TAG,"ListaFiltrada: " + ListaFiltrada.size());
        Log.e(TAG,"ad.getItemCount(): " + ad.getItemCount());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
