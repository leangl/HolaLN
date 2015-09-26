package mobi.tattu.hola.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import mobi.tattu.hola.R;
import mobi.tattu.hola.data.DataStore;
import mobi.tattu.hola.model.Category;
import mobi.tattu.hola.ui.MainActivity2;

/**
 * Created by cristian on 25/09/15.
 */
public class CategoryFragment extends BaseFragment {

    public static final String CATEGORY_LIST = "category_list";
    public ArrayList<Category> mCategories;
    public ArrayList<Category> mCategoriesSelected;

    public static CategoryFragment newInstance() {

        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategories = new ArrayList<>(DataStore.getInstance().getAll(Category.class));
        mCategoriesSelected = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        addCheckBoxContainer(view);
        Button nextButton = (Button) view.findViewById(R.id.category_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCategoriesSelected.isEmpty()) {
                    Toast.makeText(getBaseActivity(), R.string.message_error_category, Toast.LENGTH_SHORT).show();
                } else {
                    DataStore.getInstance().deleteAll(Category.class);
                    saveCategoriesSelected();
                    startActivity(new Intent(getBaseActivity(), MainActivity2.class));
                }
            }
        });
        return view;

    }
    private void saveCategoriesSelected() {
        int size = mCategoriesSelected.size();
        for (int i = 0; i < size; i++) {
            Category category = mCategoriesSelected.get(i);
            DataStore.getInstance().putObject(category.getNameCategory(getBaseActivity()), category);
        }
        DataStore.getInstance().putObject("WIZARD_COMPLETED", Boolean.TRUE);
    }
    private void addCheckBoxContainer(View view) {
        LinearLayout containerLeft = (LinearLayout) view.findViewById(R.id.container_checkbox_left);
        LinearLayout containerRight = (LinearLayout) view.findViewById(R.id.container_checkbox_right);
        int size = mCategories.size();
        for (int i = 0; i < size; i++) {
            Category category = mCategories.get(i);
            if (category.layoutSide.equals(Category.Side.LEFT)) {
                containerLeft.addView(createCheckBox(category));
            } else {
                containerRight.addView(createCheckBox(category));
            }

        }
    }

    private CheckBox createCheckBox(Category category) {
        CheckBox checkBox = new CheckBox(getBaseActivity());
        checkBox.setText(category.getNameCategory(getBaseActivity()));
        checkBox.setChecked(category.checked);
        checkBox.setPadding(10, 10, 10, 10);
        checkBox.setTag(category);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Category category = (Category) compoundButton.getTag();
                if (compoundButton.isChecked()) {
                    mCategoriesSelected.add(category);
                } else {
                    mCategoriesSelected.remove(category);
                }
            }
        });
        return checkBox;
    }


}
