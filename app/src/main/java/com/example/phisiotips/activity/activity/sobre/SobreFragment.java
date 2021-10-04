package com.example.phisiotips.activity.activity.sobre;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phisiotips.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SobreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SobreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SobreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SobreFragment newInstance(String param1, String param2) {
        SobreFragment fragment = new SobreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SobreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Element versao = new Element();
        versao.setTitle("Versão 1.0");

        View view = new AboutPage(getActivity())
                .setImage(R.drawable.logo)
                .setDescription("O PhisioTips é um aplicativo criado com o intuito de ajudar a comunicação entre pessoasem relação ao mundo fitness, " +
                        "dando a oportunidade de criar perguntar ou até mesmo enquetes, assim gerando uma atividade decomunicação entre diferentes pessoas")
                .addGroup("Entre em contato")
                .addEmail("PatrickRafael05@gmail.com", "Envie um e-mail")
                .addGroup("Redes Sociais")
                .addWebsite("https://www.linkedin.com/in/patrick-rafael-a8a3661a8/", "Linkedin")
                .addGitHub("https://github.com/Patrick-Rafael", "GitHub")
                .addInstagram("instagram.com/patrickrafaelborges/", "Instagran")
                .addTwitter("https://twitter.com/PatrickRafaelb", "Twitter")
                .addItem(versao)
                .create();

        return view;


        // View view =  inflater.inflate(R.layout.fragment_sobre, container, false);
        //return  view;
    }
}