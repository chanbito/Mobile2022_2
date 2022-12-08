package com.example.mobile2022_2.view;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


import androidx.navigation.fragment.NavHostFragment;

import com.example.mobile2022_2.Models.Produto;
import com.example.mobile2022_2.R;
import com.example.mobile2022_2.Repository.ItemRepository;
import com.example.mobile2022_2.Repository.ProdutoRepository;
import com.example.mobile2022_2.adapter.ClickItemListener;
import com.example.mobile2022_2.databinding.FragmentItemCadastroBinding;

import java.util.ArrayList;
import java.util.Locale;


public class ItemcadastroFragment extends Fragment {
    private final String TAG = "ItemcadastroFragment";
    private FragmentItemCadastroBinding binding;
    private Produto produtoSelecionado;

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
        ArrayList<String> sugest = new ArrayList<>();

        for (Produto p :
                rep.getProdutos()) {
            sugest.add(p.getDesc());
        }

        final MatrixCursor c = getCursorSearchView(sugest);

        final CursorAdapter suggestionAdapter = new SimpleCursorAdapter(
                ItemcadastroFragment.this.getContext(),
                android.R.layout.simple_list_item_1,
                c,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);

        suggestionAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                ArrayList<String> _sugest = new ArrayList<>();
                Log.e(TAG,"bati aqui: ");
                for (String s:
                        sugest) {
                    if(s.toUpperCase(Locale.ROOT).contains(charSequence.toString().toUpperCase(Locale.ROOT))){
                        Log.e(TAG,"adicionando: " + s);
                        _sugest.add(s);
                    }
                }
                return getCursorSearchView(_sugest);
            }

        });

        binding.SearchView.setSuggestionsAdapter(suggestionAdapter);
        binding.SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                produtoSelecionado = null;
                for (Produto p :
                        rep.getProdutos()) {
                    if (p.getDesc().equalsIgnoreCase(s)) {
                        produtoSelecionado = p;
                    }
                }

                if (produtoSelecionado != null) {
                    popularTela();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Item não encontrado, deseja criar?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SalvarProduto(rep, s);
                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    return;
                                }
                            });
                    builder.create().show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                suggestionAdapter.getFilter().filter(s);
                return true;
            }
        });
        binding.SearchView.setIconifiedByDefault(true);
        binding.SearchView.setFocusable(true);
        binding.SearchView.setIconified(false);
        binding.SearchView.clearFocus();
        binding.SearchView.requestFocusFromTouch();

        binding.NovoProdutoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = binding.SearchView.getQuery().toString();
                Log.e(TAG,"input: " + input);
                produtoSelecionado = null;
                for (Produto p :
                        rep.getProdutos()) {
                    if (p.getDesc().equalsIgnoreCase(input)) {
                        produtoSelecionado = p;
                    }
                }

                if (produtoSelecionado != null) {
                    popularTela();
                } else {
                    SalvarProduto(rep,input);
                }
            }
        });
        binding.CancelarButton.setOnClickListener(
                new ClickItemListener(
                        ItemFragment.ListaSelecionada,
                        ItemcadastroFragment.this) //new Lista(-1,"Nova", Calendar.getInstance().getTime(),true,null

                );

                //view1 -> NavHostFragment.findNavController(ItemcadastroFragment.this)
                //.navigate(R.id.action_itemcadastroFragment_to_Item_Fragment));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ItemcadastroFragment.this.getContext(),
                R.array.MetricasArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.MetricaSpinner.setAdapter(adapter);

        binding.Salvarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(produtoSelecionado == null){
                    Toast toast=Toast.makeText(ItemcadastroFragment.this.getContext(), "Por favor Selecione um Produto",Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
                ItemRepository irep = new ItemRepository(ItemcadastroFragment.this.getContext());
                irep.addItem(binding.MetricaSpinner.getSelectedItem().toString(),produtoSelecionado.getId(),
                        ItemFragment.ListaSelecionada.getId(),Integer.parseInt(binding.QTDeditTextNumber.getText().toString()));


                new ClickItemListener(
                        ItemFragment.ListaSelecionada,
                        ItemcadastroFragment.this //new Lista(-1,"Nova", Calendar.getInstance().getTime(),true,null
                );

                Bundle bundle = new Bundle();
                bundle.putParcelable("Lista", ItemFragment.ListaSelecionada);
                NavHostFragment.findNavController(ItemcadastroFragment.this)
                        .navigate(R.id.action_ListFragment_to_ItemFragment, bundle);
            }
        });
    }

    private void SalvarProduto(ProdutoRepository rep, String s) {
        produtoSelecionado = rep.CreateProduto(s);
        popularTela();
    }

    private MatrixCursor getCursorSearchView(ArrayList<String> sugest) {
        String[] columns = {
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA
        };
        final MatrixCursor c = new MatrixCursor(columns);

        for (int i = 0; i < sugest.size(); i++) {
            c.addRow(new Object[]{i, sugest.get(i),null});
        }



        return c;
    }

    private void popularTela() {
        binding.DescTextView.setText(produtoSelecionado.getDesc());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

