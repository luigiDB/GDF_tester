package it.luigidb.gdftester;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuestionFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private String[] mParam1;


    public QuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment QuestionFragment.
     */
    public static QuestionFragment newInstance(Cursor param1) {
        param1.moveToFirst();
        String resources[] = new String[]{  param1.getString(param1.getColumnIndex("_id")),
                                            param1.getString(param1.getColumnIndex("domanda")),
                                            param1.getString(param1.getColumnIndex("a")),
                                            param1.getString(param1.getColumnIndex("b")),
                                            param1.getString(param1.getColumnIndex("c")),
                                            param1.getString(param1.getColumnIndex("d")),
                                            param1.getString(param1.getColumnIndex("risposta"))};

        Log.v("FRAGMENT", "resource(0): " + resources[0]);

        QuestionFragment fragment = new QuestionFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, resources);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (String[])getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.v("FRAGMENT", "onStart");

        TextView t;
        int[] viewId = new int[]{R.id._id, R.id.question, R.id.opt_a, R.id.opt_b, R.id.opt_c, R.id.opt_d, R.id.response};
        for(int i = 0 ; i <= 6 ; i++) {
            t = (TextView)getActivity().findViewById(viewId[i]);
            t.setText(mParam1[i]);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /*public void onRadioButtonClicked(View view) {
        TextView responseTextView = (TextView)getActivity().findViewById(R.id.response);
        String response = (String)responseTextView.getText();

        switch(view.getId()) {
            case R.id.opt_a:
                if (response == "a")  {
                    ((MainActivity)getActivity()).results(1);
                } else {
                    ((MainActivity)getActivity()).results(0);
                }
                break;
            case R.id.opt_b:
                if (response == "b")  {
                    ((MainActivity)getActivity()).results(1);
                } else {
                    ((MainActivity)getActivity()).results(0);
                }
                break;
            case R.id.opt_c:
                if (response == "c")  {
                    ((MainActivity)getActivity()).results(1);
                } else {
                    ((MainActivity)getActivity()).results(0);
                }
                break;
            case R.id.opt_d:
                if (response == "d")  {
                    ((MainActivity)getActivity()).results(1);
                } else {
                    ((MainActivity)getActivity()).results(0);
                }
                break;
        }
    }*/

}
