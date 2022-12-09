package com.example.mobile2022_2.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.mobile2022_2.adapter.ClickItemListener;
import com.example.mobile2022_2.adapter.ItemsAdapter;
import com.example.mobile2022_2.databinding.FragmentItemsBinding;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class ItemFragment extends Fragment {
    private final String TAG = "ItemFragment";
    public static Lista ListaSelecionada;
    public static boolean OcultarAtivo;
    public List<Item> ListaFinal;
    private ItemsAdapter ad;

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
        ListaRepository repoLista = ListaRepository.getInstance(this.getContext());


        OcultarAtivo = false;

        try {
            Log.e(TAG,"getArgs: " + getArguments());
            if(getArguments().getBoolean("novaLista")) {
                ListaSelecionada = new Lista(-1,"Nova Lista", Calendar.getInstance().getTime(),true);
            }else{
                ListaSelecionada = getArguments().getParcelable("Lista");
            }

        }catch (Exception e){
            if(ListaSelecionada == null){
                ListaSelecionada = new Lista(-1,"Nova Lista", Calendar.getInstance().getTime(),true);
            }
        }

        if(ListaSelecionada.getId() >= 0){
            if(ListaFinal == null){
                ListaFinal = repo.getItembyList(ListaSelecionada.getId());
            }
            binding.editTextListName.setText(ListaSelecionada.getDesc());
            SetListEditMode(false);

        }else{
            binding.editTextListName.setText("Nova Lista");
            SetListEditMode(true);
            ListaFinal = new ArrayList<>();
        }

        Log.e(TAG,"Liastas: " + ListaFinal.size());

        ad = null;
        if(ListaFinal.size() > 0) {
            ad = new ItemsAdapter(ListaFinal,ItemFragment.this.getContext());
            binding.RCItem.setAdapter(ad);
        }
        LinearLayoutManager llm  = new LinearLayoutManager(this.getContext());
        binding.RCItem.setLayoutManager(llm);

        binding.OcultarMarcadosButton.setOnClickListener(view1 -> {
            OcultarAtivo = !OcultarAtivo;
            if(OcultarAtivo)
                binding.OcultarMarcadosButton.setText(R.string.Mostrar_Marcado);
            else
                binding.OcultarMarcadosButton.setText(R.string.Ocultar_Marcado);
            if(ad != null)
                ad.filter(OcultarAtivo);

        });

        binding.AddButton.setOnClickListener(view12 -> {
            if(ListaSelecionada.getId() == -1)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Deseja Criar uma lista com o nome " + binding.editTextListName.getText().toString() + " ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ListaSelecionada.setDesc(binding.editTextListName.getText().toString());
                                repoLista.addLista(ListaSelecionada);
                                NavHostFragment.findNavController(ItemFragment.this)
                                        .navigate(R.id.action_Item_Fragment_to_itemcadastroFragment);
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                return;
                            }
                        });
                builder.create().show();
            }else{
                NavHostFragment.findNavController(ItemFragment.this)
                        .navigate(R.id.action_Item_Fragment_to_itemcadastroFragment);
            }

        });

        binding.EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.EditButton.getText() == "Save"){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Deseja Salvar a lista com o nome " + binding.editTextListName.getText().toString() + " ?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    ListaSelecionada.setDesc(binding.editTextListName.getText().toString());
                                    repoLista.updateLista(ListaSelecionada);
                                    //NavHostFragment.findNavController(ItemFragment.this)
                                      //      .navigate(R.id.action_Item_Fragment_to_itemcadastroFragment);
                                    SetListEditMode(false);
                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    return;
                                }
                            });
                    builder.create().show();
                }else{
                    SetListEditMode(true);
                }
            }
        });

        binding.finalButton.setOnClickListener(view13 -> {
            if(ListaFinal.isEmpty()){
                ListaSelecionada.setAtivo(false);
                repoLista.updateLista(ListaSelecionada);
            }
            int count = 0;
            for (Item i :
                    ListaFinal) {
                if(i!= null)
                if(!i.getAtivo()){
                    count++;
                }
            }

            if(count > 0){
                //avisar que ainda tem itens não marcados e perguntar se quer fechar mesmo assim
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Há itens na lista ainda não recolhidos, realmente deseja continuar?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                for (Item i :
                                        ListaFinal) {
                                    if(!i.getAtivo()){
                                        i.setAtivo(true);
                                    }
                                }
                                ListaSelecionada.setAtivo(false);
                                repoLista.updateLista(ListaSelecionada);
                                NavHostFragment.findNavController(ItemFragment.this).navigate(
                                        R.id.action_Item_Fragment_to_ListFragment
                                );

                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                return;
                            }
                        });
                builder.create().show();
            }else{
                ListaSelecionada.setAtivo(false);
                repoLista.updateLista(ListaSelecionada);
                NavHostFragment.findNavController(ItemFragment.this).navigate(
                        R.id.action_Item_Fragment_to_ListFragment
                );
            }

        });
    }

    private void SetListEditMode(boolean mode) {
        binding.editTextListName.setFocusable(mode);
        binding.editTextListName.setFocusableInTouchMode(mode);
        binding.editTextListName.setClickable(mode);
        if(mode)
            binding.EditButton.setText("Save");
        else
            binding.EditButton.setText("Edit");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
